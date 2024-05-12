package com.leotoloza.menu.login;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.leotoloza.menu.ApiClient;
import com.leotoloza.menu.MainActivity;
import com.leotoloza.menu.modelo.LoginModel;
import com.leotoloza.menu.modelo.Propietario;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {
    Context context;
    private MutableLiveData<String> errorMessage;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public MutableLiveData<String> getErrorMessage() {
        if (errorMessage == null) {
            errorMessage = new MutableLiveData<>();
        }
        return errorMessage;
    }
    public void login(String email, String password) {
        LoginModel propietario = new LoginModel(email, password);
        ApiClient.ApiInmobiliaria endpoint = ApiClient.getApiInmobiliaria();
        Call<LoginModel> llamada = endpoint.login(propietario);
        llamada.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if (response.isSuccessful()) {
                    LoginModel loginResponse = response.body();
                    String token = loginResponse.getTokenGenerado();
                    propietario.setToken(token);
                    // Manejar el token JWT devuelto por el servidor
                    Log.d("salida", "Token JWT: " + token);
                    guardarSP(token);
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra("token",token);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

             }else{
                    errorMessage.setValue(response.message());
             }
         }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                errorMessage.setValue(t.getMessage());
            }
     });
    }

    private void guardarSP(String token){
    SharedPreferences sp = context.getSharedPreferences("tokenInmobiliaria",0);
    SharedPreferences.Editor editor = sp.edit();
    editor.putString("tokenAcceso",token);
    editor.commit();
    }


}

