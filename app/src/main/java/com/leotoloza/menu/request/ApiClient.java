package com.leotoloza.menu.request;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.leotoloza.menu.modelo.Contrato;
import com.leotoloza.menu.modelo.Inmueble;
import com.leotoloza.menu.modelo.LoginModel;
import com.leotoloza.menu.modelo.Pago;
import com.leotoloza.menu.modelo.Propietario;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public class ApiClient {
    public static final String URLBASE = "http://192.168.0.106:5000/";
    private static ApiInmobiliaria apiInmobiliaria;

    public static ApiInmobiliaria getApiInmobiliaria() {
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URLBASE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        apiInmobiliaria = retrofit.create(ApiInmobiliaria.class);
        return apiInmobiliaria;
    }


    public interface ApiInmobiliaria {
        @POST("api/Login")
        Call<LoginModel> login(@Body LoginModel loginModel);

        @GET("api/Inmueble")
        Call<List<Inmueble>> getInmuebles(@Header("Authorization") String token);

        @GET("api/Propietario")
        Call<Propietario> getPerfil(@Header("Authorization") String token);

        @PUT("api/Propietario/actualizar")
        Call<Propietario> editarPerfil(@Header("Authorization") String token, @Body Propietario propietario);

        @PATCH("api/Inmueble/habilitar/{id}")
        Call<Inmueble> habilitarInmueble(@Header("Authorization") String token, @Path("id") int id);

        @Multipart
        @POST("api/Inmueble/guardar")
        Call<Inmueble> CargarInmueble(@Header("Authorization") String token,
                                      @Part("direccion") RequestBody direccion,
                                      @Part("uso") RequestBody uso,
                                      @Part("tipoInmuebleid") RequestBody tipoInmuebleid,
                                      @Part("ambientes") RequestBody ambientes,
                                      @Part("coordenadas") RequestBody coordenadas,
                                      @Part("precio") RequestBody precio,
                                      @Part("descripcion") RequestBody descripcion,
                                      @Part MultipartBody.Part imagen);

        @GET("api/contrato/alquilados")
        Call<List<Contrato>> inmueblesAlquilados(@Header("Authorization") String token);

        @GET("api/pago")
        Call<List<Pago>> pagosPorContrato(@Header("Authorization") String token);
        @POST("api/recovery")
        @FormUrlEncoded
        Call<String> enviarMail(@Field("email") String email);

    }
}
