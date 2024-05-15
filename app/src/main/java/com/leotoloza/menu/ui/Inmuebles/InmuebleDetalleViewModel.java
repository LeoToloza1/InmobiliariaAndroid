package com.leotoloza.menu.ui.Inmuebles;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.leotoloza.menu.modelo.Inmueble;
import com.leotoloza.menu.modelo.TipoInmueble;

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
        //TODO hacer traer el string del tipo de inmueble correspondiente.
        if(inmueble != null){
            if(inmueble.getTipo() == null) {
                TipoInmueble tipoInmueble = new TipoInmueble();
                tipoInmueble.setId(inmueble.getTipoInmuebleId());
                Log.d("salida", "recuperarInmueble: "+tipoInmueble.getTipo());
                inmueble.setTipo(tipoInmueble);
            }
            inmuebleMutableLiveData.setValue(inmueble);
        }
    }

}
