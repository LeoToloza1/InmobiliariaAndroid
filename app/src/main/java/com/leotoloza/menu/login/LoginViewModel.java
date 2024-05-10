package com.leotoloza.menu.login;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.leotoloza.menu.ApiClient;
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
        isLoading.setValue(true);
        Propietario propietario = new Propietario(email, password);
        ApiClient apiClient = ApiClient.retrofit.create(ApiClient.class);
        Call<String> call = apiClient.login(propietario);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                isLoading.setValue(false);
                if (response.isSuccessful()) {
                    accessTokenLiveData.setValue(response.body());

                    Log.d("salida", "onResponse: --->"+response.body());
                } else {
                    errorMessage.setValue("Credenciales inválidas");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                isLoading.setValue(false);
                errorMessage.setValue("Error de conexión");
            }
        });
    }

}

