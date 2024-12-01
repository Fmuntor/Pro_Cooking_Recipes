package com.pcr.procookingrecipes.ui.Activity;

import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pcr.procookingrecipes.Adapters.ReciclerViewBusqueda.ItemBusquedaAdapter;
import com.pcr.procookingrecipes.ConexionAPI.Spoonacular.Spoonacular_API_Response;
import com.pcr.procookingrecipes.R;
import com.pcr.procookingrecipes.Receta.RecetaBusqueda;
import com.pcr.procookingrecipes.databinding.ActivityBusquedaBinding;

import java.util.List;

public class BusquedaActivity extends AppCompatActivity {

    private ActivityBusquedaBinding binding;
    private RecyclerView recyclerView;
    private Spoonacular_API_Response apiResponse;
    private ItemBusquedaAdapter adapter;
    private List<RecetaBusqueda> recetasCompletas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflar el layout usando ViewBinding
        binding = ActivityBusquedaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        inicializarRecyclerView();

        // Obtener los datos de la actividad anterior
        recetasCompletas = getIntent().getParcelableArrayListExtra("recetasCompletas");

        // Configurar el adaptador
        adapter = new ItemBusquedaAdapter(recetasCompletas);
        binding.recyclerViewBusqueda.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflar el menú
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void inicializarRecyclerView() {
        recyclerView = binding.recyclerViewBusqueda;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
