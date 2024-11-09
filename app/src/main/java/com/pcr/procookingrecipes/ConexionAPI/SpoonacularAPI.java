package com.pcr.procookingrecipes.ConexionAPI;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface SpoonacularAPI {
    @GET("recipes/complexSearch")
    Call<APIResponse> searchRecipes(@Query("query") String query, @Query("apiKey") String apiKey);

}
