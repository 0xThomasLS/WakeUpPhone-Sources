package fr.thomaslesciellour.wakeupphone.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import fr.thomaslesciellour.wakeupphone.R;

public class SMSPermissionActivity extends AppCompatActivity implements View.OnClickListener {

    private final static int SMS_PERMISSION_REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smspermission);

        findViewById(R.id.next_activity).setOnClickListener(this);

        if (checkSMSPermission())
            nextActivity();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.next_activity) {
            requestSMSPermission();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        boolean permissionGranted = true;

        for (int i=0; i<grantResults.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                permissionGranted = false;
                break;
            }
        }

        // Permission : SMS
        if (requestCode == SMS_PERMISSION_REQUEST_CODE) {
            // Demande de permission acceptée
            if (permissionGranted) {
                nextActivity();
            }

            // Demande de permission refusée
            else {
                new AlertDialog.Builder(this)
                        .setTitle(getString(R.string.permission_refused_title))
                        .setMessage(getString(R.string.permission_refused_sms_message))
                        .setNegativeButton(getString(R.string.permission_refused_confirmed_button), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                nextActivity();
                            }
                        })
                        .setPositiveButton(getString(R.string.permission_refused_granted_button), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestSMSPermission();
                            }
                        })
                        .setCancelable(false)
                        .show();
            }
        }
    }

    private void nextActivity() {
        startActivity(new Intent(this, ReadyActivity.class));
        finish();
    }

    private boolean checkSMSPermission()
    {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED)
            return false;
        return true;
    }

    private void requestSMSPermission()
    {
        if (!checkSMSPermission()) {
            ActivityCompat.requestPermissions(this, new String[]{
                    android.Manifest.permission.READ_SMS,
                    android.Manifest.permission.RECEIVE_SMS
            }, SMS_PERMISSION_REQUEST_CODE);
        }
        else
            nextActivity();
    }

}
