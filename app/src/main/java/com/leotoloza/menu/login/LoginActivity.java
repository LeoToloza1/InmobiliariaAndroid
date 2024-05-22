package com.leotoloza.menu.login;

import static com.leotoloza.menu.Servicios.ToastPesonalizado.mostrarMensaje;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.leotoloza.menu.MainActivity;
import com.leotoloza.menu.R;
import com.leotoloza.menu.Servicios.ToastPesonalizado;
import com.leotoloza.menu.databinding.ActivityLoginAcitivityBinding;

import java.util.concurrent.Executor;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginAcitivityBinding binding;
    private Intent intent;
    private LoginViewModel viewModel;
    private LlamarViewModel viewModelLlamada;
    private BiometricaViewModel biometricaViewModel;
    private llamadaBroadcast broadcast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginAcitivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.titulo.setText("Bienvenido de nuevo a: InmobiliApp");
        pedirPermiso();
        registrarBrodcast();
        SharedPreferences sp = this.getSharedPreferences("datosPropietario", this.MODE_PRIVATE);
        String email = sp.getString("emailPropietario", "");
        String password = sp.getString("passwordPropietario", "");

        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(LoginViewModel.class);
        viewModelLlamada =ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(LlamarViewModel.class);
        biometricaViewModel =ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(BiometricaViewModel.class);
        biometricaViewModel.getAutenticadoConHuella().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

            }
        });

        binding.recuperarPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(LoginActivity.this, RecuperarPassActivity.class);
                startActivity(intent);
            }
        });
        viewModel.getMensajeError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.mensaje.setText(s);
                ToastPesonalizado.mostrarMensaje(getApplicationContext(), s);
            }
        });

        biometricaViewModel.getBiometricoSoportado().observe(this, new Observer<Boolean>() {
    @Override
         public void onChanged(Boolean aBoolean) {

        }
        });
        //TODO mejorar para que la primera vez sea con login normal y luego use la huella
        biometricaViewModel.getAutenticadoConHuella().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean autenticado) {
                viewModel.login(email, password);
            }
        });
        Button btnLogin = binding.btnLogin;
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.EditEmail.getText().toString();
                String pass = binding.EditPass.getText().toString();
                viewModel.login(email, pass);
            }
        });
        biometricaViewModel.inicializarBiometria(LoginActivity.this);
viewModelLlamada.getDeteccionAgitado().observe(this, new Observer<Boolean>() {
    @Override
    public void onChanged(Boolean aBoolean) {
        llamadaBroadcast.appIniciada = false;

    }
});

    }

    public void registrarBrodcast(){
        this.broadcast = new llamadaBroadcast();
        Log.d("salida","Aca llega el metodo");
        registerReceiver(broadcast,new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }
    public void pedirPermiso() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 2500);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcast);
    }
}