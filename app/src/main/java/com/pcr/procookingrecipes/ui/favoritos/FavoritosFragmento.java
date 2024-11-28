package com.pcr.procookingrecipes.ui.favoritos;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pcr.procookingrecipes.Adapters.ReciclerViewBusqueda.ItemBusquedaAdapter;
import com.pcr.procookingrecipes.ConexionAPI.Spoonacular.APIResponse;
import com.pcr.procookingrecipes.R;
import com.pcr.procookingrecipes.Receta.RecetaBusqueda;
import com.pcr.procookingrecipes.databinding.FragmentoFavoritosBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class FavoritosFragmento extends Fragment {

    private FragmentoFavoritosBinding binding;
    private ItemBusquedaAdapter adapter;
    private List<RecetaBusqueda> listaFavoritos = new ArrayList<>();
    private FirebaseDatabase database;
    private DatabaseReference referenciaFavoritos;
    private final Executor executor = Executors.newSingleThreadExecutor();
    private APIResponse apiResponse;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Inflar el layout
        binding = FragmentoFavoritosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Instanciar APIResponse
        apiResponse = new APIResponse(getContext());

        // Configurar RecyclerView
        adapter = new ItemBusquedaAdapter(listaFavoritos);
        binding.recyclerViewFavoritos.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewFavoritos.setAdapter(adapter);

        // Configurar Firebase
        database = FirebaseDatabase.getInstance();
        referenciaFavoritos = database.getReference("favoritos");

        // Referencia al TextView para mostrar el mensaje sin favoritos
        TextView tvRecyclerSinDatos = root.findViewById(R.id.sinFavoritos);
        tvRecyclerSinDatos.setVisibility(View.GONE);  // Ocultar el mensaje "sin favoritos"

        // Desactivar los botones flotantes
        FloatingActionButton botonIntroducirItem = requireActivity().findViewById(R.id.botonIntroducirItem);
        botonIntroducirItem.setVisibility(View.GONE);
        FloatingActionButton botonBuscar = requireActivity().findViewById(R.id.botonBuscar);
        botonBuscar.setVisibility(View.GONE);

        // Cargar favoritos
        cargarFavoritos(tvRecyclerSinDatos);

        return root;
    }

    private void cargarFavoritos(TextView tvRecyclerSinDatos) {
        // Obtener el UID del usuario actual
        String userId = FirebaseAuth.getInstance().getUid();
        if (userId == null) {
            Toast.makeText(getContext(), "No estás autenticado", Toast.LENGTH_SHORT).show();
            return;
        }

        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        // Eliminar el punto (para evitar problemas con Firebase)
        if (email.contains(".")) {
            email = email.replace(".", "·");
        }

        // Referencia al nodo del usuario en Firebase
        DatabaseReference usuarioFavoritos = referenciaFavoritos.child(email + " - " + userId);

        usuarioFavoritos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaFavoritos.clear();  // Limpiar la lista antes de cargar nuevos datos

                if (dataSnapshot.exists()) {
                    int totalBusquedas = (int) dataSnapshot.getChildrenCount();
                    if (totalBusquedas == 0) {
                        Log.d("Firebase", "No hay favoritos.");
                        tvRecyclerSinDatos.setVisibility(View.VISIBLE);  // Mostrar mensaje sin favoritos
                        return;
                    }

                    for (DataSnapshot busquedaSnapshot : dataSnapshot.getChildren()) {
                        String idReceta = busquedaSnapshot.child("ID").getValue(String.class);
                        if (idReceta != null) {
                            int id = Integer.parseInt(idReceta);
                            executor.execute(() -> {
                                RecetaBusqueda receta = apiResponse.getInformacionReceta(id);
                                if (receta != null) {
                                    listaFavoritos.add(receta);
                                }

                                // Actualizar el adaptador solo después de cargar todos los datos
                                if (listaFavoritos.size() == totalBusquedas) {
                                    requireActivity().runOnUiThread(() -> {
                                        adapter.notifyDataSetChanged();  // Actualizar el RecyclerView
                                        Log.d("Firebase", "Favoritos cargados: " + listaFavoritos.size());
                                        tvRecyclerSinDatos.setVisibility(View.GONE);  // Ocultar el mensaje "sin favoritos"
                                    });
                                }
                            });
                        }
                    }
                } else {
                    Log.d("Firebase", "No hay favoritos.");
                    tvRecyclerSinDatos.setVisibility(View.VISIBLE);  // Mostrar mensaje sin favoritos
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Firebase", "Error al leer favoritos: " + databaseError.getMessage());
                tvRecyclerSinDatos.setVisibility(View.VISIBLE);  // Mostrar mensaje si ocurre un error
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
