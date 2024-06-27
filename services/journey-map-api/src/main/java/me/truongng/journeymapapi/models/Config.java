package me.truongng.journeymapapi.models;

public class Config {
    public enum Role {
        CUSTOMER,
        DRIVER,
        ADMIN
    }

    public enum RideStatus {
        REQUESTED,
        ACCEPTED,
        IN_PROGRESS,
        COMPLETED,
        CANCELLED
    }

    public enum VehicleType {
        MOTORBIKE,
        CAR4,
        CAR7
    }
}
