package fr.thomaslesciellour.wakeupphone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import fr.thomaslesciellour.wakeupphone.R;
import fr.thomaslesciellour.wakeupphone.service.RegistrationIntentService;

public class ReadyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ready);

        LinearLayout ll = (LinearLayout) findViewById(R.id.ll_ready);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.breathe_animation);

        ll.startAnimation(animation);

        startService(new Intent(this, RegistrationIntentService.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return false;
    }
}
