package com.leotoloza.menu.login;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.leotoloza.menu.MainActivity;
import com.leotoloza.menu.modelo.LoginModel;
import com.leotoloza.menu.request.ApiClient;

import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<Boolean> biometricoSoportado;
    private MutableLiveData<String> mensajeAuth;
    private MutableLiveData<String> errorMessage;
    private MutableLiveData<Boolean> autenticadoConHuella;
    private BiometricPrompt biometricPrompt;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public MutableLiveData<Boolean> getAutenticadoConHuella() {
        if (autenticadoConHuella == null) autenticadoConHuella = new MutableLiveData<>();
        return autenticadoConHuella;
    }

    public MutableLiveData<Boolean> getBiometricoSoportado() {
        if (biometricoSoportado == null) biometricoSoportado = new MutableLiveData<>();
        return biometricoSoportado;
    }

    public MutableLiveData<String> getMensajeAuth() {
        if (mensajeAuth == null) mensajeAuth = new MutableLiveData<>();
        return mensajeAuth;
    }

    public MutableLiveData<String> getErrorMessage() {
        if (errorMessage == null) errorMessage = new MutableLiveData<>();
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
                    guardarDatosPropietario(email, password);
                    LoginModel loginResponse = response.body();
                    String token = loginResponse.getTokenGenerado();
                    propietario.setToken(token);
                    guardarToken(token);
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra("token", token);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } else {
                    errorMessage.setValue(response.message());
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                errorMessage.setValue(t.getMessage());
            }
        });
    }

    private void guardarToken(String token) {
        SharedPreferences sp = context.getSharedPreferences("tokenInmobiliaria", 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("tokenAcceso", token);
        editor.commit();
    }

    private void guardarDatosPropietario(String email, String password) {
        SharedPreferences sp = context.getSharedPreferences("datosPropietario", 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("guardado", true);
        editor.putString("emailPropietario", email);
        editor.putString("passwordPropietario", password);
        editor.commit();
    }

    public void inicializarBiometria(Context context) {
        Executor executor = ContextCompat.getMainExecutor(context);
        biometricPrompt = new BiometricPrompt((AppCompatActivity) context, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                mensajeAuth.setValue("Error de autenticación: " + errString);
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                autenticadoConHuella.setValue(true);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                mensajeAuth.setValue("Autenticación fallida");
            }
        });

        leerHuella(context);
    }


    public BiometricPrompt.PromptInfo infoAuth(Context context) {
        return new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Autenticación Biométrica")
                .setSubtitle("Inicie sesión utilizando su huella digital")
                .setNegativeButtonText("Cancelar")
                .build();
    }

    public void leerHuella(Context contexto) {
        BiometricManager biometricManager = BiometricManager.from(contexto);
        switch (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG | BiometricManager.Authenticators.DEVICE_CREDENTIAL)) {
            case BiometricManager.BIOMETRIC_SUCCESS:
                biometricoSoportado.setValue(true);
                if (biometricPrompt != null) {
                    BiometricPrompt.PromptInfo promptInfo = infoAuth(contexto);
                    biometricPrompt.authenticate(promptInfo);
                }
                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                mensajeAuth.setValue("No hay hardware biométrico disponible en este dispositivo.");
                biometricoSoportado.setValue(false);
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                mensajeAuth.setValue("El hardware biométrico no está disponible actualmente.");
                biometricoSoportado.setValue(false);
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                mensajeAuth.setValue("No hay datos biométricos registrados. Registre al menos una huella digital en la configuración del dispositivo.");
                biometricoSoportado.setValue(false);
                break;
        }
    }
}
