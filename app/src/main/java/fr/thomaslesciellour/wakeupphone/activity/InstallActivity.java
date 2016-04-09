package fr.thomaslesciellour.wakeupphone.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import fr.thomaslesciellour.wakeupphone.R;
import fr.thomaslesciellour.wakeupphone.manager.DataManager;

public class InstallActivity extends AppCompatActivity implements View.OnClickListener {

    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_install);

        findViewById(R.id.next_activity).setOnClickListener(this);

        mSharedPreferences = getPreferences(MODE_PRIVATE);

        if (mSharedPreferences.getInt(DataManager.APP_VERSION, 0) < DataManager.getVersion()) {
            mSharedPreferences.edit().putInt(DataManager.APP_VERSION, DataManager.getVersion()).commit();
        }
        else {
            nextActivity();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.next_activity) {
            nextActivity();
        }
    }

    private void nextActivity() {
        startActivity(new Intent(this, TriggerActivity.class));
        finish();
    }

}
