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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pcr.procookingrecipes.Activity.LoginActivity;
import com.pcr.procookingrecipes.Activity.RecetaActivity;
import com.pcr.procookingrecipes.databinding.FragmentoCuentaBinding;

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

        // Obtener el usuario actual
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            // Obtener el nombre de usuario, el correo electrónico y el teléfono de contacto
            String nombre = currentUser.getDisplayName();
            String email = currentUser.getEmail();
            String telefono = currentUser.getPhoneNumber();

            // Mostrar los datos en los TextViews
            if(nombre == null){
                nombreCuenta.setText("No se ha encontrado el nombre de la cuenta.");
            }else{
                nombreCuenta.setText(nombre);
            }

            if(email == null){
                mailCuenta.setText("No se ha encontrado el correo electrónico de la cuenta.");
            }else{
                mailCuenta.setText(email);
            }

            if(telefono == null){
                tlfnCuenta.setText("No se ha encontrado el teléfono de la cuenta.");
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