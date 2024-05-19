package com.leotoloza.menu.ui.Contratos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.leotoloza.menu.R;
import com.leotoloza.menu.Servicios.ToastPesonalizado;
import com.leotoloza.menu.databinding.FragmentContratosBinding;
import com.leotoloza.menu.modelo.Contrato;
import com.leotoloza.menu.modelo.Inmueble;
import com.leotoloza.menu.ui.Inmuebles.InmuebleAdapter;

import java.util.List;

public class ContratoFragment extends Fragment {
private ContratoViewModel viewModel;
    private FragmentContratosBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel =new ViewModelProvider(this).get(ContratoViewModel.class);
        binding = FragmentContratosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        RecyclerView listaContratos = binding.listaContratos;
viewModel.getContratos().observe(getViewLifecycleOwner(), new Observer<List<Contrato>>() {
    @Override
    public void onChanged(List<Contrato> contratosList) {
ContratoAdapter contratoAdapter = new ContratoAdapter(contratosList,getContext());
GridLayoutManager glm=new GridLayoutManager(getContext(),1,GridLayoutManager.VERTICAL,false);
        listaContratos.setLayoutManager(glm);
        listaContratos.setAdapter(contratoAdapter);
        contratoAdapter.setClickListener(new ContratoAdapter.ClickListener() {
            @Override
            public void clickDetalle(Contrato contrato) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("contrato", contrato);
                Navigation.findNavController(requireView()).navigate(R.id.action_nav_contratos_to_Inquilinos, bundle);
            }
        });
    }
});
viewModel.getListContratos();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}