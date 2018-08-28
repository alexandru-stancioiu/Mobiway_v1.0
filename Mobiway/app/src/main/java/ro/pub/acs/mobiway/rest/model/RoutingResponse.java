package ro.pub.acs.mobiway.rest.model;

import java.util.List;

public class RoutingResponse {

    private List<Location> routePoints;

    private double estimatedTravelTime;

    public List<Location> getRoutePoints() {
        return routePoints;
    }

    public void setRoutePoints(List<Location> routePoints) {
        this.routePoints = routePoints;
    }

    public double getEstimatedTravelTime() {
        return estimatedTravelTime;
    }

    public void setEstimatedTravelTime(double estimatedTravelTime) {
        this.estimatedTravelTime = estimatedTravelTime;
    }
}
