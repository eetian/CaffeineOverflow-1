package com.caffeineoverflow.utils;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RecipeApiService {
    @GET("recipes/search")
    @Headers({
            "x-rapidapi-host: spoonacular-recipe-food-nutrition-v1.p.rapidapi.com",
            "x-rapidapi-key: API_KEY"
    })
    Call<TopResults> getTopResults(@Query("query") String foodName);
}
