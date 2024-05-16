package com.leotoloza.menu.ui.Inmuebles;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.leotoloza.menu.Servicios.ToastPesonalizado;
import com.leotoloza.menu.modelo.Inmueble;
import com.leotoloza.menu.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NuevoInmuebleViewModel  extends AndroidViewModel {
    private MutableLiveData<Inmueble> inmuebleMutableLiveData;
    private Context context;
    private ToastPesonalizado toast;
    public NuevoInmuebleViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }
    public MutableLiveData<Inmueble> getInmuebleMutableLiveData(){
        if(inmuebleMutableLiveData==null){
            inmuebleMutableLiveData = new MutableLiveData<>();
        }
        return inmuebleMutableLiveData;
    }

    public void agregarInmueble(Inmueble inmueble){
    String token = recuperarToken();
        if (token != null) {
            ApiClient.ApiInmobiliaria endpoint = ApiClient.getApiInmobiliaria();
            Call<Inmueble> call = endpoint.altaInmueble(token,inmueble);
            call.enqueue(new Callback<Inmueble>() {
                @Override
                public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                    inmuebleMutableLiveData.setValue(response.body());
                    Log.d("salida", "onResponse habilitado: "+response.body());
                    toast.mostrarMensaje(context,"Su inmueble se agregó correctamente");
                }
                @Override
                public void onFailure(Call<Inmueble> call, Throwable t) {
                    toast.mostrarMensaje(context,"No se pudo agregar el inmueble:" +t.getMessage());
                }
            });
        } else {
            toast.mostrarMensaje(context,"Token vencido, por favor inicie sesión nuevamente");
        }
    }
    private String recuperarToken() {
        SharedPreferences sp = context.getSharedPreferences("tokenInmobiliaria", 0);
        return "Bearer "+sp.getString("tokenAcceso", null);
    }
}
