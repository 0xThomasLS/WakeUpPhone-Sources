package fr.thomaslesciellour.wakeupphone.service;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

public class MyGcmListenerService extends GcmListenerService {

    private final static String TAG = "GcmListenerService";

    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");

        Log.i(TAG, from);
        Log.i(TAG, message);
    }
}
