package ro.pub.acs.mobiway.rest.model;

import java.io.Serializable;
import java.util.Date;

public class Location {

    private Integer idUser;
    private Double latitude;
    private Double longitude;
    private Integer speed;
    private String timestamp;
    private String color;

    public Location() {
    }

    public Location(Integer idUser) {
        this.idUser = idUser;
    }

    public Location(Integer idUser, Double latitude, Double longitude, Integer speed,
                    String timestamp, String color) {
        this.idUser = idUser;
        this.latitude = latitude;
        this.longitude = longitude;
        this.speed = speed;
        this.timestamp = timestamp;
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public Date getTimestamp() {
        return timestamp == null ? null : new Date(Long.parseLong(timestamp));
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = String.valueOf(timestamp.getTime());
    }

    @Override
    public String toString() {
        return "ro.pub.acs.traffic.model.Location[ idUser=" + idUser + " ]";
    }
}