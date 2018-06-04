package ro.pub.acs.mobiway.service;

import javafx.util.Pair;
import org.springframework.scheduling.TaskScheduler;
import ro.pub.acs.mobiway.dao.WaypointMeanSpeedDAO;
import ro.pub.acs.mobiway.model.*;

import java.util.Map;

public class PersistWaypointMeanSpeeds {

    private TaskScheduler taskScheduler;

    //@Autowired
    private WaypointSpeeds waypointSpeeds;

    //@Autowired
    private WaypointMeanSpeedDAO waypointMeanSpeedDAO;

    private class PersistWaypointMeanSpeedsTask implements Runnable {
        @Override
        public void run() {
            waypointMeanSpeedDAO.clearAllRows();

            for (Map.Entry<Waypoint, Pair<Float, Integer>> waypointMeanSpeedEntry : waypointSpeeds.getWaypointToSpeed().entrySet()) {
                Waypoint waypoint = waypointMeanSpeedEntry.getKey();

//                WaypointMeanSpeed waypointMeanSpeedRow = waypointMeanSpeedDAO.getWaypointMeanSpeed(waypoint.getWaypointLat(), waypoint.getWaypointLong());
//                if (waypointMeanSpeedRow == null) {
                WaypointMeanSpeed waypointMeanSpeedRow = new WaypointMeanSpeed();
                waypointMeanSpeedRow.setStreetName(waypoint.getStreetName());
                waypointMeanSpeedRow.setWaypointLat(waypoint.getWaypointLat());
                waypointMeanSpeedRow.setWaypointLong(waypoint.getWaypointLong());
//                }

                waypointMeanSpeedRow.setMeanSpeed(waypointMeanSpeedEntry.getValue().getKey());
                waypointMeanSpeedRow.setNumberOfMeasuments(waypointMeanSpeedEntry.getValue().getValue());

                System.out.println(waypointMeanSpeedRow);
                waypointMeanSpeedDAO.saveOrUpdateWaypointMeanSpeed(waypointMeanSpeedRow);
            }
        }
    }

    public PersistWaypointMeanSpeeds(TaskScheduler taskScheduler, WaypointSpeeds waypointSpeeds, WaypointMeanSpeedDAO waypointMeanSpeedDAO) {
        this.taskScheduler = taskScheduler;
        this.waypointSpeeds = waypointSpeeds;
        this.waypointMeanSpeedDAO = waypointMeanSpeedDAO;

        taskScheduler.scheduleAtFixedRate(new PersistWaypointMeanSpeedsTask(), 5 * 60 * 1000);
    }
}
