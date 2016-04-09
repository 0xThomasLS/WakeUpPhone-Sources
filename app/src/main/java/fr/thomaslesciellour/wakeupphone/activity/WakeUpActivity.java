package fr.thomaslesciellour.wakeupphone.activity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import fr.thomaslesciellour.wakeupphone.R;
import fr.thomaslesciellour.wakeupphone.manager.DataManager;

public class WakeUpActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String TAG = "WUA";

    private static int mVolumeStream;
    private static AudioManager mAudioManager;
    private static MediaPlayer mPlayer;
    private static Vibrator mVibrator;
    private static ImageView mVibrateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wakeup);

        mVibrateView = (ImageView) findViewById(R.id.logo);

        mPlayer = MediaPlayer.create(this, DataManager.getAlertSoundUri(this));

        if (DataManager.getVibrationPermission(this))
            mVibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        findViewById(R.id.logo).setOnClickListener(this);
        startAlert();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.logo) {
            stopAlert();
        }
    }

    private void startAlert()
    {
        mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.wizz_animation);

        mVolumeStream = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
        mPlayer.start();
        mVibrateView.startAnimation(animation);

        if (DataManager.getVibrationPermission(this))
            mVibrator.vibrate(DataManager.getVibratePattern(this), 2);
    }

    private void stopAlert() {
        mPlayer.stop();
        mVibrateView.clearAnimation();
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mVolumeStream, 0);

        if (DataManager.getVibrationPermission(this))
            mVibrator.cancel();

        startActivity(new Intent(this, ReadyActivity.class));
        finish();
    }
}
