package fr.thomaslesciellour.wakeupphone.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import fr.thomaslesciellour.wakeupphone.R;
import fr.thomaslesciellour.wakeupphone.manager.DataManager;

public class TriggerActivity extends AppCompatActivity implements View.OnClickListener {

    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trigger);

        findViewById(R.id.next_activity).setOnClickListener(this);

        mSharedPreferences = getPreferences(MODE_PRIVATE);
        if (mSharedPreferences.getBoolean(DataManager.TRIGGER_BOOL_KEY, false))
            nextActivity();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.next_activity) {
            saveTrigger();
            nextActivity();
        }
    }

    private void saveTrigger()
    {
        String trigger = ((EditText) findViewById(R.id.trigger)).getText().toString();

        if (!"".equals(trigger)) {
            SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
            sharedPreferences.edit().putString(DataManager.TRIGGER_KEY, trigger).commit();
        }
    }

    private void nextActivity()
    {
        mSharedPreferences.edit().putBoolean(DataManager.TRIGGER_BOOL_KEY, true).commit();
        startActivity(new Intent(this, SMSPermissionActivity.class));
        finish();
    }
}
