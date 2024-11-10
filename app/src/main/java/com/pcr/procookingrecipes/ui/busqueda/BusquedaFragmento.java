package com.pcr.procookingrecipes.ui.busqueda;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pcr.procookingrecipes.Adapters.BusquedaDataModel;
import com.pcr.procookingrecipes.Adapters.ItemBusquedaAdapter;
import com.pcr.procookingrecipes.ConexionAPI.APIResponse;
import com.pcr.procookingrecipes.R;
import com.pcr.procookingrecipes.Receta.Receta;
import com.pcr.procookingrecipes.databinding.FragmentoBusquedaBinding;
import com.pcr.procookingrecipes.ui.busqueda.BusquedaViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class BusquedaFragmento extends Fragment {

    private FragmentoBusquedaBinding binding;
    private RecyclerView recyclerView;
    private ItemBusquedaAdapter adapter;
    private List<BusquedaDataModel> itemList;
    private FloatingActionButton fab, buscar;

    private final Executor executor = Executors.newSingleThreadExecutor(); // Executor para manejar tareas en segundo plano

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        BusquedaViewModel busquedaViewModel =
                new ViewModelProvider(this).get(BusquedaViewModel.class);

        binding = FragmentoBusquedaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        inicializarRecyclerView(root);
        itemList = new ArrayList<>();
        itemList.add(new BusquedaDataModel("Elemento 1", "Descripción 1"));

        adapter = new ItemBusquedaAdapter(itemList);
        binding.recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        final TextView textView = binding.textBusqueda;
        busquedaViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        return root;
    }

    private void inicializarRecyclerView(View root) {
        recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fab = requireActivity().findViewById(R.id.botonFlotante1);
        fab.setImageResource(R.drawable.ic_plus);

        fab.setOnClickListener(v -> {
            itemList.add(new BusquedaDataModel("Elemento 1", "Descripción 1"));

            adapter.notifyItemInserted(itemList.size());
        });

        buscar = requireActivity().findViewById(R.id.botonFlotante2);
        buscar.setOnClickListener(v -> {
            // Realizar la conexión con la API en un hilo de fondo
            executor.execute(() -> {
                APIResponse apiResponse = new APIResponse();
                //Verificar si se ha elegido ingredientes y hay datos
                List<Receta> recipes = apiResponse.searchAppleRecipes(); // Se lee de la API
                if (recipes != null) {
                    new Handler(Looper.getMainLooper()).post(() -> {
                        // Mostrar los resultados en el hilo principal
                        for (Receta recipe : recipes) {
                            Log.d("Recipe", "Title: " + recipe.getTitle());
                            Log.d("Recipe", "Image URL: " + recipe.getImage());
                        }
                    });
                } else {
                    new Handler(Looper.getMainLooper()).post(() -> {
                        Log.e("APIResponse", "No se pudieron obtener recetas.");
                    });
                }
            });
        });
    }
}