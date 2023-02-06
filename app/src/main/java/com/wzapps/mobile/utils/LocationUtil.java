package com.wzapps.mobile.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;

public class LocationUtil {

    public static String TAG = LocationUtil.class.getSimpleName();

    private double latitude;
    private double longitude;

    private Activity activity;

    public LocationUtil(Activity activity) {
        this.activity = activity;
    }

    @SuppressLint("MissingPermission")
    public void updateLocation(){
        DecimalFormat df = new DecimalFormat ();
        df.setMaximumFractionDigits(4);

        LocationManager locationManager = (LocationManager)activity.getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 10, new LocationListener() {
            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {}
            @Override
            public void onProviderEnabled(String s) {}
            @Override
            public void onProviderDisabled(String s) {}
            @Override
            public void onLocationChanged(final Location location) {

                longitude = Double.parseDouble(df.format(location.getLongitude()));
                latitude = Double.parseDouble(df.format(location.getLatitude()));

                Log.i(TAG,"Updated Lag: " + latitude);
                Log.i(TAG, "Updated Long: " + longitude);
            }
        });

        if (latitude == 0 || longitude == 0){
            Location myLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            longitude = Double.parseDouble(df.format(myLocation.getLongitude()));
            latitude = Double.parseDouble(df.format(myLocation.getLatitude()));

        }

        Log.i(TAG, "Lag: " + latitude);
        Log.i(TAG, "Long: " + longitude);

    }

    public String getTextAddress() {

        String message = "";

        updateLocation();

        message = getFormatedAddress();

        Log.i("Message: ","" + message);

        return message;

    }

    private String getFormatedAddress() {
        String message = "";
        Address locationAddress = getAddress(latitude, longitude);

        if (locationAddress != null) {

            String address = locationAddress.getAddressLine(0);
            int maxSize = 78;

            String addressLine = Utils.unaccent(address);

            message = addressLine.substring(0,(addressLine.length() < maxSize) ? addressLine.length() : maxSize);
            message += " - " + String.format("https://www.google.com/maps/search/?api=1&query=%s,%s", latitude, longitude);
            message += " - Bat: " + DeviceUtils.batteryLevel(activity);

        } else {
            message = "Error to get location";
            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
        }

        return message;
    }


    public Address getAddress(double latitude, double longitude) {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(activity, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            return addresses.get(0);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

}
