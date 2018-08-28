package ro.pub.acs.mobiway.service;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import ro.pub.acs.mobiway.model.Location;
import ro.pub.acs.mobiway.utils.Constants;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import static ro.pub.acs.mobiway.utils.Constants.FACEBOOK_PAGE_ID;

public class PostJourneyStatusToFacebookTask extends Thread {

    private double meanSpeed;

    private int durationInMillis;

    private List<Location> locations;

    public PostJourneyStatusToFacebookTask(double meanSpeed, int durationInMillis, List<Location> locations) {
        this.meanSpeed = meanSpeed;
        this.durationInMillis = durationInMillis;
        this.locations = locations;
    }

    @Override
    public void run() {

        String duration = null;
        if (durationInMillis < 1000 * 60 * 60) {
            duration = DurationFormatUtils.formatDuration(durationInMillis, "m 'Minutes'", true);
        } else if (durationInMillis < 1000 * 60 * 60 * 24) {
            duration = DurationFormatUtils.formatDuration(durationInMillis, "H 'Hours' m 'Minutes'", true);
        } else {
            duration = DurationFormatUtils.formatDuration(durationInMillis, "d 'Days' H 'Hours' m 'Minutes'", true);
        }

        String meanSpeedKmH = String.format("%.2f", meanSpeed * 3.6);

        String message = String.format("Journey completed in %s with a mean speed of %s km/h", duration, meanSpeedKmH);

        String mapsUrl = "https://maps.googleapis.com/maps/api/staticmap?size=500x500&path=color:0x0000ff|weight:5|";
        for (int i = 0 ; i < locations.size(); i++) {
            Location location = locations.get(i);
            mapsUrl += location.getLatitude() + "," + location.getLongitude();
            if (i != locations.size() - 1) {
                mapsUrl += "|";
            }
        }

        HttpClient httpClient = new DefaultHttpClient();

        StringBuilder url = new StringBuilder();

        url.append("https://graph.facebook.com/");
        url.append(FACEBOOK_PAGE_ID);
        url.append("/feed?");

        try {
            url.append("message=" + URLEncoder.encode(message, "UTF-8"));
            url.append("&link=" + URLEncoder.encode(mapsUrl, "UTF-8")) ;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        url.append("&access_token=");
        url.append(Constants.MOBIWAY_PAGE_ACCESS_TOKEN);

        HttpPost httpPost = new HttpPost(url.toString());

        HttpResponse httpPostResponse = null;
        try {
            httpPostResponse = httpClient.execute(httpPost);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }
}
