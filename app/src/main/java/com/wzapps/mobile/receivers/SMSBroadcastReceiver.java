package com.wzapps.mobile.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.wzapps.mobile.activities.GLocationActivity;
import com.wzapps.mobile.activities.MainActivity;
import com.wzapps.mobile.consts.Consts;
import com.wzapps.mobile.utils.LocationUtil;
import com.wzapps.mobile.utils.SmsUtils;


public class SMSBroadcastReceiver extends BroadcastReceiver {
    public static String TAG = SMSBroadcastReceiver.class.getSimpleName();

    private MainActivity mainActivity;
    private LocationUtil locationUtil;
    public SMSBroadcastReceiver(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.locationUtil = new LocationUtil(mainActivity);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "Receiving sms");

        mainActivity.brCallback("param"); //calling activity method

        if (intent.getAction().equals(Consts.SMS_RECEIVED)) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                // get sms objects
                Object[] pdus = (Object[]) bundle.get("pdus");
                if (pdus.length == 0) {
                    return;
                }
                // large message might be broken into many

                String message = SmsUtils.getMessage(pdus);
                Toast.makeText(context, "Message received: " + message, Toast.LENGTH_SHORT).show();

                if (message.trim().equalsIgnoreCase(Consts.TEXT_TRACKER)) {
                    Intent pi = new Intent();
                    pi.setClass(mainActivity, GLocationActivity.class);
                    pi.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mainActivity.startActivity(intent);
                }

            }
        }

    }


}