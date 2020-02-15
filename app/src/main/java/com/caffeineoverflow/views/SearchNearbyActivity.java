package com.caffeineoverflow.views;

import com.caffeineoverflow.R;
import com.caffeineoverflow.utils.LocationService;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class SearchNearbyActivity extends AppCompatActivity {

    private double latitude = 0.0;
    private double longitude = 0.0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_nearby);

        LocationService locationService = LocationService.getLocationManager(getApplicationContext());
        if(locationService.location!=null) {
            this.latitude = locationService.location.getLatitude();
            this.longitude = locationService.location.getLongitude();
        }
        else{
            TextView textView = findViewById(R.id.nearbytext);
            textView.setText("Need location permissions to proceed.");
        }

    }
}
