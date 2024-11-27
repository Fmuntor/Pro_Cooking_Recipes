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
        binding = FragmentoFavoritosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Instanciar APIResponse
        apiResponse = new APIResponse();

        // Configurar RecyclerView
        adapter = new ItemBusquedaAdapter(listaFavoritos);
        binding.recyclerViewFavoritos.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewFavoritos.setAdapter(adapter);

        // Configurar Firebase
        database = FirebaseDatabase.getInstance();
        referenciaFavoritos = database.getReference("favoritos");

        TextView tvRecyclerSinDatos = root.findViewById(R.id.sinFavoritos);

        // Cargar favoritos
        cargarFavoritos();

        if(listaFavoritos.isEmpty()){
            tvRecyclerSinDatos.setVisibility(View.VISIBLE);
        }else{
            tvRecyclerSinDatos.setVisibility(View.GONE);
        }

        return root;
    }

    private void cargarFavoritos() {
        // Obtener el UID del usuario actual
        String userId = FirebaseAuth.getInstance().getUid();
        if (userId == null) {
            Toast.makeText(getContext(), "No estás autenticado", Toast.LENGTH_SHORT).show();
            return;
        }
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        //Eliminar el punto
        if (email.contains(".")){
            email = email.replace(".","·");
        }
        // Referencia al nodo del usuario
        DatabaseReference usuarioFavoritos = referenciaFavoritos.child(email+" - "+userId);

        usuarioFavoritos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaFavoritos.clear();
                int totalBusquedas = (int) dataSnapshot.getChildrenCount();
                if (totalBusquedas == 0) {
                    Log.d("Firebase", "No hay favoritos.");
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
                            if (listaFavoritos.size() == totalBusquedas) {
                                // Actualiza el adaptador en el hilo principal
                                requireActivity().runOnUiThread(() -> {
                                    adapter.notifyDataSetChanged();
                                    Log.d("Firebase", "Favoritos cargados: " + listaFavoritos.size());
                                });
                            }
                        });
                    }
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
