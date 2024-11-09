package com.pcr.procookingrecipes.API;

import com.pcr.procookingrecipes.Usuario.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIService {
    @GET("users")
    Call<List<Usuario>> getUsers();
}

