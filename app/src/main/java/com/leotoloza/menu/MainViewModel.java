package com.leotoloza.menu;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.leotoloza.menu.modelo.Inmueble;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends AndroidViewModel {

        private MutableLiveData<List<Inmueble>> inmueblesLiveData;
        private Context context;
        public MainViewModel(@NonNull Application application) {
            super(application);
            context = application.getApplicationContext();
        }

        public MutableLiveData<List<Inmueble>> getInmueblesLiveData() {
            if (inmueblesLiveData == null) {
                inmueblesLiveData = new MutableLiveData<>();
            }
            return inmueblesLiveData;
        }

        public String recuperarToken() {
            SharedPreferences sp = context.getSharedPreferences("tokenInmobiliaria", 0);
            String token = sp.getString("tokenAcceso", null);
            if (token != null) {
                cargarInmuebles();
                return token;
            } else {
                mostrarMensajeError("Token vencido, por favor inicie sesión nuevamente");
            return null;
            }
        }

        private void cargarInmuebles() {
            ApiClient.ApiInmobiliaria endpoint = ApiClient.getApiInmobiliaria();
            Call<List<Inmueble>> listaInmuebles = endpoint.getInmuebles(recuperarToken());
            listaInmuebles.enqueue(new Callback<List<Inmueble>>() {
                @Override
                public void onResponse(Call<List<Inmueble>> call, Response<List<Inmueble>> response) {

                }

                @Override
                public void onFailure(Call<List<Inmueble>> call, Throwable t) {

                }
            });
        }

        private void mostrarMensajeError(String mensaje) {
            Toast.makeText(context, mensaje, Toast.LENGTH_LONG).show();
        }
    }