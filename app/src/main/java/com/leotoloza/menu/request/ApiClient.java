package com.leotoloza.menu.request;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.leotoloza.menu.modelo.Inmueble;
import com.leotoloza.menu.modelo.LoginModel;
import com.leotoloza.menu.modelo.Propietario;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public class ApiClient {
    public static final String URLBASE ="http://192.168.0.105:5000/";
    private static ApiInmobiliaria apiInmobiliaria;

    public static ApiInmobiliaria getApiInmobiliaria(){
       Gson gson=new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URLBASE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
   apiInmobiliaria=retrofit.create(ApiInmobiliaria.class);
   return apiInmobiliaria;
    }



    public interface ApiInmobiliaria{
        @POST("api/Login")
        Call<LoginModel> login(@Body LoginModel loginModel);

        @GET("api/Inmueble")
        Call<List<Inmueble>> getInmuebles(@Header("Authorization") String token);

        @GET("api/Propietario")
        Call<Propietario> getPerfil(@Header("Authorization") String token);
    }

}
