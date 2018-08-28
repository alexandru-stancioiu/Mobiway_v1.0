package ro.pub.acs.mobiway.gui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import ro.pub.acs.mobiway.R;
import ro.pub.acs.mobiway.rest.model.Location;

public class ConfirmJourneyFragment extends DialogFragment {

    private IConfirmJourneyResponse confirmJourneyResponse;

    @SuppressLint("StringFormatInvalid")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction

        Double endJourneyLatitude = this.getArguments().getDouble("end_journey_latitude");
        Double endJourneyLongitude = this.getArguments().getDouble("end_journey_longitude");
        final Location endJourney = new Location();
        endJourney.setLatitude(endJourneyLatitude);
        endJourney.setLongitude(endJourneyLongitude);

        Integer arrivalDelay = this.getArguments().getInt("arrival_minutes");
        Date date = new Date();
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, arrivalDelay);

        int arrivalHour = calendar.get(Calendar.HOUR_OF_DAY);
        int arrivalMinutes = calendar.get(Calendar.MINUTE);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        Resources res = getResources();
        String confirmJourney = res.getString(R.string.confirm_journey);
        builder.setMessage(String.format(confirmJourney, arrivalHour, arrivalMinutes))
                .setPositiveButton(R.string.proceed, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        confirmJourneyResponse.onStartJourneyOK(endJourney);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        confirmJourneyResponse.onStartJourneyCancelled();
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            confirmJourneyResponse = (IConfirmJourneyResponse) activity;
        }
        catch (ClassCastException e) {
            Log.d("ConfirmJourneyDialog", "Activity doesn't implement the IConfirmJourneyResponse interface");
        }
    }
}
