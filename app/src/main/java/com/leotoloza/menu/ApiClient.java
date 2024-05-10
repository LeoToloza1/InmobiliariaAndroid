package com.leotoloza.menu;

import com.leotoloza.menu.modelo.Propietario;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.leotoloza.menu.modelo.Propietario;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiClient {
    @POST("api/Login")
    Call<String> login(@Body Propietario propietario);

    Gson gson = new GsonBuilder().setLenient().create();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://localhost:5000/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();
}
