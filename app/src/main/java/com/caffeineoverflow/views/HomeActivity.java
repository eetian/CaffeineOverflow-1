package com.caffeineoverflow.views;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.caffeineoverflow.R;
import com.caffeineoverflow.utils.LocationService;

import java.util.Locale;


public class HomeActivity extends AppCompatActivity{

    private LocationManager locationManager=null;
    private LocationListener locationListener=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions( this, new String[] {  android.Manifest.permission.ACCESS_COARSE_LOCATION  },
                    LocationService.MY_PERMISSION_ACCESS_COURSE_LOCATION );
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

    }

    public void onClickRecepieBtn(View view){
        //create an explicit intent to call Receipe Activity
        Intent intent = new Intent(this, RecipeActivity.class);
        startActivity(intent);

    }

    public void onClickSearchNearbyBtn(View view){
        //call the API service to search for the nearby coffee shops
        Intent intent = new Intent(this,SearchNearbyActivity.class);
        startActivity(intent);
    }

    public void onClickLogBtn(View view){
        //create an explicit intent to call Log Activity
        Intent intent = new Intent(this, LogActivity.class);
        startActivity(intent);
    }

    public void onClickCalculateBtn(View view){
        //create an explicit intent to call Calculate Activity
//        Intent intent = new Intent(this,ResultActivity.class);
//        startActivity(intent);
    }


}

