package com.caffeineoverflow.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.caffeineoverflow.R;
import com.caffeineoverflow.models.DetailedRecipe;
import com.caffeineoverflow.repositories.service.DetailedRecipeApiService;
import com.caffeineoverflow.models.Ingredient;
import com.caffeineoverflow.utils.IngridentListAdapter;
import com.caffeineoverflow.utils.OnIngredientClickListener;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class DetailedRecipeActivity extends AppCompatActivity {

    static final String TAG = RecipeActivity.class.getSimpleName();
    static final String BASE_URL = "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/";


    private RecyclerView recyclerView;
    DetailedRecipe detailedRecipe;
    ArrayList<Ingredient> ingredients = new ArrayList<>();
    IngridentListAdapter ingridentListAdapter;
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        recyclerView = findViewById(R.id.ingridentList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Intent intent = getIntent();
        String recipeId = intent.getStringExtra("recipeId");

        ingridentListAdapter = new IngridentListAdapter(ingredients, new OnIngredientClickListener() {
            @Override
            public void onIngredientClick(Ingredient ingredient) {
                System.out.println(ingredient.getName());
//                Toast.makeText(getApplicationContext(), ingredient.getName(), Toast.LENGTH_LONG).show();
            }
        });
        recyclerView.setAdapter(ingridentListAdapter);

        // Call this method when user searches sth
        getDetailRecipe(recipeId);
    }

    private void getDetailRecipe(String id) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        DetailedRecipeApiService recipeDetailApiService = retrofit.create(DetailedRecipeApiService.class);
        Call<DetailedRecipe> call = recipeDetailApiService.getDetailedRecipe(id);
        System.out.println("MIA   " + call.request().toString());

        call.enqueue(new Callback<DetailedRecipe>() {
            @Override
            public void onResponse(Call<DetailedRecipe> call, Response<DetailedRecipe> response) {
                detailedRecipe = response.body();
                ingredients.clear();
                ingredients.addAll(detailedRecipe.getExtendedIngredients());

                TextView recipeNameTv = (TextView)findViewById(R.id.recipeTitle);
                recipeNameTv.setText(detailedRecipe.getTitle());
                TextView recipeIdTv = (TextView)findViewById(R.id.recipeId);
                recipeIdTv.setText(detailedRecipe.getId());
                TextView recipeInstructionTv = (TextView)findViewById(R.id.recipeInstruction);
                recipeInstructionTv.setText(detailedRecipe.getInstructions());

                // Set ingredients
                ingridentListAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<DetailedRecipe> call, Throwable t) {
                System.out.println("DetailedRecipe API call fails");
            }
        });
    }

}
