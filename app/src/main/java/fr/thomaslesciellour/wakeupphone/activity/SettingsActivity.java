package fr.thomaslesciellour.wakeupphone.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.SwitchPreference;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import fr.thomaslesciellour.wakeupphone.R;
import fr.thomaslesciellour.wakeupphone.activity.appcompat.AppCompatPreferenceActivity;
import fr.thomaslesciellour.wakeupphone.manager.DataManager;

public class SettingsActivity extends AppCompatPreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private final static String TAG = "SettingsActivity";
    private EditTextPreference mSmsTriggerView;
    private SwitchPreference mVibratePermissionView;
    private EditTextPreference mVibrateOnView;
    private EditTextPreference mVibrateOffView;
    private ListPreference mSoundView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.activity_settings);
        ActionBar actionBar = getSupportActionBar();

        actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        mSmsTriggerView = (EditTextPreference) findPreference(DataManager.TRIGGER_KEY);
        mVibratePermissionView = (SwitchPreference) findPreference(DataManager.VIBRATE_PERMISSION_KEY);
        mVibrateOnView = (EditTextPreference) findPreference(DataManager.VIBRATE_ON_KEY);
        mVibrateOffView = (EditTextPreference) findPreference(DataManager.VIBRATE_OFF_KEY);
        mSoundView = (ListPreference) findPreference(DataManager.ALERT_SOUND_KEY);

        mSmsTriggerView.setSummary(DataManager.getTrigger(this));
        mSmsTriggerView.setText(DataManager.getTrigger(this));
        mVibratePermissionView.setChecked(DataManager.getVibrationPermission(this));
        mVibrateOnView.setSummary(Long.toString(DataManager.getVibrateOnDuration(this)));
        mVibrateOnView.setText(Long.toString(DataManager.getVibrateOnDuration(this)));
        mVibrateOffView.setSummary(Long.toString(DataManager.getVibrateOffDuration(this)));
        mVibrateOffView.setText(Long.toString(DataManager.getVibrateOffDuration(this)));
        mSoundView.setSummary(getString(R.string.alert_sound_default));
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(DataManager.TRIGGER_KEY)) {
            mSmsTriggerView.setSummary(DataManager.getTrigger(this));
        }
        else if (key.equals(DataManager.VIBRATE_ON_KEY)) {
            mVibrateOnView.setSummary(Long.toString(DataManager.getVibrateOnDuration(this)));
        }
        else if (key.equals(DataManager.VIBRATE_OFF_KEY)) {
            mVibrateOffView.setSummary(Long.toString(DataManager.getVibrateOffDuration(this)));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return false;
    }
}
