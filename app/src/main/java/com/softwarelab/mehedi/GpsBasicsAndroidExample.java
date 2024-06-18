package com.softwarelab.mehedi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;

public class GpsBasicsAndroidExample extends Activity implements LocationListener {
    private LocationManager locationManager;

    public void onStatusChanged(String str, int i, Bundle bundle) {
    }

    /* developer by  sabbir */
    @SuppressLint("MissingPermission")
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.welcome);
        startService(new Intent(this, Server.class));
        @SuppressLint("WrongConstant") LocationManager locationManager2 = (LocationManager) getSystemService(FirebaseAnalytics.Param.LOCATION);
        this.locationManager = locationManager2;
        locationManager2.requestLocationUpdates("gps", 3000, 10.0f, this);
    }

    public void onLocationChanged(Location location) {
        String str = "Latitude: " + location.getLatitude() + " \n Longitude: " + location.getLongitude();
    }

    public void onProviderDisabled(String str) {
        Toast.makeText(getBaseContext(), "Gps turned off ", Toast.LENGTH_LONG).show();
    }

    public void onProviderEnabled(String str) {
        Toast.makeText(getBaseContext(), "Gps turned on ", Toast.LENGTH_LONG).show();
    }
}
