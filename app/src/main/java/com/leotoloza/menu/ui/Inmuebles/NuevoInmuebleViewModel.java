package com.leotoloza.menu.ui.Inmuebles;

import static android.app.Activity.RESULT_OK;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.leotoloza.menu.Servicios.ToastPesonalizado;
import com.leotoloza.menu.modelo.Inmueble;
import com.leotoloza.menu.request.ApiClient;

import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NuevoInmuebleViewModel extends AndroidViewModel {
    private MutableLiveData<Inmueble> inmuebleMutableLiveData;
    private MutableLiveData<Uri> uriMutableLiveData;
    private MutableLiveData<File> mFotoFile;
    private File directorio;

    private Context context;
    private ToastPesonalizado toast;

    public NuevoInmuebleViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
        directorio = context.getFilesDir();
    }

    public LiveData<Uri> getUriMutable() {
        if (uriMutableLiveData == null) {
            uriMutableLiveData = new MutableLiveData<>();
        }
        return uriMutableLiveData;
    }

    public void recibirFoto(ActivityResult result, Context context) {
        if (result.getResultCode() == RESULT_OK) {
            Intent data = result.getData();
            Uri uri = data.getData();
            uriMutableLiveData.setValue(uri);
        }
    }

    public MutableLiveData<Inmueble> getInmuebleMutableLiveData() {
        if (inmuebleMutableLiveData == null) {
            inmuebleMutableLiveData = new MutableLiveData<>();
        }
        return inmuebleMutableLiveData;
    }

    public LiveData<File> getMFotoFile() {
        if (mFotoFile == null) {
            mFotoFile = new MutableLiveData<>();
        }
        return mFotoFile;
    }

    // Método para agregar un inmueble con imagen al servidor
    public void agregarInmuebleConImagen(Inmueble inmueble, File imagenFile) {
        inmueble.setFotoFile(imagenFile);
        String token = recuperarToken();
        if (token != null) {
            ApiClient.ApiInmobiliaria endpoint = ApiClient.getApiInmobiliaria();
            Call<Inmueble> call = endpoint.altaInmueble(token, inmueble);
            call.enqueue(new Callback<Inmueble>() {
                @Override
                public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                    inmuebleMutableLiveData.setValue(response.body());
                    Log.d("salida", "onResponse habilitado: " + response.body());
                    toast.mostrarMensaje(context, "Su inmueble se agregó correctamente");
                }

                @Override
                public void onFailure(Call<Inmueble> call, Throwable t) {
                    toast.mostrarMensaje(context, "No se pudo agregar el inmueble: " + t.getMessage());
                }
            });
        } else {
            toast.mostrarMensaje(context, "Token vencido, por favor inicie sesión nuevamente");
        }
    }

    private String recuperarToken() {
        SharedPreferences sp = context.getSharedPreferences("tokenInmobiliaria", 0);
        return "Bearer " + sp.getString("tokenAcceso", null);
    }
}
