package com.leotoloza.menu.ui.Perfil;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.leotoloza.menu.R;
import com.leotoloza.menu.Servicios.Dialogo;
import com.leotoloza.menu.Servicios.ToastPesonalizado;
import com.leotoloza.menu.databinding.ActivityLoginAcitivityBinding;
import com.leotoloza.menu.databinding.FragmentPerfilBinding;
import com.leotoloza.menu.login.LoginAcitivity;
import com.leotoloza.menu.modelo.Propietario;
import com.leotoloza.menu.request.ApiClient;

import org.w3c.dom.Text;

public class PerfilFragment extends Fragment  implements Dialogo.CambioContraseñaListener {
private PerfilViewModel viewModel;
    private FragmentPerfilBinding binding;
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); // Indica que este fragmento tiene su propio menú de opciones
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())).get(PerfilViewModel.class);
        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        setMenuVisibility(true);
        TextView nombre = binding.etNombre;
        TextView apellido = binding.etApellido;
        TextView dni = binding.etDni;
        TextView email = binding.etMail;
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
                telefono.setEnabled(habilitar);
            }
        });
        Button btnEditar = binding.btEditar;
        viewModel.getTextoBotonLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String texto) {
                btnEditar.setText(texto);
            }
        });

        binding.btEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.habilitarCampos();
                viewModel.cambiarTextoBoton();
                binding.btEditar.setVisibility(View.INVISIBLE);
                binding.btGuardarCambios.setVisibility(View.VISIBLE);
            }
        });
        viewModel.getPassword().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Dialogo.mostrarDialogoInformativo(getContext(),"Ocurrio un error",s);
            }
        });
//TODO hacer un dialogo para poder resetear la contraseña
        binding.btGuardarCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = binding.etNombre.getText().toString();
                String apellido = binding.etApellido.getText().toString();
                String dni = binding.etDni.getText().toString();
                String telefono = binding.etTelefono.getText().toString();
                Propietario propietario = new Propietario();
                propietario.setNombre(nombre);
                propietario.setApellido(apellido);
                propietario.setDni(dni);
                viewModel.editarPerfil(propietario);
                binding.btGuardarCambios.setVisibility(View.INVISIBLE);
                binding.btEditar.setVisibility(View.VISIBLE);
                viewModel.habilitarCampos();
            }
        });
        viewModel.getMutablePropietario().observe(getViewLifecycleOwner(), new Observer<Propietario>() {
            @Override
            public void onChanged(Propietario propietario) {
                nombre.setText(propietario.getNombre());
                apellido.setText(propietario.getApellido());
                dni.setText(propietario.getDni());
                email.setText(propietario.getEmail());
                telefono.setText(propietario.getTelefono()+"");
                String urlFoto = urlBase + propietario.getAvatarUrl();
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
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            Dialogo.mostrarDialogoConEntrada(getContext(), "Cambio de contraseña", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
            ToastPesonalizado.mostrarMensaje(getContext(), "Cambiando contraseña");
                }
            }, this);
            return true;
        }else if (item.getItemId() == R.id.action_logout) {
            Dialogo.mostrarDialogoConfirmacion(getContext(), "Cerrar Sesión", "¿Estás seguro que deseas cerrar sesión?", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(getActivity(), LoginAcitivity.class);
                    viewModel.cerrarSesion();
                    startActivity(intent);
                }
            });
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onAceptar(String nuevaContraseña) {
    viewModel.cambiarPassword(nuevaContraseña);
    }
}