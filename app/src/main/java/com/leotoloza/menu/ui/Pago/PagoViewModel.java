package com.leotoloza.menu.ui.Pago;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.leotoloza.menu.Servicios.ToastPesonalizado;
import com.leotoloza.menu.modelo.Contrato;
import com.leotoloza.menu.modelo.Pago;
import com.leotoloza.menu.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PagoViewModel extends AndroidViewModel {
    private MutableLiveData<List<Pago>> pagos;
//    private Bundle bundle;
    private Context context;

    public PagoViewModel(@NonNull Application application) {
        super(application);
        this.context= application.getApplicationContext();
    }

    public MutableLiveData<List<Pago>> getPagos() {
        if(pagos==null){
            pagos = new MutableLiveData<>();
        }
        return pagos;
    }
public void getListPagos(){
        String token = recuperarToken();
    ApiClient.ApiInmobiliaria endpoint = ApiClient.getApiInmobiliaria();
   Call<List<Pago>> lista= endpoint.pagosPorContrato(token,6);
   lista.enqueue(new Callback<List<Pago>>() {
       @Override
       public void onResponse(Call<List<Pago>> call, Response<List<Pago>> response) {
           if (response.isSuccessful()){
               pagos.postValue(response.body());
//               ToastPesonalizado.mostrarMensaje(context,"Trayendo datos de los pagos");
//               Log.d("salida", "onResponse: "+response.raw());
           }else{
               ToastPesonalizado.mostrarMensaje(context,"Ocurrio un error: "+response.message());
           }
       }
       @Override
       public void onFailure(Call<List<Pago>> call, Throwable t) {
        ToastPesonalizado.mostrarMensaje(context,"Respuesta del servidor: "+t.getMessage());
       }
   });

}

    private String recuperarToken() {
        SharedPreferences sp = context.getSharedPreferences("tokenInmobiliaria", 0);
        return "Bearer "+sp.getString("tokenAcceso", null);
    }


}
