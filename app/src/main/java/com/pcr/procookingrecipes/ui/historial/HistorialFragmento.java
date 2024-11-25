package com.pcr.procookingrecipes.ui.historial;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pcr.procookingrecipes.Adapters.ItemHistorialAdapter;
import com.pcr.procookingrecipes.Adapters.ReciclerViewBusqueda.ItemBusquedaAdapter;
import com.pcr.procookingrecipes.ConexionAPI.Spoonacular.APIResponse;
import com.pcr.procookingrecipes.Receta.RecetaBusqueda;
import com.pcr.procookingrecipes.Receta.RecetaHistorial;
import com.pcr.procookingrecipes.databinding.FragmentoFavoritosBinding;
import com.pcr.procookingrecipes.databinding.FragmentoHistorialBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class HistorialFragmento extends Fragment {

    private FragmentoHistorialBinding binding;
    private final Executor executor = Executors.newSingleThreadExecutor();
    private APIResponse apiResponse;
    private ItemHistorialAdapter adapter;
    private List<RecetaHistorial> listaHistorial = new ArrayList<>();
    private FirebaseDatabase database;
    private DatabaseReference referenciaHistorial;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HistorialViewModel slideshowViewModel =
                new ViewModelProvider(this).get(HistorialViewModel.class);

        binding = FragmentoHistorialBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Instanciar APIResponse
        apiResponse = new APIResponse();

        // Configurar RecyclerView
        adapter = new ItemHistorialAdapter(listaHistorial);
        binding.recyclerViewHistorial.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewHistorial.setAdapter(adapter);

        // Configurar Firebase
        database = FirebaseDatabase.getInstance();
        referenciaHistorial = database.getReference("historial");

        cargarHistorial();

        return root;
    }

    private void cargarHistorial() {
        // Obtener el UID del usuario actual
        String userId = FirebaseAuth.getInstance().getUid();
        if (userId == null) {
            Toast.makeText(getContext(), "No estás autenticado", Toast.LENGTH_SHORT).show();
            return;
        }
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        //Eliminar el punto
        if (email.contains(".")) {
            email = email.replace(".", "·");
        }
        // Referencia al nodo del usuario
        DatabaseReference usuarioHistorial = referenciaHistorial.child(email + " - " + userId);

        usuarioHistorial.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaHistorial.clear();
                int totalBusquedas = (int) dataSnapshot.getChildrenCount();
                if (totalBusquedas == 0) {
                    Log.d("Firebase", "No hay historial.");
                    return;
                }

                for (DataSnapshot busquedaSnapshot : dataSnapshot.getChildren()) {
                    String fecha = busquedaSnapshot.child("fecha").getValue(String.class);

                    List<String> parametros = new ArrayList<>();
                    for (DataSnapshot parametroSnapshot : busquedaSnapshot.child("parametros").getChildren()) {
                        String parametro = parametroSnapshot.getValue(String.class);
                        parametros.add(parametro);
                    }

                    List<String> recetas = new ArrayList<>();
                    for (DataSnapshot recetaSnapshot : busquedaSnapshot.child("recetas").getChildren()) {
                        String receta = recetaSnapshot.getValue(String.class);
                        recetas.add(receta);
                    }

                    executor.execute(() -> {
                        RecetaHistorial recetaHistorial = new RecetaHistorial();
                        recetaHistorial.setFecha(fecha);
                        recetaHistorial.setParametros(parametros);
                        recetaHistorial.setRecetas(recetas);
                        recetaHistorial.setPushKey(busquedaSnapshot.getKey());

                        listaHistorial.add(recetaHistorial);

                        requireActivity().runOnUiThread(() -> {
                            adapter.notifyDataSetChanged();
                        });
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Firebase", "Error al leer favoritos: " + databaseError.getMessage());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}