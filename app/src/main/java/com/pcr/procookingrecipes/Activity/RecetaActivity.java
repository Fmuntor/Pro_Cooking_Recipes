package com.pcr.procookingrecipes.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pcr.procookingrecipes.Adapters.RecyclerViewReceta.RecetaEquipo.EquipoAdapter;
import com.pcr.procookingrecipes.Adapters.RecyclerViewReceta.RecetaIngredientes.IngredientesAdapter;
import com.pcr.procookingrecipes.Adapters.RecyclerViewReceta.RecetaInstrucciones.InstruccionesAdapter;
import com.pcr.procookingrecipes.databinding.ActivityRecetaBinding;

public class RecetaActivity extends AppCompatActivity {
    private ActivityRecetaBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflar el layout usando ViewBinding
        binding = ActivityRecetaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Ejemplo de cómo rellenar los valores
        // Suponiendo que los datos de la receta vienen como parámetros extras o de una base de datos

        String tituloReceta = "Patatas al horno";
        String comensales = "Para 4 personas";
        String tiempoPreparacion = "Tiempo de preparación: 10 minutos";
        String tiempoCocinado = "Tiempo de cocina: 30 minutos";
        String[] ingredientes = {"2 patatas", "1 cucharada de sal", "Aceite de oliva"};
        String[] equipo = {"Olla", "Cuchillo", "Horno"};
        String[] instrucciones = {
                "Pelar las patatas.",
                "Cortar las patatas en trozos.",
                "Precalentar el horno a 180°C.",
                "Cocinar durante 30 minutos."
        };

        // Establecer valores en los TextViews
        binding.tituloReceta.setText(tituloReceta);
        binding.comensalesReceta.setText(comensales);
        binding.tiempoPreparacion.setText(tiempoPreparacion);
        binding.tiempoCocinado.setText(tiempoCocinado);

        // Aquí rellenamos los RecyclerViews de ingredientes, equipo e instrucciones
        // Usamos adaptadores para los RecyclerViews

        // Ingredientes RecyclerView
        IngredientesAdapter ingredientesAdapter = new IngredientesAdapter(ingredientes);
        binding.recyclerIngredientes.setAdapter(ingredientesAdapter);
        // añadir item
        ingredientesAdapter.addItem("3 huevos");

        // Equipo RecyclerView
        EquipoAdapter equipoAdapter = new EquipoAdapter(equipo);
        binding.recyclerEquipo.setAdapter(equipoAdapter);
        // añadir item
        equipoAdapter.addItem("Plancha");

        // Instrucciones RecyclerView
        InstruccionesAdapter instruccionesAdapter = new InstruccionesAdapter(instrucciones);
        binding.recyclerInstrucciones.setAdapter(instruccionesAdapter);
        // añadir item
        instruccionesAdapter.addItem("Cocinar durante 45 minutos.");

        // Configurar el botón para guardar y volver
        binding.botonCrearCarta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí puedes manejar la acción del botón, por ejemplo, guardar los datos de la receta
                Toast.makeText(RecetaActivity.this, "Receta guardada", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Liberar el binding cuando la actividad se destruye
        binding = null;
    }
}
