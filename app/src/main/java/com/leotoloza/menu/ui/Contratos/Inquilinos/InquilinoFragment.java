package com.leotoloza.menu.ui.Contratos.Inquilinos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leotoloza.menu.R;
import com.leotoloza.menu.databinding.FragmentInmuebleDetalleBinding;
import com.leotoloza.menu.databinding.FragmentInquilinoBinding;
import com.leotoloza.menu.modelo.Inquilino;
import com.leotoloza.menu.ui.Inmuebles.InmuebleDetalleViewModel;

public class InquilinoFragment extends Fragment {
    private FragmentInquilinoBinding binding;
    private InquilinoViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInquilinoBinding.inflate(inflater, container, false);
        viewModel= new ViewModelProvider(this).get(InquilinoViewModel.class);
        viewModel.getInquilinos().observe(getViewLifecycleOwner(), new Observer<Inquilino>() {
            @Override
            public void onChanged(Inquilino inquilino) {
            binding.etNombre.setText(inquilino.getNombre());
            binding.etApellido.setText(inquilino.getApellido());
            binding.etDni.setText(inquilino.getDni());
            binding.etMail.setText(inquilino.getEmail());
            binding.etTelefono.setText(inquilino.getTelefono()+"");
            }
        });

        viewModel.recuperarInquilino(getArguments());

        return binding.getRoot();
    }
}