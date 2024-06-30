package me.truongng.journeymapapi.models;

import java.util.Objects;

public class Location {
    private double latitude;
    private double longitude;

    public Location() {
    }

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double distanceTo(Location newLocation) {
        return Math.sqrt(
                (longitude - newLocation.longitude) * (longitude - newLocation.longitude)
                        + (latitude - newLocation.latitude) * (latitude - newLocation.latitude));
    }

    @Override
    public boolean equals(Object newObject) {
        if (this == newObject)
            return true; // cùng 1 object, hoặc cùng rỗng
        if (newObject == null || getClass() != newObject.getClass())
            return false; // 1 cái rỗng hoặc khác loại class
        Location newLocation = (Location) newObject;
        return Double.compare(newLocation.latitude, latitude) == 0
                && Double.compare(newLocation.longitude, longitude) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude); // hashCode theo giá trị
    }

    @Override
    public String toString() {
        return "Location{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
