package com.wzapps.mobile.utils;

import android.app.Activity;
import android.content.Context;
import android.os.BatteryManager;
import android.util.Log;

public class DeviceUtils {

    public static int batteryLevel(Activity activity){
        BatteryManager bm = (BatteryManager) activity.getSystemService(Context.BATTERY_SERVICE);
        int batLevel = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
        Log.i("Battery", "" + batLevel);
        return  batLevel;
    }


}
