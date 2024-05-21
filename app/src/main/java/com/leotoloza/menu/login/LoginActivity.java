package com.leotoloza.menu.login;

import static com.leotoloza.menu.Servicios.ToastPesonalizado.mostrarMensaje;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginAcitivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.titulo.setText("Bienvenido de nuevo a: InmobiliApp");
        SharedPreferences sp = this.getSharedPreferences("datosPropietario", this.MODE_PRIVATE);
        String email = sp.getString("emailPropietario", "");
        String password = sp.getString("passwordPropietario", "");

        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(LoginViewModel.class);

        viewModel.getAutenticadoConHuella().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

            }
        });
        viewModel.getAutenticadoConHuella().observe(this, new Observer<Boolean>() {
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
        viewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.mensaje.setText(s);
            }
        });

        viewModel.getMensajeAuth().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                ToastPesonalizado.mostrarMensaje(getApplicationContext(), s);
            }
        });
viewModel.getBiometricoSoportado().observe(this, new Observer<Boolean>() {
    @Override
    public void onChanged(Boolean aBoolean) {

    }
});
        viewModel.getAutenticadoConHuella().observe(this, new Observer<Boolean>() {
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
        viewModel.inicializarBiometria(LoginActivity.this);
    }
}