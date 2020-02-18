package com.caffeineoverflow.repositories.remote.services;

import com.caffeineoverflow.models.DetailedRecipe;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface DetailedRecipeApiService {
    @GET("recipes/{id}/information")
    @Headers({
            "x-rapidapi-host: spoonacular-recipe-food-nutrition-v1.p.rapidapi.com",
            "x-rapidapi-key: f5f17108cfmsh651e47d752cbfd2p13124cjsn9ee6f39b357a"
    })
    Call<DetailedRecipe> getDetailedRecipe(@Path("id") String id);
}
