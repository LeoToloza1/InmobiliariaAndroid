package com.leotoloza.menu.ui.Inmuebles;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leotoloza.menu.R;
import com.leotoloza.menu.databinding.FragmentInmuebleDetalleBinding;
import com.leotoloza.menu.modelo.Inmueble;


public class InmuebleDetalleFragment extends Fragment {
    private FragmentInmuebleDetalleBinding binding;
    private InmuebleDetalleViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInmuebleDetalleBinding.inflate(inflater, container, false);
        viewModel= new ViewModelProvider(this).get(InmuebleDetalleViewModel.class);
        viewModel.getInmuebleMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Inmueble>() {
            @Override
            public void onChanged(Inmueble inmueble) {
            binding.Direccion.setText(inmueble.getDireccion());
            binding.Precio.setText(inmueble.getPrecio()+"");
            binding.TipoInmueble.setText(inmueble.getTipo().getTipo());
            binding.Uso.setText(inmueble.getUso());
            binding.Ambientes.setText(inmueble.getAmbientes());
            boolean estado = inmueble.getEstado().equals("Disponible");
            binding.EstadoSwitch.setChecked(estado);
            binding.Descripcion.setText(inmueble.getDescripcion());
            }
        });
    viewModel.recuperarInmueble(getArguments());
        return binding.getRoot();
    }
}