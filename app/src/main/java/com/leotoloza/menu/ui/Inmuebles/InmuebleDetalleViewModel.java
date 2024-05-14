package com.leotoloza.menu.ui.Inmuebles;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.leotoloza.menu.modelo.Inmueble;

public class InmuebleDetalleViewModel extends AndroidViewModel {
    private MutableLiveData<Inmueble> inmuebleMutableLiveData;
    private Context context;
    public InmuebleDetalleViewModel(@NonNull Application application) {
        super(application);
    }

public MutableLiveData<Inmueble> getInmuebleMutableLiveData(){
        if(inmuebleMutableLiveData==null){
            inmuebleMutableLiveData = new MutableLiveData<>();
        }
        return inmuebleMutableLiveData;
}
    public void recuperarInmueble(Bundle bundle){
        Inmueble inmueble = (Inmueble) bundle.get("inmueble");
        if(inmueble!=null){
            inmuebleMutableLiveData.setValue(inmueble);
        }
    }

}
