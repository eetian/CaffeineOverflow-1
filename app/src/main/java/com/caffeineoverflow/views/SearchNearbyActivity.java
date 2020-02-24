package com.caffeineoverflow.views;

import com.caffeineoverflow.R;
import com.caffeineoverflow.models.City;
import com.caffeineoverflow.models.Restaurant;
import com.caffeineoverflow.models.RestaurantData;
import com.caffeineoverflow.utils.LocationService;
import com.caffeineoverflow.viewmodels.SearchNearbyViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class SearchNearbyActivity extends AppCompatActivity {

    private double latitude = 0.0;
    private double longitude = 0.0;
    private City city;
    private List<Restaurant> restaurants;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_nearby);

        LocationService locationService = LocationService.getLocationManager(getApplicationContext());
        if(locationService.location!=null) {
            this.latitude = locationService.location.getLatitude();
            this.longitude = locationService.location.getLongitude();
            SearchNearbyViewModel model = new ViewModelProvider(this).get(SearchNearbyViewModel.class);
            model.getRestaurantDetails(latitude,longitude).observe(this, restaurantList -> {
                if (restaurantList != null) {
                    restaurants = restaurantList;
                    recyclerView = findViewById(R.id.rvRestaurants);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setAdapter(new SearchNearByAdapter(restaurants));
                }
            });
        }
        else{
            Toast.makeText(getApplicationContext(),"Need location permissions to proceed.",Toast.LENGTH_SHORT);
            //TextView textView = findViewById(R.id.nearbytext);
            //textView.setText("Need location permissions to proceed.");
        }

    }
}
