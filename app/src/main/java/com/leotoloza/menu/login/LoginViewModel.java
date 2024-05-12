package com.leotoloza.menu.login;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.leotoloza.menu.ApiClient;
import com.leotoloza.menu.modelo.LoginModel;
import com.leotoloza.menu.modelo.Propietario;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {

    private MutableLiveData<String> accessTokenLiveData;
    private MutableLiveData<Boolean> isLoading;
    private MutableLiveData<String> errorMessage;

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<String> getAccessTokenLiveData() {
        if (accessTokenLiveData == null) {
            accessTokenLiveData = new MutableLiveData<>();
        }
        return accessTokenLiveData;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        if (isLoading == null) {
            isLoading = new MutableLiveData<>();
        }
        return isLoading;
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
             }else{
                 Log.d("salida", "siFallo: "+response.message());
             }
         }
            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Log.d("salida", "onFailure: "+t.getMessage());
            }
     });
    }
}

