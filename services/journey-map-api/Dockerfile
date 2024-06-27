# Stage 1: Build the application
FROM openjdk:17-jdk-slim AS build

# Define the Maven version to install
ARG MAVEN_VERSION=3.9.6

# Install Maven
RUN apt-get update && apt-get install -y curl tar \
    && curl -fsSL https://downloads.apache.org/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz -o maven.tar.gz \
    && mkdir -p /usr/share/maven \
    && tar -xzf maven.tar.gz -C /usr/share/maven --strip-components=1 \
    && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn \
    && rm maven.tar.gz \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml file and the source code to the container
COPY pom.xml .
COPY src ./src

# Ensure dependencies are downloaded and cached
RUN mvn dependency:go-offline -B

# Build the application
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the jar file from the previous stage
COPY --from=build /app/target/journey-map-api-0.0.1-SNAPSHOT.jar /app/journey-map-api-0.0.1-SNAPSHOT.jar

# Expose the port the application runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "/app/journey-map-api-0.0.1-SNAPSHOT.jar"]
