package com.leotoloza.menu;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.leotoloza.menu.modelo.Inmueble;
import com.leotoloza.menu.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends AndroidViewModel {
        public MainViewModel(@NonNull Application application) {
            super(application);
        }


    }