package com.leotoloza.menu.ui.Inmuebles;

import static android.app.Activity.RESULT_OK;

import static androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.Navigation;

import com.google.gson.Gson;
import com.leotoloza.menu.R;
import com.leotoloza.menu.Servicios.RealPathUtil;
import com.leotoloza.menu.Servicios.ToastPesonalizado;
import com.leotoloza.menu.modelo.Inmueble;
import com.leotoloza.menu.request.ApiClient;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NuevoInmuebleViewModel extends AndroidViewModel {
    private MutableLiveData<Uri> uriMutableLiveData;
    private MutableLiveData<Inmueble> inmuebleMutableLiveData;
    private Context context;
    private ToastPesonalizado toast;
    public NuevoInmuebleViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<Uri> getUriMutable() {
        if (uriMutableLiveData == null) {
            uriMutableLiveData = new MutableLiveData<>();
        }
        return uriMutableLiveData;
    }

    public LiveData<Inmueble> getInmuebleMutableLiveData() {
        if (inmuebleMutableLiveData == null) {
            inmuebleMutableLiveData = new MutableLiveData<>();
        }
        return inmuebleMutableLiveData;
    }

    public void recibirFoto(ActivityResult result, Context context) {
        if (result.getResultCode() == RESULT_OK) {
            Intent data = result.getData();
            Uri uri = data.getData();
                uriMutableLiveData.setValue(uri);
        }
    }

    public void agregarInmuebleConImagen(Inmueble inmueble, Uri imagenUri) {
        if (inmueble.getDireccion().isEmpty() || inmueble.getAmbientes() == 0 || inmueble.getUso().isEmpty() || inmueble.getPrecio() == 0 || imagenUri == null) {
            toast.mostrarMensaje(context, "Por favor complete todos los campos y seleccione una imagen.");
            return;
        }
        String token = recuperarToken();
        ApiClient.ApiInmobiliaria endpoint = ApiClient.getApiInmobiliaria();
        if (token != null) {
            RequestBody inmuebleBody = RequestBody.create(MediaType.parse("application/json"), new Gson().toJson(inmueble));
            String path = RealPathUtil.getRealPath(context, imagenUri);
            File file = new File(path);
            RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part imagePart = MultipartBody.Part.createFormData("img", file.getName(), fileBody);
            RequestBody direccion = RequestBody.create(MediaType.parse("text/plain"), inmueble.getDireccion());
            RequestBody ambientes = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(inmueble.getAmbientes()));
            RequestBody uso = RequestBody.create(MediaType.parse("text/plain"), inmueble.getUso());
            RequestBody tipoInmuebleid = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(inmueble.getTipoInmuebleId()));
            RequestBody coordenadas = RequestBody.create(MediaType.parse("text/plain"), inmueble.getCoordenadas());
            RequestBody precio = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(inmueble.getPrecio()));
            RequestBody descripcion = RequestBody.create(MediaType.parse("text/plain"), inmueble.getDescripcion());

            Call<Inmueble> call = endpoint.CargarInmueble(token, direccion, uso,tipoInmuebleid,ambientes, coordenadas, precio, descripcion, imagePart);
            call.enqueue(new Callback<Inmueble>() {
                @Override
                public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                    if (response.isSuccessful()) {
                        inmuebleMutableLiveData.setValue(response.body());
                        toast.mostrarMensaje(context, "Inmueble agregado correctamente.");

                    } else {
                        toast.mostrarMensaje(context, "Error al agregar el inmueble: " + response.message());
                    }
                }
                @Override
                public void onFailure(Call<Inmueble> call, Throwable t) {
                    toast.mostrarMensaje(context, "Error al agregar el inmueble: " + t.getMessage());
                }
            });
        } else {
            toast.mostrarMensaje(context, "Token vencido, por favor inicie sesión nuevamente.");
        }
    }
    private String recuperarToken() {
        SharedPreferences sp = context.getSharedPreferences("tokenInmobiliaria", 0);
        return "Bearer " + sp.getString("tokenAcceso", null);
    }

//    private boolean tienePermisoParaGaleria() {
//        return ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
//    }
//
//    private void solicitarPermisoParaGaleria() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},10001);
//        }
//    }


}