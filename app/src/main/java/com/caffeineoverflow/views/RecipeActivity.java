package com.caffeineoverflow.views;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.caffeineoverflow.R;
import com.caffeineoverflow.utils.Result;
import com.caffeineoverflow.utils.ResultListAdapter;

import java.util.ArrayList;
import java.util.List;

public class RecipeActivity extends AppCompatActivity {

    static final String TAG = RecipeActivity.class.getSimpleName();
    static final String BASE_URL = "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/";

    final static String API_KEY = "360db5380533df750100fadb2ae9c770";

    private RecyclerView recyclerView;
    List<Result> results = new ArrayList<>();
    ResultListAdapter resultListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        recyclerView = findViewById(R.id.rvRecipeList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        resultListAdapter = new ResultListAdapter(results);
        recyclerView.setAdapter(resultListAdapter);
    }

}
