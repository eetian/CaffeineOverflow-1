package com.caffeineoverflow.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.caffeineoverflow.R;
import com.caffeineoverflow.utils.OnItemClickListener;
import com.caffeineoverflow.utils.RecipeApiService;
import com.caffeineoverflow.utils.Result;
import com.caffeineoverflow.utils.ResultListAdapter;
import com.caffeineoverflow.utils.TopResults;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RecipeActivity extends AppCompatActivity {

    static final String TAG = RecipeActivity.class.getSimpleName();
    static final String BASE_URL = "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/";


    private RecyclerView recyclerView;
    List<Result> results = new ArrayList<>();
    ResultListAdapter resultListAdapter;
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        recyclerView = findViewById(R.id.rvRecipeList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        resultListAdapter = new ResultListAdapter(results, new OnItemClickListener() {
            @Override
            public void onItemClick(Result result) {
                System.out.println("MIA      OnItemClick: " + result.getTitle());
                Intent intent = new Intent(getApplicationContext(), DetailedRecipeActivity.class);
                intent.putExtra("recipeId", result.getId());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(resultListAdapter);

        // TODO FIXME. Add search box
        // Call this method when user searches sth
        getRecipes("pasta");
    }

    private void getRecipes(String drinkName) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        RecipeApiService recipeApiService = retrofit.create(RecipeApiService.class);
        Call<TopResults> call = recipeApiService.getTopResults(drinkName);
        System.out.println("MIA   " + call.request().toString());
        System.out.println("Here");

        call.enqueue(new Callback<TopResults>() {
            @Override
            public void onResponse(Call<TopResults> call, Response<TopResults> response) {
                results.clear();
                results.addAll(response.body().getResults());
                resultListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<TopResults> call, Throwable throwable) {
                Log.e(TAG, throwable.toString());
            }
        });
    }

}
