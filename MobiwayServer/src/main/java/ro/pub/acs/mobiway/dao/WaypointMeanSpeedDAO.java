package ro.pub.acs.mobiway.dao;

import ro.pub.acs.mobiway.model.StreetMeanSpeed;
import ro.pub.acs.mobiway.model.WaypointMeanSpeed;

import java.util.List;

public interface WaypointMeanSpeedDAO {

    List<WaypointMeanSpeed> getAllWaypointMeanSpeeds();

    WaypointMeanSpeed getWaypointMeanSpeed(Double latitude, Double longitude);

    List<WaypointMeanSpeed> getWaypointMeanSpeedByStreetName(String streetName);

    void saveOrUpdateWaypointMeanSpeed(WaypointMeanSpeed waypointMeanSpeed);

    void clearAllRows();
}
