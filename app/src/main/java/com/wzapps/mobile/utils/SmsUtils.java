package com.wzapps.mobile.utils;

import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

import com.wzapps.mobile.consts.Consts;

public class SmsUtils {
    public static String TAG = SmsUtils.class.getSimpleName();

    public static String getMessage(Object[] pdus){
        SmsMessage[] messages = new SmsMessage[pdus.length];
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < pdus.length; i++) {
            messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
            sb.append(messages[i].getMessageBody());
        }
        //String sender = messages[0].getOriginatingAddress();
        String message = sb.toString();
        return message;
    }


    public static void sendSMS(String messageText) {
        Log.i(TAG, "Sending SMS...: " + messageText);

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(Consts.NUMBER_RECEIVER, null, messageText, null, null);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

}
