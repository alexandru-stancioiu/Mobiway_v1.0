package ro.pub.acs.mobiway.model;

import java.util.Objects;

public class Waypoint {

    private Double waypointLat;

    private Double waypointLong;

    private String streetName;

    public Waypoint() {
    }

    public Waypoint(Double waypointLat, Double waypointLong, String streetName) {
        this.waypointLat = waypointLat;
        this.waypointLong = waypointLong;
        this.streetName = streetName;
    }

    public Double getWaypointLat() {
        return waypointLat;
    }

    public void setWaypointLat(Double waypointLat) {
        this.waypointLat = waypointLat;
    }

    public Double getWaypointLong() {
        return waypointLong;
    }

    public void setWaypointLong(Double waypointLong) {
        this.waypointLong = waypointLong;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Waypoint waypoint = (Waypoint) o;
        return Objects.equals(waypointLat, waypoint.waypointLat) &&
                Objects.equals(waypointLong, waypoint.waypointLong);
    }

    @Override
    public int hashCode() {

        return Objects.hash(waypointLat, waypointLong);
    }
}
