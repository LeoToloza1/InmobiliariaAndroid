package com.leotoloza.menu.ui.Inmuebles;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leotoloza.menu.R;
import com.leotoloza.menu.databinding.FragmentNuevoInmuebleBinding;
import com.leotoloza.menu.modelo.Inmueble;

import java.io.File;

public class NuevoInmuebleFragment extends Fragment {
    private FragmentNuevoInmuebleBinding binding;
    private NuevoInmuebleViewModel viewModel;
    private Intent intent;
    private ActivityResultLauncher<Intent> arl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNuevoInmuebleBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(NuevoInmuebleViewModel.class);
        binding.Galeria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirGaleria();
            }
        });
        viewModel.getInmuebleMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Inmueble>() {
            @Override
            public void onChanged(Inmueble inmueble) {
                String direccion = binding.Direccion.getText().toString();
                Double precio = Double.valueOf(binding.Precio.getText().toString());
                String uso = binding.Uso.getText().toString();
                int ambientes = Integer.parseInt(binding.Ambientes.getText().toString());
                String estado = "Retirado";
                String descripcion = binding.Descripcion.getText().toString();
                inmueble.setDireccion(direccion);
                inmueble.setPrecio(precio);
                inmueble.setTipoInmuebleId(1);
                inmueble.setUso(uso);
                inmueble.setAmbientes(ambientes);
                inmueble.setEstado(estado);
                inmueble.setDescripcion(descripcion);
                binding.guardarButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewModel.agregarInmuebleConImagen(inmueble, viewModel.getMFotoFile().getValue());
                    }
                });
            }
        });

        return binding.getRoot();
    }
        private void abrirGaleria() {
        intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        arl = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                viewModel.recibirFoto(result, NuevoInmuebleFragment.this.requireContext());
            }
        });
        arl.launch(intent);
    }
}
