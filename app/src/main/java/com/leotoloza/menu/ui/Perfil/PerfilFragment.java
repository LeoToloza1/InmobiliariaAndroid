package com.leotoloza.menu.ui.Perfil;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.leotoloza.menu.R;
import com.leotoloza.menu.databinding.FragmentPerfilBinding;
import com.leotoloza.menu.modelo.Propietario;
import com.leotoloza.menu.request.ApiClient;

import org.w3c.dom.Text;

public class PerfilFragment extends Fragment {
private PerfilViewModel viewModel;
    private FragmentPerfilBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())).get(PerfilViewModel.class);
        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        TextView nombre = binding.etNombre;
        TextView apellido = binding.etApellido;
        TextView dni = binding.etDni;
        TextView email = binding.etMail;
        TextView pass = binding.etPass;
        TextView telefono = binding.etTelefono;
        ImageView foto = binding.ivFoto;
        String urlBase = ApiClient.URLBASE + "img/uploads/";
        viewModel.getCamposEditablesLiveData().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean habilitar) {
                nombre.setEnabled(habilitar);
                apellido.setEnabled(habilitar);
                dni.setEnabled(habilitar);
                email.setEnabled(habilitar);
                pass.setEnabled(habilitar);
                telefono.setEnabled(habilitar);
            }
        });
        Button btnEditar = binding.btGuardar;
        viewModel.getTextoBotonLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String texto) {
                btnEditar.setText(texto);
            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.habilitarCampos();
                viewModel.cambiarTextoBoton();
            }
        });
        viewModel.getMutablePropietario().observe(getViewLifecycleOwner(), new Observer<Propietario>() {
            @Override
            public void onChanged(Propietario propietario) {
                nombre.setText(propietario.getNombre());
                apellido.setText(propietario.getApellido());
                dni.setText(propietario.getDni());
                email.setText(propietario.getEmail());
                pass.setText("contraseña");
                telefono.setText(propietario.getTelefono()+"");
                String urlFoto = urlBase + propietario.getAvatarUrl();
//                Log.d("salida", "RUTA FOTO --> "+urlFoto);

                Glide.with(requireContext())
                        .load(urlFoto)
                        .placeholder(R.drawable.loading)
                        .error(R.drawable.error)
                        .into(foto);
            }
        });
        viewModel.cargarPerfil();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}