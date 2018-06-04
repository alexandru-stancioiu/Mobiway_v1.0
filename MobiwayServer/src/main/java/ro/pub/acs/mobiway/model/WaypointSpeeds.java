package ro.pub.acs.mobiway.model;

import javafx.util.Pair;
import ro.pub.acs.mobiway.dao.WaypointMeanSpeedDAO;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WaypointSpeeds {

    Map<Waypoint, Pair<Float, Integer>> waypointToSpeedAndNumberOfMeasurements;

    private WaypointMeanSpeedDAO waypointMeanSpeedDAO;

    public WaypointSpeeds(WaypointMeanSpeedDAO waypointMeanSpeedDAO) {
        this.waypointMeanSpeedDAO = waypointMeanSpeedDAO;

        waypointToSpeedAndNumberOfMeasurements = new ConcurrentHashMap<>();

        List<WaypointMeanSpeed> waypointMeanSpeeds = waypointMeanSpeedDAO.getAllWaypointMeanSpeeds();

        for (WaypointMeanSpeed waypointMeanSpeed : waypointMeanSpeeds) {
            Waypoint waypoint = new Waypoint(waypointMeanSpeed.getWaypointLat(), waypointMeanSpeed.getWaypointLong(), waypointMeanSpeed.getStreetName());
            waypointToSpeedAndNumberOfMeasurements.put(waypoint,
                    new Pair<>(waypointMeanSpeed.getMeanSpeed(), waypointMeanSpeed.getNumberOfMeasuments()));
        }
    }

    public Map<Waypoint, Pair<Float, Integer>> getWaypointToSpeed() {
        return waypointToSpeedAndNumberOfMeasurements;
    }

    public void resetWaypointSpeeds() {
        waypointToSpeedAndNumberOfMeasurements.clear();
    }
}
