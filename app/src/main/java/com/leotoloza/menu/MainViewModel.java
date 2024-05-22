package com.leotoloza.menu;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.leotoloza.menu.Servicios.Dialogo;
import com.leotoloza.menu.Servicios.ToastPesonalizado;
import com.leotoloza.menu.login.LoginActivity;
import com.leotoloza.menu.modelo.Inmueble;
import com.leotoloza.menu.request.ApiClient;
import com.leotoloza.menu.ui.Perfil.PerfilViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends AndroidViewModel implements Dialogo.CambioContraseñaListener{
    private int OPCION_CONTRASEÑA = R.id.action_settings;
    private int OPCION_SALIR = R.id.action_logout;
    private MutableLiveData<Integer> _menuItemSelection;
    private LiveData<Integer> menuItemSelectionLiveData;
    private MutableLiveData<Integer> menuAction;
    private Context context;
        public MainViewModel(@NonNull Application application) {
            super(application);
            this.context=getApplication().getApplicationContext();
        }

    public LiveData<Integer> getMenuItemSelectionLiveData() {
        if(menuItemSelectionLiveData==null)menuItemSelectionLiveData=new MutableLiveData<>();
        return menuItemSelectionLiveData;
    }
    public void onMenuItemSelected(int itemId) {
        if(_menuItemSelection==null)_menuItemSelection=new MutableLiveData<>();
        _menuItemSelection.postValue(itemId);
    }
    public LiveData<Integer> getMenuActionLiveData() {
        if(menuAction==null)menuAction=new MutableLiveData<>();
        return menuAction;
    }
    public void setContext(Context context) {
        this.context = context;
    }
    public void cerrarSesion(){
        SharedPreferences sp = context.getSharedPreferences("tokenInmobiliaria",0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("tokenAcceso","");
        editor.commit();
    }
    public void handleMenuAction(int actionId) {
        if (actionId == OPCION_CONTRASEÑA) {
            if (context instanceof Activity && !((Activity) context).isFinishing()) {
                Dialogo.mostrarDialogoConEntrada(context, "Cambio de contraseña", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ToastPesonalizado.mostrarMensaje(context, "Cambiando contraseña");
                    }
                }, this);
            }
        } else if (actionId == OPCION_SALIR) {
            if (context instanceof Activity && !((Activity) context).isFinishing()) {
                Dialogo.mostrarDialogoConfirmacion(context, "Cerrar Sesión", "¿Estás seguro que deseas cerrar sesión?", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cerrarSesion();
                        Intent intent = new Intent(context, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(intent);
                    }
                });
            }
        }
    }
    @Override
    public void onAceptar(String nuevaContraseña) {
        PerfilViewModel.cambiarPassword(nuevaContraseña);
        cerrarSesion();
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }
    }