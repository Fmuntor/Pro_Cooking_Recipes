package com.pcr.procookingrecipes.ConexionAPI.Spoonacular;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SpoonacularAPI {
    @GET("recipes/complexSearch")
    Call<APIResponse> searchRecipes(@Query("query") String query, @Query("apiKey") String apiKey);

}
