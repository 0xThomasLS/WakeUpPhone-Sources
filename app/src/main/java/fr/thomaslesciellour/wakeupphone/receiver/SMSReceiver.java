package fr.thomaslesciellour.wakeupphone.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import fr.thomaslesciellour.wakeupphone.manager.DataManager;

public class SMSReceiver extends BroadcastReceiver {

    private final static String TAG = "SMSReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();

        try {
            if (bundle != null) {
                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++) {
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);

                    if (DataManager.getSmsTrigger(context).equals(currentMessage.getDisplayMessageBody())) {
                        Intent alertIntent = new Intent();
                        alertIntent.setClassName(DataManager.getPackageName(context), DataManager.getPackageName(context) + ".activity.WakeUpActivity");
                        alertIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(alertIntent);
                    }
                }
            }
        } catch (Exception e) {}
    }

}
