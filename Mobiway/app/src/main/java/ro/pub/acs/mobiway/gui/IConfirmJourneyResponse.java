package ro.pub.acs.mobiway.gui;

import ro.pub.acs.mobiway.rest.model.Location;

public interface IConfirmJourneyResponse {

    void onStartJourneyOK(Location location);
    void onStartJourneyCancelled();
}
