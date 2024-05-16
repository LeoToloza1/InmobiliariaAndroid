package com.leotoloza.menu.ui.Inmuebles;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leotoloza.menu.R;
import com.leotoloza.menu.databinding.FragmentInmueblesBinding;
import com.leotoloza.menu.databinding.FragmentNuevoInmuebleBinding;
import com.leotoloza.menu.modelo.Inmueble;


public class NuevoInmuebleFragment extends Fragment {
    private FragmentNuevoInmuebleBinding binding;
    private NuevoInmuebleViewModel viewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNuevoInmuebleBinding.inflate(inflater, container, false);
        viewModel= new ViewModelProvider(this).get(NuevoInmuebleViewModel.class);

 viewModel.getInmuebleMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Inmueble>() {
     @Override
     public void onChanged(Inmueble inmueble) {

     }
 });


        return binding.getRoot();
    }
}