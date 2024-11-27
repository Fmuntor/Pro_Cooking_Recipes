package com.pcr.procookingrecipes.ui.cuenta;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pcr.procookingrecipes.Activity.LoginActivity;
import com.pcr.procookingrecipes.Activity.RecetaActivity;
import com.pcr.procookingrecipes.R;
import com.pcr.procookingrecipes.databinding.FragmentoCuentaBinding;

import java.util.Objects;

public class CuentaFragmento extends Fragment {

    private FragmentoCuentaBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CuentaViewModel slideshowViewModel =
                new ViewModelProvider(this).get(CuentaViewModel.class);

        binding = FragmentoCuentaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView nombreCuenta = binding.nombreCuenta;
        final TextView mailCuenta = binding.mailCuenta;
        final TextView tlfnCuenta = binding.tlfnCuenta;
        final Button cerrarSesion = binding.botonLogout;

        // Desactivar los botones flotantes
        FloatingActionButton botonIntroducirItem = requireActivity().findViewById(R.id.botonIntroducirItem);
        botonIntroducirItem.setVisibility(View.GONE);
        FloatingActionButton botonBuscar = requireActivity().findViewById(R.id.botonBuscar);
        botonBuscar.setVisibility(View.GONE);

        // Obtener el usuario actual
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            // Obtener el nombre de usuario, el correo electrónico y el teléfono de contacto
            String nombre = currentUser.getDisplayName();
            String email = currentUser.getEmail();
            String telefono = currentUser.getPhoneNumber();

            // Mostrar los datos en los TextViews
            if(Objects.equals(nombre, "") || nombre == null){
                nombreCuenta.setText(R.string.fernando_munoz_torres);
            }else{
                nombreCuenta.setText(nombre);
            }

            if(Objects.equals(email, "") || email == null){
                mailCuenta.setText(R.string.correo_no_encontrado);
            }else{
                mailCuenta.setText(email);
            }

            if(Objects.equals(telefono, "") || telefono == null){
                tlfnCuenta.setText(R.string.tlfn);
            }else{
                tlfnCuenta.setText(telefono);
            }

        }

        // Obtener una instancia de FirebaseAuth
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        cerrarSesion.setOnClickListener(v -> {
            // Desloguear al usuario actual
            mAuth.signOut();

            // Mostrar un mensaje de confirmación o redirigir a otra pantalla
            Toast.makeText(getContext(), "Sesión cerrada con éxito.", Toast.LENGTH_SHORT).show();

            // Redirigir al usuario a la pantalla de inicio de sesión
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}