package ro.pub.acs.mobiway.model;

import javax.persistence.*;

@Entity
@Table(name = "waypoint_mean_speeds")
public class WaypointMeanSpeed {
    private static final long serialVersionUID = 1L;

    public WaypointMeanSpeed() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "street_name")
    private String streetName;

    @Column(name = "mean_speed")
    private Float meanSpeed;

    @Column(name = "number_of_measurements")
    private Integer numberOfMeasuments;

    @Column(name = "waypoint_lat")
    private Double waypointLat;

    @Column(name = "waypoint_long")
    private Double waypointLong;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public Float getMeanSpeed() {
        return meanSpeed;
    }

    public void setMeanSpeed(Float meanSpeed) {
        this.meanSpeed = meanSpeed;
    }

    public Integer getNumberOfMeasuments() {
        return numberOfMeasuments;
    }

    public void setNumberOfMeasuments(Integer numberOfMeasuments) {
        this.numberOfMeasuments = numberOfMeasuments;
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

    public String toString() {
        return streetName + " (" + waypointLat + "," + waypointLong + ") "  + meanSpeed + " km/h";
    }
}
