package me.truongng.journeymapapi.models;

public class Ride {
    private String id;
    private Customer customer;
    private Driver driver;
    private Location pickupLocation;
    private Location dropoffLocation;
    private Config.RideStatus status;

    public Ride() {
    }

    public Ride(
            Customer customer,
            Driver driver,
            Location pickupLocation,
            Location dropoffLocation,
            Config.RideStatus status) {
        this.customer = customer;
        this.driver = driver;
        this.pickupLocation = pickupLocation;
        this.dropoffLocation = dropoffLocation;
        this.status = status;
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