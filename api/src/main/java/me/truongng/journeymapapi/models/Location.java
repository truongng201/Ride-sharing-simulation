package me.truongng.journeymapapi.models;

import java.util.Objects;

public class Location {
    private double X;
    private double Y;

    public Location() {
    }

    public Location(double X, double Y) {
        this.X = X;
        this.Y = Y;
    }

    public double getX() {
        return X;
    }

    public void setX(double X) {
        this.X = X;
    }

    public double getY() {
        return Y;
    }

    public void setY(double Y) {
        this.Y = Y;
    }

    public double distanceTo(Location newLocation) {
        return Math.sqrt(
                (Y - newLocation.Y) * (Y - newLocation.Y)
                        + (X - newLocation.X) * (X - newLocation.X));
    }

    @Override
    public boolean equals(Object newObject) {
        if (this == newObject)
            return true; // cùng 1 object, hoặc cùng rỗng
        if (newObject == null || getClass() != newObject.getClass())
            return false; // 1 cái rỗng hoặc khác loại class
        Location newLocation = (Location) newObject;
        return Double.compare(newLocation.X, X) == 0
                && Double.compare(newLocation.Y, Y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(X, Y); // hashCode theo giá trị
    }

    @Override
    public String toString() {
        return "Location{" +
                "X=" + X +
                ", Y=" + Y +
                '}';
    }
}
