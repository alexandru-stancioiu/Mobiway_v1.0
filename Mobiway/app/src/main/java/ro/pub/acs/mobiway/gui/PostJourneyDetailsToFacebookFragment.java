package ro.pub.acs.mobiway.gui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import ro.pub.acs.mobiway.R;
import ro.pub.acs.mobiway.gui.main.IConfirmPostToFacebook;

public class PostJourneyDetailsToFacebookFragment extends DialogFragment {

    private IConfirmPostToFacebook confirmPostToFacebook;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage(R.string.post_journey_info)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        confirmPostToFacebook.postJourneyDetailsToFacebook();                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            confirmPostToFacebook = (IConfirmPostToFacebook) activity;
        }
        catch (ClassCastException e) {
            Log.d("PotJourneyDetailsDialog", "Activity doesn't implement the IConfirmPostToFacebook interface");
        }
    }
}
