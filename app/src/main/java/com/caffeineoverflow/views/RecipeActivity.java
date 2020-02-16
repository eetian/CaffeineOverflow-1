package com.caffeineoverflow.views;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.caffeineoverflow.R;
import com.caffeineoverflow.utils.Result;
import com.caffeineoverflow.utils.ResultListAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class RecipeActivity extends AppCompatActivity {

    static final String TAG = RecipeActivity.class.getSimpleName();
    static final String BASE_URL = "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/";

    final static String API_KEY = "360db5380533df750100fadb2ae9c770";

    private RecyclerView recyclerView;
    List<Result> results = new ArrayList<>();
    ResultListAdapter resultListAdapter;
    OkHttpClient client = new OkHttpClient();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        getArecipe(1076217);
        recyclerView = findViewById(R.id.rvRecipeList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        resultListAdapter = new ResultListAdapter(results);
        recyclerView.setAdapter(resultListAdapter);

    }

    public static void printJson(String uglyJson) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(uglyJson);
        String prettyJsonString = gson.toJson(je);
        Log.d("DEBUG", prettyJsonString);
        Log.d("DEBUG", uglyJson);
    }

    private void getArecipe(int recipeId) {

        Request request = new Request.Builder()
                .url("https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/search?query=mocha")
                .get()
                .addHeader("x-rapidapi-host", "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com")
                .addHeader("x-rapidapi-key", "f5f17108cfmsh651e47d752cbfd2p13124cjsn9ee6f39b357a")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                System.out.println(response.body().string().toString());
            }
        });
    }

}
