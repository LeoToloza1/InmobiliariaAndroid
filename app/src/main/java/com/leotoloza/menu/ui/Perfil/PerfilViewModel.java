package com.leotoloza.menu.ui.Perfil;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.common.api.Api;
import com.leotoloza.menu.Servicios.ToastPesonalizado;
import com.leotoloza.menu.modelo.Inmueble;
import com.leotoloza.menu.modelo.Propietario;
import com.leotoloza.menu.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilViewModel extends AndroidViewModel {
    private MutableLiveData<Propietario> mutablePropietario;
    private MutableLiveData<Boolean> camposEditablesLiveData;
    private MutableLiveData<String> textoBotonLiveData;
    private ToastPesonalizado toast;
    private boolean editable;
    private Context context;

    public PerfilViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        editable = false;
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
    public MutableLiveData<Propietario> getMutablePropietario() {
        if (mutablePropietario == null) {
            mutablePropietario = new MutableLiveData<>();
            cargarPerfil();
        }
        return mutablePropietario;
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
    public void cargarPerfil() {
        String token = recuperarToken();
        Log.d("token", "cargarPerfil: "+token);
        if (token != null) {
            ApiClient.ApiInmobiliaria endpoint = ApiClient.getApiInmobiliaria();
            Call<Propietario> prop = endpoint.getPerfil(token);
            prop.enqueue(new Callback<Propietario>() {
                @Override
                public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                    if (response.isSuccessful()) {
                        mutablePropietario.postValue(response.body());
                    } else {
                        mostrarMensajeError("Error al cargar el perfil: " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<Propietario> call, Throwable t) {
                    mostrarMensajeError("Ocurrió un error al consultar su perfil: " + t.getMessage());
                }
            });
        } else {
            mostrarMensajeError("Token vencido, por favor inicie sesión nuevamente");
        }
    }

    public void editarPerfil(Propietario propietario){
        String token = recuperarToken();
        ApiClient.ApiInmobiliaria endpoint = ApiClient.getApiInmobiliaria();
        Call<Propietario> prop = endpoint.editarPerfil(token,propietario);
        prop.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if (response.isSuccessful()) {
                    mutablePropietario.postValue(response.body());
                    toast.mostrarMensaje(context,"Su perfil se Actualizó correctamente");
                } else {
                    toast.mostrarMensaje(context,"Error al Actualizar el perfil: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {
                toast.mostrarMensaje(context,"Ocurrió un error al Actualizar su perfil: " + t.getMessage());
            }
        });


    }
    private String recuperarToken() {
        SharedPreferences sp = context.getSharedPreferences("tokenInmobiliaria", 0);
        return "Bearer "+sp.getString("tokenAcceso", null);
    }

    private void mostrarMensajeError(String mensaje) {
        Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show();
    }




}