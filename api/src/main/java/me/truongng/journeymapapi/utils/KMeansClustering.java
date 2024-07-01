package main.java.me.truongng.journeymapapi.utils;

import java.util.*;

/*
 KMeansClustering - Phân vùng tài xế 
 Input : List of Drivers and K 
 Output : Centroids and Clusters
 
 --> Phân vùng ra xong mỗi khi có khách hàng , thì tìm cái centroid gần nó nhất rồi random một thằng tài xế trong vùng đó để đi đón khách hàng đó
 */

import me.truongng.journeymapapi.models.Driver;
import me.truongng.journeymapapi.models.Location;

public class KMeansClustering {

    private int k; // Number of clusters
    private List<Driver> drivers; // List of drivers
    private List<Driver> centroids; // List of centroids
    private Map<Driver, List<Driver>> clusters; // Clusters

    public KMeansClustering(int k, List<Driver> drivers) {
        this.k = k;
        this.drivers = drivers;
        this.centroids = new ArrayList<>();
        this.clusters = new HashMap<>();
    }

    // Initialize centroids
    private void initializeCentroids() {
        Random random = new Random();
        Set<Integer> selectedIndices = new HashSet<>();
        while (selectedIndices.size() < k) {
            int index = random.nextInt(drivers.size());
            if (!selectedIndices.contains(index)) {
                centroids.add(drivers.get(index));
                selectedIndices.add(index);
            }
        }
    }

    // Calculate Euclidean distance
    private double calculateDistance(Driver d1, Driver d2) {
        return d1.getLocation().distanceTo(d2.getLocation());
    }

    // Assign drivers to the nearest centroid
    private void assignDriversToClusters() {
        clusters.clear();
        for (Driver centroid : centroids) {
            clusters.put(centroid, new ArrayList<>());
        }
        for (Driver driver : drivers) {
            double minDistance = Double.MAX_VALUE;
            Driver nearestCentroid = null;
            for (Driver centroid : centroids) {
                double distance = calculateDistance(driver, centroid);
                if (distance < minDistance) {
                    minDistance = distance;
                    nearestCentroid = centroid;
                }
            }
            clusters.get(nearestCentroid).add(driver);
        }
    }

    // Update centroids
    private void updateCentroids() {
        List<Driver> newCentroids = new ArrayList<>();
        for (Map.Entry<Driver, List<Driver>> entry : clusters.entrySet()) {
            List<Driver> cluster = entry.getValue();
            double sumX = 0;
            double sumY = 0;
            for (Driver driver : cluster) {
                sumX += driver.getLocation().getX();
                sumY += driver.getLocation().getY();
            }
            double newX = sumX / cluster.size();
            double newY = sumY / cluster.size();
            newCentroids.add(new Driver(new Location(newX, newY)));
        }
        centroids = newCentroids;
    }

    // Check convergence - độ lệch giữa 2 centroid gần nhau
    private boolean hasConverged(List<Driver> oldCentroids, List<Driver> newCentroids, double threshold) {
        for (int i = 0; i < oldCentroids.size(); i++) {
            if (calculateDistance(oldCentroids.get(i), newCentroids.get(i)) > threshold) {
                return false;
            }
        }
        return true;
    }

    // Run the K-means clustering algorithm
    public void run(int maxIterations, double threshold) {
        initializeCentroids();
        for (int i = 0; i < maxIterations; i++) {
            List<Driver> oldCentroids = new ArrayList<>(centroids);
            assignDriversToClusters();
            updateCentroids();
            if (hasConverged(oldCentroids, centroids, threshold)) {
                break;
            }
        }
    }

    // Get the clusters
    public Map<Driver, List<Driver>> getClusters() {
        return clusters;
    }

    // public static void main(String[] args) {
    // List<Driver> drivers = Arrays.asList(
    // new Driver(new Location(1, 1)),
    // new Driver(new Location(2, 1)),
    // new Driver(new Location(4, 3)),
    // new Driver(new Location(5, 4)));

    // int k = 2;
    // KMeansClustering kMeans = new KMeansClustering(k, drivers);
    // kMeans.run(100, 0.01);

    // Map<Driver, List<Driver>> clusters = kMeans.getClusters();
    // for (Map.Entry<Driver, List<Driver>> entry : clusters.entrySet()) {
    // System.out.println(
    // "Centroid: " + entry.getKey().getLocation().getX() + ", " +
    // entry.getKey().getLocation().getY());
    // for (Driver driver : entry.getValue()) {
    // System.out.println("Driver: " + driver.getLocation().getX() + ", " +
    // driver.getLocation().getY());
    // }
    // }
    // }
}