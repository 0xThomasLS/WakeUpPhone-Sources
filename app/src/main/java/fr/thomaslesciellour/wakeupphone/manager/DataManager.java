package fr.thomaslesciellour.wakeupphone.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;

import fr.thomaslesciellour.wakeupphone.BuildConfig;
import fr.thomaslesciellour.wakeupphone.R;

public class DataManager {

    public final static String TRIGGER_KEY = "trigger";
    public final static String TRIGGER_BOOL_KEY = "trigger_bool";
    public final static String VIBRATE_PERMISSION_KEY = "vibrate_permission";
    public final static String VIBRATE_ON_KEY = "vibrate_on";
    public final static String VIBRATE_OFF_KEY = "vibrate_off";
    public final static String ALERT_SOUND_KEY = "alert_sound";
    public final static String APP_VERSION = "app_version";

    private final static String TAG = "DataManager";
    private final static String VIBRATE_ON_DURATION = "500";
    private final static String VIBRATE_OFF_DURATION = "1000";

    public static String getTrigger(Context context)
    {
        String defaultSmsTrigger = context.getString(R.string.default_trigger);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String trigger = sharedPreferences.getString(TRIGGER_KEY, defaultSmsTrigger);

        if (trigger.length() == 0)
            return defaultSmsTrigger;

        Log.i("DataManager", trigger);

        return trigger;
    }

    public static String getPackageName(Context context)
    {
        String[] packageName = context.getPackageName().split("\\.");
        StringBuffer buffer = new StringBuffer();
        String strPackage;

        for(int i=0; i<3; i++) {
            buffer.append(packageName[i] + '.');
        }
        strPackage = buffer.substring(0, buffer.length()-1);

        Log.i("DataManager", strPackage);
        return strPackage;
    }

    public static int getVersion()
    {
        return BuildConfig.VERSION_CODE;
    }

    public static boolean getVibrationPermission(Context context)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(VIBRATE_PERMISSION_KEY, true);
    }

    public static long getVibrateOnDuration(Context context)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        try {
            long vibrateOn = Long.parseLong(sharedPreferences.getString(VIBRATE_ON_KEY, VIBRATE_ON_DURATION));
            return vibrateOn;
        }
        catch (NumberFormatException e) {
            return Long.parseLong(VIBRATE_ON_DURATION);
        }
    }

    public static long getVibrateOffDuration(Context context)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        try {
            long vibrateOff = Long.parseLong(sharedPreferences.getString(VIBRATE_OFF_KEY, VIBRATE_OFF_DURATION));
            return vibrateOff;
        }
        catch (NumberFormatException e) {
            return Long.parseLong(VIBRATE_OFF_DURATION);
        }
    }

    public static long[] getVibratePattern(Context context)
    {
        return new long[] {0, getVibrateOnDuration(context), getVibrateOffDuration(context), getVibrateOnDuration(context)};
    }

    public static Uri getAlertSoundUri(Context context)
    {
        String defaultValue = context.getString(R.string.alert_sound_value_default);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String uri = sharedPreferences.getString(ALERT_SOUND_KEY, defaultValue);

        if (defaultValue.equals(uri))
            return Settings.System.DEFAULT_RINGTONE_URI;

        return Uri.parse(uri);
    }

}
