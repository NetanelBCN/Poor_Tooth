package com.example.hw1.Utilities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MyLocationManager {
    private Location locationGPS;
    private android.location.LocationManager locationManager;
    private LocationListener locationListener;
    private double userLat;
    private double userLon;
    private Context context;
    public final double DEFAULT_LAT = 31.771959;
    public final double DEFAULT_LON = 35.217018;

    public MyLocationManager(Context context) {
        this.context = context;
    }

    public Location getLocationGPS() {
        return locationGPS;
    }

    public void findUserLocation() {
        do {
            ListenToLocation();
        } while (getUserLon() == 0.0 && getUserLat() == 0.0);
    }

    public double getUserLat() {
        return userLat;
    }

    public double getUserLon() {
        return userLon;
    }

    public Context getContext() {
        return context;
    }

    public void askLocationPermissions(Activity activity) {
        if (ActivityCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.context, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            }

            if (ContextCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
    }

    private void ListenToLocation() {
        locationManager = (android.location.LocationManager) this.context.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            userLat = DEFAULT_LAT;
            userLon = DEFAULT_LON;
            return;
        } else {
            locationGPS = locationManager.getLastKnownLocation(android.location.LocationManager.GPS_PROVIDER);
            if (locationGPS == null) {
                requestLocationUpdate();
            } else {
                userLon = locationGPS.getLongitude();
                userLat = locationGPS.getLatitude();
            }
            while (userLon == 0.0 && userLat == 0.0)
                requestLocationUpdate();
        }
    }

    private void requestLocationUpdate() {
        if (locationManager != null) {
            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {
                    handleLocation(location);
                    locationManager.removeUpdates(this);
                }
            };

            if (ActivityCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.requestLocationUpdates(
                    android.location.LocationManager.GPS_PROVIDER,
                    0, // Minimum time interval between updates in milliseconds
                    0, // Minimum distance between updates in meters
                    locationListener
            );
        }
    }

    private void handleLocation(Location location) {
        userLon = locationGPS.getLongitude();
        userLat = locationGPS.getLatitude();
    }

    public LocationManager getLocationManager() {
        return locationManager;
    }

    public LocationListener getLocationListener() {
        return locationListener;
    }

    public void destroyUpdates() {
        locationManager.removeUpdates(locationListener);
    }
}
