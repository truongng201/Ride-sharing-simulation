package me.truongng.journeymapapi.models;

public class Ride {
    private String id;
    private Customer customer;
    private Driver driver;
    private Location pickupLocation;
    private Location dropoffLocation;
    private Config.RideStatus status;
    private double fare;
    private double distance;

    public Ride() {
    }

    public Ride(
            String id,
            Customer customer,
            Driver driver,
            Location pickupLocation,
            Location dropoffLocation,
            Config.RideStatus status,
            double fare,
            double distance) {
        this.id = id;
        this.customer = customer;
        this.driver = driver;
        this.pickupLocation = pickupLocation;
        this.dropoffLocation = dropoffLocation;
        this.status = status;
        this.fare = fare;
        this.distance = distance;
    }

    public String getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Location getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(Location pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public Location getDropoffLocation() {
        return dropoffLocation;
    }

    public void setDropoffLocation(Location dropoffLocation) {
        this.dropoffLocation = dropoffLocation;
    }

    public String getStatus() {
        switch (status) {
            case REQUESTED:
                return "REQUESTED";
            case ACCEPTED:
                return "ACCEPTED";
            case COMPLETED:
                return "COMPLETED";
            case CANCELLED:
                return "CANCELLED";
            default:
                return "UNKNOWN";
        }
    }

    public void setStatus(String status) {
        switch (status) {
            case "REQUESTED":
                this.status = Config.RideStatus.REQUESTED;
                break;
            case "ACCEPTED":
                this.status = Config.RideStatus.ACCEPTED;
                break;
            case "COMPLETED":
                this.status = Config.RideStatus.COMPLETED;
                break;
            case "CANCELLED":
                this.status = Config.RideStatus.CANCELLED;
                break;
            default:
                this.status = Config.RideStatus.REQUESTED;
        }
    }

    public double getFare() {
        return fare;
    }

    public void setFare(double fare) {
        this.fare = fare;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "Ride{" +
                "id='" + id + '\'' +
                ", customer=" + customer +
                ", driver=" + driver +
                ", pickupLocation=" + pickupLocation +
                ", dropoffLocation=" + dropoffLocation +
                ", status=" + status +
                '}';
    }
}