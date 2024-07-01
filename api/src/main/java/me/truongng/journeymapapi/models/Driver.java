package me.truongng.journeymapapi.models;

public class Driver {
    private String id;
    private Location location;
    private Config.VehicleType vehicleType;
    private User user;
    private int rating;
    private double revenue;
    private double kilometers_driven;

    public Driver() {
    }

    public Driver(
            Location location,
            Config.VehicleType vehicleType,
            User user,
            int rating,
            double revenue,
            double kilometers_driven) {
        this.location = location;
        this.vehicleType = vehicleType;
        this.user = user;
        this.rating = rating;
        this.revenue = revenue;
        this.kilometers_driven = kilometers_driven;
    }

    public Driver(Location location) {
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getVehicleType() {
        switch (vehicleType) {
            case CAR4:
                return "CAR4";
            case MOTORBIKE:
                return "MOTORBIKE";
            case CAR7:
                return "CAR7";
            default:
                return "MOTORBIKE";
        }
    }

    public void setVehicleType(String vehicleType) {
        switch (vehicleType) {
            case "CAR4":
                this.vehicleType = Config.VehicleType.CAR4;
                break;
            case "MOTORBIKE":
                this.vehicleType = Config.VehicleType.MOTORBIKE;
                break;
            case "CAR7":
                this.vehicleType = Config.VehicleType.CAR7;
                break;
            default:
                this.vehicleType = Config.VehicleType.MOTORBIKE;
                break;
        }
    }

    public Integer getUserId() {
        return user == null ? null : Integer.parseInt(user.getId());
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    public double getKilometersDriven() {
        return kilometers_driven;
    }

    public void setKilometers_driven(double kilometers_driven) {
        this.kilometers_driven = kilometers_driven;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "id='" + id + '\'' +
                ", location=" + location +
                ", userId='" + getId() + '\'' +
                ", rating=" + rating +
                ", revenue=" + revenue +
                ", kilometers_driven=" + kilometers_driven +
                '}';
    }
}
