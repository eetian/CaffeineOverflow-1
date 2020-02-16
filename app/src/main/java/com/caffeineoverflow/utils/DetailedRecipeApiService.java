package com.caffeineoverflow.utils;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DetailedRecipeApiService {
    @GET("recipes/{id}/information")
    @Headers({
            "x-rapidapi-host: spoonacular-recipe-food-nutrition-v1.p.rapidapi.com",
            "x-rapidapi-key: RANDOM_API"
    })
    Call<DetailedRecipe> getDetailedRecipe(@Path("id") String id);
}
