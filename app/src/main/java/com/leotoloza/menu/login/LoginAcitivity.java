package com.leotoloza.menu.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import com.leotoloza.menu.R;
import com.leotoloza.menu.databinding.ActivityLoginAcitivityBinding;

public class LoginAcitivity extends AppCompatActivity {
private ActivityLoginAcitivityBinding binding;
private Intent intent;
private LoginViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginAcitivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(LoginViewModel.class);
        Button btnLogin=binding.btnLogin;
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.EditEmail.getText().toString();
                String pass = binding.EditPass.getText().toString();
                viewModel.login(email,pass);
            }
        });
        viewModel.getAccessTokenLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.d("SALIDA", "LIENA 40 onChanged: --->"+s);
            }
        });
        viewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.d("SALIDA", "LINEA46 onChanged: --->"+s);
            }
        });
        viewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                Log.d("SALIDA", "LIENA 52 onChanged: --->"+aBoolean);
            }
        });

    }
}