package fr.thomaslesciellour.wakeupphone.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

import fr.thomaslesciellour.wakeupphone.R;

public class RegistrationIntentService extends IntentService {

    private final static String TAG = "RegistrationService";

    public RegistrationIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {

            InstanceID instanceID = InstanceID.getInstance(this);
            String token = instanceID.getToken(getString(R.string.gcm_defaultSenderId), GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);

            Log.i(TAG, token);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
