package com.caffeineoverflow.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.caffeineoverflow.R;
import com.caffeineoverflow.utils.OnItemClickListener;
import com.caffeineoverflow.repositories.service.RecipeApiService;
import com.caffeineoverflow.models.Result;
import com.caffeineoverflow.utils.ResultListAdapter;
import com.caffeineoverflow.models.TopResults;

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

    Button btn_drinksearch;
    EditText edt_drinkquery;
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

        Intent intent = getIntent();
        String eventName = intent.getStringExtra("eventName");
        edt_drinkquery = findViewById(R.id.edt_drinkquery);

        if ( eventName != null && eventName.length()!=0){
            edt_drinkquery.setText(eventName);
            getRecipes(eventName);
        }



        resultListAdapter = new ResultListAdapter(results, new OnItemClickListener() {
            @Override
            public void onItemClick(Result result) {
                System.out.println("MIA      OnItemClick: " + result.getTitle());
                Intent intent = new Intent(getApplicationContext(), DetailedRecipeActivity.class);
                intent.putExtra("recipeId", result.getId());
                startActivity(intent);
            }
        });
//        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(resultListAdapter);

        edt_drinkquery = findViewById(R.id.edt_drinkquery);

        // Add event listener for the add event button
        btn_drinksearch = (Button) findViewById(R.id.btn_drinksearch);
        btn_drinksearch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String drinkQueryStr = edt_drinkquery.getText().toString();
                System.out.println("MIA         drinkQueryStr: "+drinkQueryStr);
                getRecipes(drinkQueryStr);
            }
        });

//        // TODO FIXME. Add search box ---- FINISHED
//        // Call this method when user searches sth
//        getRecipes("pasta");
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
