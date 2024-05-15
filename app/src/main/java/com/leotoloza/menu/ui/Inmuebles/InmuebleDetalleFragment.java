package com.leotoloza.menu.ui.Inmuebles;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.leotoloza.menu.R;
import com.leotoloza.menu.databinding.FragmentInmuebleDetalleBinding;
import com.leotoloza.menu.modelo.Inmueble;
import com.leotoloza.menu.request.ApiClient;


public class InmuebleDetalleFragment extends Fragment {
    private FragmentInmuebleDetalleBinding binding;
    private InmuebleDetalleViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInmuebleDetalleBinding.inflate(inflater, container, false);
        viewModel= new ViewModelProvider(this).get(InmuebleDetalleViewModel.class);
        String urlFoto = ApiClient.URLBASE+"api";
        viewModel.getInmuebleMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Inmueble>() {
            @Override
            public void onChanged(Inmueble inmueble) {
            binding.Direccion.setText("Direccion: "+inmueble.getDireccion());
            binding.Precio.setText("Precio: $"+inmueble.getPrecio());
            binding.TipoInmueble.setText("Tipo: "+inmueble.getTipo().getTipo());
            binding.Uso.setText("Uso: "+inmueble.getUso());
            binding.Ambientes.setText("Ambientes: "+inmueble.getAmbientes()+"");
            boolean estado = inmueble.getEstado().equals("Disponible");
            binding.EstadoSwitch.setChecked(estado);
            binding.Descripcion.setText("Descripción: "+inmueble.getDescripcion());
                String urlBase = ApiClient.URLBASE + "img/uploads/";
                String urlFoto =urlBase +  inmueble.getAvatarUrl();
                Glide.with(getContext())
                        .load(urlFoto)
                        .placeholder(R.drawable.loading)
                        .error(R.drawable.error)
                        .into(binding.imagen);
            }
        });
    viewModel.recuperarInmueble(getArguments());
        return binding.getRoot();
    }
}