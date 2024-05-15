package com.leotoloza.menu.ui.Inmuebles;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.leotoloza.menu.modelo.Inmueble;
import com.leotoloza.menu.modelo.TipoInmueble;
import com.leotoloza.menu.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InmuebleDetalleViewModel extends AndroidViewModel {
    private MutableLiveData<Inmueble> inmuebleMutableLiveData;
    private MutableLiveData<Boolean> camposEditablesLiveData;
    private MutableLiveData<String> textoBotonLiveData;
    private MutableLiveData<Boolean> clickBtn;
    private boolean editable;
    private Context context;

    public InmuebleDetalleViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        editable = false;
    }

    public MutableLiveData<Boolean> getClickBtn(){
        if(clickBtn==null){
            clickBtn=new MutableLiveData<>();
        }
        return clickBtn;
    }
public MutableLiveData<Inmueble> getInmuebleMutableLiveData(){
        if(inmuebleMutableLiveData==null){
            inmuebleMutableLiveData = new MutableLiveData<>();
        }
        return inmuebleMutableLiveData;
}
    public LiveData<String> getTextoBotonLiveData() {
        if (textoBotonLiveData == null) {
            textoBotonLiveData = new MutableLiveData<>();
            textoBotonLiveData.setValue("Editar");
        }
        return textoBotonLiveData;
    }
    public void cambiarTextoBoton() {
        if (editable) {
            textoBotonLiveData.setValue("Guardar");
        } else {
            textoBotonLiveData.setValue("Editar");
        }
    }
    public LiveData<Boolean> getCamposEditablesLiveData() {
        if (camposEditablesLiveData == null) {
            camposEditablesLiveData = new MutableLiveData<>();
            camposEditablesLiveData.setValue(editable);
        }
        return camposEditablesLiveData;
    }
    public void habilitarCampos() {
        editable = !editable;
        camposEditablesLiveData.setValue(editable);
    }
    public void recuperarInmueble(Bundle bundle){
        Inmueble inmueble = (Inmueble) bundle.get("inmueble");
//        Log.d("salida", "recuperarInmueble: "+inmueble.toString());
            inmuebleMutableLiveData.setValue(inmueble);
        }
    public void habilitarInmueble(Bundle bundle) {
        Inmueble inmuebleRecuperado = (Inmueble) bundle.get("inmueble");
//        Log.d("salida", "habilitarInmueble: "+inmuebleRecuperado.toString());
        String token = recuperarToken();
        if (token != null) {
            ApiClient.ApiInmobiliaria endpoint = ApiClient.getApiInmobiliaria();
            Call<Inmueble> call = endpoint.habilitarInmueble(token, inmuebleRecuperado.getId());
            call.enqueue(new Callback<Inmueble>() {
                @Override
                public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                    inmuebleMutableLiveData.setValue(response.body());
                    Log.d("salida", "onResponse habilitado: "+response.body());
                    mostrarMensajeError("Su inmueble se actualizó correctamente");
                }

                @Override
                public void onFailure(Call<Inmueble> call, Throwable t) {
            mostrarMensajeError("Ocurrio un error al actualizar el inmueble:" +t.getMessage());
                }
            });
        } else {
            mostrarMensajeError("Token vencido, por favor inicie sesión nuevamente");
        }
    }
    private String recuperarToken() {
        SharedPreferences sp = context.getSharedPreferences("tokenInmobiliaria", 0);
        return "Bearer "+sp.getString("tokenAcceso", null);
    }
    private void mostrarMensajeError(String mensaje) {
        Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show();
    }

}


