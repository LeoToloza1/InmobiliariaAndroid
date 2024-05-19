package com.leotoloza.menu.ui.Contratos;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.leotoloza.menu.Servicios.ToastPesonalizado;
import com.leotoloza.menu.modelo.Contrato;
import com.leotoloza.menu.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContratoViewModel extends AndroidViewModel {

    private MutableLiveData<List<Contrato>> contratos;
    private ToastPesonalizado toast;
    private Context context;
    public ContratoViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public MutableLiveData<List<Contrato>> getContratos() {
        if(contratos ==null){
            contratos = new MutableLiveData<>();
        }
        return contratos;
    }

public void getListContratos(){
  String token = recuperarToken();
    ApiClient.ApiInmobiliaria endpoint = ApiClient.getApiInmobiliaria();
    Call<List<Contrato>> listaContrato = endpoint.inmueblesAlquilados(token);
    listaContrato.enqueue(new Callback<List<Contrato>>() {
        @Override
        public void onResponse(Call<List<Contrato>> call, Response<List<Contrato>> response) {
            if(response.isSuccessful()){
                toast.mostrarMensaje(context,"Trayendo el listado de contratos");
                contratos.postValue(response.body());
            }else{
toast.mostrarMensaje(context,"Ocurrio un error"+response.message());
                Log.d("salida", "Ocurrio un error"+response.message());
            }
        }
        @Override
        public void onFailure(Call<List<Contrato>> call, Throwable t) {
            toast.mostrarMensaje(context,"Ocurrio un error"+t.getMessage());
        }
    });
}
    private String recuperarToken() {
        SharedPreferences sp = context.getSharedPreferences("tokenInmobiliaria", 0);
        return "Bearer "+sp.getString("tokenAcceso", null);
    }


}