package com.pcr.procookingrecipes.Activity.Busqueda;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pcr.procookingrecipes.Adapters.ReciclerViewBusqueda.ItemBusquedaAdapter;
import com.pcr.procookingrecipes.Adapters.ReciclerViewBusqueda.BusquedaDataModel;
import com.pcr.procookingrecipes.ConexionAPI.Spoonacular.APIResponse;
import com.pcr.procookingrecipes.R;
import com.pcr.procookingrecipes.Receta.RecetaBusqueda;
import com.pcr.procookingrecipes.databinding.ActivityBusquedaBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BusquedaActivity extends AppCompatActivity {

    private ActivityBusquedaBinding binding;
    private RecyclerView recyclerView;
    private APIResponse apiResponse;
    private ItemBusquedaAdapter adapter;
    private List<RecetaBusqueda> recetasCompletas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityBusquedaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        inicializarRecyclerView();

        recetasCompletas = getIntent().getParcelableArrayListExtra("recetasCompletas");

        adapter = new ItemBusquedaAdapter(recetasCompletas);
        binding.recyclerViewBusqueda.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflar el menú; esto agrega elementos a la barra de acción si está presente
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void inicializarRecyclerView() {
        recyclerView = binding.recyclerViewBusqueda;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
