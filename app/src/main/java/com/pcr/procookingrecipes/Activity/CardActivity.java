package com.pcr.procookingrecipes.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.pcr.procookingrecipes.Adapters.RecyclerViewReceta.RecetaEquipo.EquipoAdapter;
import com.pcr.procookingrecipes.Adapters.RecyclerViewReceta.RecetaIngredientes.IngredientesAdapter;
import com.pcr.procookingrecipes.Adapters.RecyclerViewReceta.RecetaInstrucciones.InstruccionesAdapter;
import com.pcr.procookingrecipes.ConexionAPI.Spoonacular.APIResponse;
import com.pcr.procookingrecipes.InstruccionesReceta.Equipment;
import com.pcr.procookingrecipes.InstruccionesReceta.Ingredient;
import com.pcr.procookingrecipes.InstruccionesReceta.Instruction;
import com.pcr.procookingrecipes.InstruccionesReceta.Step;
import com.pcr.procookingrecipes.Receta.RecetaBusqueda;
import com.pcr.procookingrecipes.databinding.ActivityCardBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CardActivity extends AppCompatActivity {
    private ActivityCardBinding binding;
    private APIResponse apiResponse;
    private final Executor executor = Executors.newSingleThreadExecutor();
    private List<Instruction> instruccionesCompletas;
    private ArrayList<String> listaPasos, listaIngredientes, listaEquipo;;  // Usamos ArrayList para poder agregar dinámicamente

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflar el layout usando ViewBinding
        binding = ActivityCardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Recuperar datos
        String carta = getIntent().getStringExtra("Carta");
        // Extraer la url y estado de la carta
        Picasso.get().load(carta).into(binding.imagenCarta);
        // actualizar la UI en el hilo principal
        runOnUiThread(() -> {
            binding.imagenCarta.setVisibility(View.VISIBLE);
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Liberar el binding cuando la actividad se destruye
        binding = null;
    }
}
