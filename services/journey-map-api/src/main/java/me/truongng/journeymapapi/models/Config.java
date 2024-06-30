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

    public static boolean isValidRole(String input) {
        for (Role role : Role.values()) {
            if (role.name().equals(input)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isValidRideStatus(String input) {
        for (RideStatus status : RideStatus.values()) {
            if (status.name().equals(input)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isValidVehicleType(String input) {
        for (VehicleType type : VehicleType.values()) {
            if (type.name().equals(input)) {
                return true;
            }
        }
        return false;
    }
}
