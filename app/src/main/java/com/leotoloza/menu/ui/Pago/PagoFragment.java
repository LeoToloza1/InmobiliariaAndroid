package com.leotoloza.menu.ui.Pago;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leotoloza.menu.R;
import com.leotoloza.menu.Servicios.ToastPesonalizado;
import com.leotoloza.menu.databinding.FragmentPagoBinding;
import com.leotoloza.menu.modelo.Contrato;
import com.leotoloza.menu.modelo.Pago;
import com.leotoloza.menu.ui.Contratos.ViewModelCompartido;

import java.util.List;


public class PagoFragment extends Fragment {
    private FragmentPagoBinding binding;
    private PagoViewModel viewModel;
    private ViewModelCompartido viewModelCompartido;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewModel =new ViewModelProvider(this).get(PagoViewModel.class);
       viewModelCompartido = new ViewModelProvider(requireActivity()).get(ViewModelCompartido.class);
        binding = FragmentPagoBinding.inflate(inflater, container, false);
        RecyclerView listaPagos = binding.listaPago;
        viewModelCompartido.getContrato().observe(getViewLifecycleOwner(), new Observer<Contrato>() {
            @Override
            public void onChanged(Contrato contrato) {
                int contratoId= contrato.getId();
                viewModel.getListPagos(contratoId);
            }
        });
        viewModel.getPagos().observe(getViewLifecycleOwner(), new Observer<List<Pago>>() {
            @Override
            public void onChanged(List<Pago> pagosList) {
                PagoAdapter pagoAdapter = new PagoAdapter(pagosList,getContext());
                GridLayoutManager glm=new GridLayoutManager(getContext(),1,GridLayoutManager.VERTICAL,false);
                listaPagos.setLayoutManager(glm);
                listaPagos.setAdapter(pagoAdapter);
                pagoAdapter.setClickListener(new PagoAdapter.ClickListener() {
                    @Override
                    public void clickDetalle(Pago pago) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("pago", pago);
                    Navigation.findNavController(requireView()).navigate(R.id.action_nav_pagos_to_detallePagoFragment, bundle);
                    }
                });
            }
        });


        return binding.getRoot();
    }
}