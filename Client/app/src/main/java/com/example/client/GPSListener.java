package com.example.client;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.math.BigDecimal;

public class GPSListener extends Service implements LocationListener {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private Context mContext;
    private LocationManager locationManager;

    private String homeLatitude;
    private String homeLongitude;

    private Location homeLocation;

    private boolean isInHome = false;

    private final float eventDiameter = 3;

    private Location location;

    public GPSListener() {
    }

    public GPSListener(Context context) {
        this.mContext = context;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = preferences.edit();

        homeLatitude = preferences.getString("home_latitude","0");
        homeLongitude = preferences.getString("home_longitude","0");

        homeLocation = new Location("home");
        homeLocation.setLatitude(Double.parseDouble(homeLatitude));
        homeLocation.setLongitude(Double.parseDouble(homeLongitude));

        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.e("permission","fail");
            return START_NOT_STICKY;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        location.getProvider();
        // calculate distance
        //Log.e("onLocationChanged",Double.toString(homeLocation.distanceTo(location)));
        if(homeLocation.distanceTo(location)<eventDiameter){//in home
            if(!isInHome){
                Log.e("onLocationChanged","in home");
                isInHome = true;
                //request home in alarm
                HttpRequest httpRequest = new HttpRequest(getApplicationContext());
                httpRequest.execute("homeInPost");
            }
        }
        else{
            if(isInHome){
               isInHome = false;
               Log.e("onLocationChanged","out home");
               //request home out alarm
               HttpRequest httpRequest = new HttpRequest(getApplicationContext());
               httpRequest.execute("homeOutPost");
            }
        }



    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
