package com.wzapps.mobile.listeners;

import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import com.wzapps.mobile.activities.GLocationActivity;
import com.wzapps.mobile.consts.Consts;

public class GNotificationListener extends NotificationListenerService {
    public static String TAG = GNotificationListener.class.getSimpleName();

    @Override
    public void onCreate() {
        Log.d(TAG, "Created");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);

        Log.d(TAG, "Notification has arrived");
        Log.d(TAG, "ID: " + sbn.getId() + " Posted by: " + sbn.getPackageName() + " at: " + sbn.getPostTime() + " ");

        Bundle bundle = sbn.getNotification().extras;

        if (sbn.getPackageName().equalsIgnoreCase("com.google.android.gm")) {
            if(bundle.get(Consts.NOTIFICATION_SUBTEXT).toString().equals(Consts.EMAIL_SENDER) && bundle.get(Consts.NOTIFICATION_TEXT).toString().equalsIgnoreCase(Consts.TEXT_TRACKER)){
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), GLocationActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }

//        for (String key : sbn.getNotification().extras.keySet()) {
//            if (sbn.getNotification().extras.get(key) != null){
//                Log.i(TAG, key + "=" + sbn.getNotification().extras.get(key).toString());
//            }
//        }

    }

}
