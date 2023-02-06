package com.wzapps.mobile.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import com.wzapps.mobile.R;
import com.wzapps.mobile.listeners.GNotificationListener;
import com.wzapps.mobile.receivers.SMSBroadcastReceiver;
import com.wzapps.mobile.utils.PermissionUtils;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements PermissionUtils.PermissionResultCallback {

    public static String TAG = MainActivity.class.getSimpleName();

    @Override
    public void onStart() {
        super.onStart();
        SMSBroadcastReceiver smsReceiver = new SMSBroadcastReceiver(this); //passing context
        IntentFilter filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(smsReceiver, filter);
        //startActivity(new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS));
        startService(new Intent(this, GNotificationListener.class));
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermissions();
        setContentView(R.layout.activity_main);
    }

    private void checkPermissions(){
        PermissionUtils permissionUtils = new PermissionUtils(MainActivity.this);
        ArrayList<String> permissions=new ArrayList<>();

        permissions.add(Manifest.permission.RECEIVE_SMS);
        permissions.add(Manifest.permission.READ_SMS);
        permissions.add(Manifest.permission.SEND_SMS);
        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);

        permissionUtils.check_permission(permissions,"Need permissions for getting your location",1);
    }

    @Override
    public void PermissionGranted(int request_code) {
        Log.i("PERMISSION","GRANTED");
    }

    @Override
    public void PartialPermissionGranted(int request_code, ArrayList<String> granted_permissions) {
        Log.i("PERMISSION PARTIALLY","GRANTED");
    }

    @Override
    public void PermissionDenied(int request_code) {
        Log.i("PERMISSION","DENIED");
    }

    @Override
    public void NeverAskAgain(int request_code) {
        Log.i("PERMISSION","NEVER ASK AGAIN");
    }

    public void brCallback(String param){
        Log.d("BroadcastReceiver",param);
    }

}