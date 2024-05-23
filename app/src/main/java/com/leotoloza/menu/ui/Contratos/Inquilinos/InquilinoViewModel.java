package com.leotoloza.menu.ui.Contratos.Inquilinos;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.leotoloza.menu.Servicios.ToastPesonalizado;
import com.leotoloza.menu.modelo.Contrato;
import com.leotoloza.menu.modelo.Inmueble;
import com.leotoloza.menu.modelo.Inquilino;

public class InquilinoViewModel extends AndroidViewModel{
    private Context context;
    private MutableLiveData<Inquilino> inquilinos;
    private ToastPesonalizado toast;
    public InquilinoViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public MutableLiveData<Inquilino> getInquilinos() {
        if(inquilinos==null){
            inquilinos=new MutableLiveData<>();
        }
        return inquilinos;
    }

    public void recuperarInquilino(Bundle bundle){
        Contrato contrato = (Contrato) bundle.get("contrato");
        inquilinos.postValue(contrato.getInquilino());
    }


}
