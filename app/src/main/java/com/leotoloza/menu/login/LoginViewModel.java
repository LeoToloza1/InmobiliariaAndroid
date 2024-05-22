package com.leotoloza.menu.login;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.leotoloza.menu.MainActivity;
import com.leotoloza.menu.modelo.LoginModel;
import com.leotoloza.menu.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {
    private Context contexto;
    private MutableLiveData<String> mensajeError;
    private MutableLiveData<Boolean> autenticadoPreviamente;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        contexto = application.getApplicationContext();
    }
    public MutableLiveData<Boolean> getAutenticadoPreviamente() {
        if (autenticadoPreviamente == null) {
            autenticadoPreviamente = new MutableLiveData<>();
            verificarAutenticacionPrevia();
        }
        return autenticadoPreviamente;
    }

    public MutableLiveData<String> getMensajeError() {
        if (mensajeError == null) {
            mensajeError = new MutableLiveData<>();
        }
        return mensajeError;
    }

    public void login(String email, String password) {
        LoginModel propietario = new LoginModel(email, password);
        ApiClient.ApiInmobiliaria endpoint = ApiClient.getApiInmobiliaria();
        Call<LoginModel> llamada = endpoint.login(propietario);
        llamada.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (response.isSuccessful()) {
                    guardarDatosPropietario(email, password);
                    LoginModel respuestaLogin = response.body();
                    String token = respuestaLogin.getTokenGenerado();
                    propietario.setToken(token);
                    guardarToken(token);
                    establecerAutenticacionPrevia(true);
                    Intent intent = new Intent(contexto, MainActivity.class);
                    intent.putExtra("token", token);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    contexto.startActivity(intent);
                } else {
                    mensajeError.setValue(response.message());
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                mensajeError.setValue(t.getMessage());
            }
        });
    }

    private void guardarToken(String token) {
        SharedPreferences sp = contexto.getSharedPreferences("tokenInmobiliaria", 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("tokenAcceso", token);
        editor.commit();
    }

    private void guardarDatosPropietario(String email, String password) {
        SharedPreferences sp = contexto.getSharedPreferences("datosPropietario", 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("emailPropietario", email);
        editor.putString("passwordPropietario", password);
        editor.commit();
    }


    private void verificarAutenticacionPrevia() {
        SharedPreferences sp = contexto.getSharedPreferences("autenticacionBiometrica", 0);
        boolean autenticado = sp.getBoolean("autenticadoPreviamente", false);
        autenticadoPreviamente.setValue(autenticado);
    }

    private void establecerAutenticacionPrevia(boolean autenticado) {
        SharedPreferences sp = contexto.getSharedPreferences("autenticacionBiometrica", 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("autenticadoPreviamente", autenticado);
        editor.commit();
    }
}
