package com.pcr.procookingrecipes.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pcr.procookingrecipes.Adapters.RecyclerViewReceta.RecetaEquipo.EquipoAdapter;
import com.pcr.procookingrecipes.Adapters.RecyclerViewReceta.RecetaIngredientes.IngredientesAdapter;
import com.pcr.procookingrecipes.Adapters.RecyclerViewReceta.RecetaInstrucciones.InstruccionesAdapter;
import com.pcr.procookingrecipes.ConexionAPI.Spoonacular.APIResponse;
import com.pcr.procookingrecipes.InstruccionesReceta.Equipment;
import com.pcr.procookingrecipes.InstruccionesReceta.Ingredient;
import com.pcr.procookingrecipes.InstruccionesReceta.Instruction;
import com.pcr.procookingrecipes.InstruccionesReceta.Step;
import com.pcr.procookingrecipes.Receta.RecetaBusqueda;
import com.pcr.procookingrecipes.databinding.ActivityRecetaBinding;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class RecetaActivity extends AppCompatActivity {
    private ActivityRecetaBinding binding;
    private APIResponse apiResponse;
    private final Executor executor = Executors.newSingleThreadExecutor();
    private List<Instruction> instruccionesCompletas;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflar el layout usando ViewBinding
        binding = ActivityRecetaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        apiResponse = new APIResponse();
        int id = getIntent().getIntExtra("ID", -1);
        RecetaBusqueda receta = getIntent().getParcelableExtra("Receta");

        executor.execute(() -> {
            instruccionesCompletas = apiResponse.getInstrucciones(id);
            Log.d("Receta", "Instrucciones: " + instruccionesCompletas);

            // Verificar que las instrucciones no son nulas ni vacías
            if (instruccionesCompletas != null && !instruccionesCompletas.isEmpty()) {
                String recetaFormateada = formatearInstrucciones(instruccionesCompletas);
                Log.d("Receta", recetaFormateada); // Muestra en la consola
            } else {
                Log.d("Receta", "No se encontraron instrucciones.");
            }

            // Actualizar UI en el hilo principal después de la ejecución en el background
            runOnUiThread(() -> {
                // Establecer valores en los TextViews
                binding.tituloReceta.setText(receta.getTitle());
                Picasso.get().load(receta.getImage()).into(binding.imagenReceta);
                binding.comensalesReceta.setText("Para " + receta.getServings() + " personas.");

                // Asignar los pasos (instrucciones) al RecyclerView
                if (instruccionesCompletas != null && !instruccionesCompletas.isEmpty()) {
                    List<Step> listaPasos = instruccionesCompletas.get(0).getSteps();

                    // Extraer los pasos como un arreglo de Strings
                    String[] arrayPasos = new String[listaPasos.size()];
                    for (int i = 0; i < listaPasos.size(); i++) {
                        arrayPasos[i] = listaPasos.get(i).getStep(); // Suponiendo que getStep() devuelve un String con la descripción del paso
                    }

                    // Configurar el RecyclerView para las instrucciones
                    InstruccionesAdapter instruccionesAdapter = new InstruccionesAdapter(arrayPasos);
                    binding.recyclerInstrucciones.setAdapter(instruccionesAdapter);

                    // Obtener los ingredientes del primer paso y configurar su RecyclerView
                    List<Ingredient> listaIngredientes = listaPasos.get(0).getIngredients();
                    String[] arrayIngredientes = new String[listaIngredientes.size()];
                    for (int i = 0; i < listaIngredientes.size(); i++) {
                        arrayIngredientes[i] = listaIngredientes.get(i).getName();
                    }

                    IngredientesAdapter ingredientesAdapter = new IngredientesAdapter(arrayIngredientes);
                    binding.recyclerIngredientes.setAdapter(ingredientesAdapter);

                    // Obtener el equipo del primer paso y configurar su RecyclerView
                    List<Equipment> listaEquipo = listaPasos.get(0).getEquipment();
                    String[] arrayEquipo = new String[listaEquipo.size()];
                    for (int i = 0; i < listaEquipo.size(); i++) {
                        arrayEquipo[i] = listaEquipo.get(i).getName();
                    }

                    EquipoAdapter equipoAdapter = new EquipoAdapter(arrayEquipo);
                    binding.recyclerEquipo.setAdapter(equipoAdapter);
                }
            });
        });

        // Configurar el botón para guardar y volver
        binding.botonCrearCarta.setOnClickListener(v -> {
            // Aquí puedes manejar la acción del botón, por ejemplo, guardar los datos de la receta
            Toast.makeText(RecetaActivity.this, "Receta guardada", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Liberar el binding cuando la actividad se destruye
        binding = null;
    }

    public String formatearInstrucciones(List<Instruction> instrucciones) {
        StringBuilder sb = new StringBuilder();

        for (Instruction instruccion : instrucciones) {
            sb.append("Receta: ").append(instruccion.getName().isEmpty() ? "Sin título" : instruccion.getName()).append("\n");

            for (Step paso : instruccion.getSteps()) {
                sb.append("Paso ").append(paso.getNumber()).append(": ").append(paso.getStep()).append("\n");

                // Ingredientes
                if (!paso.getIngredients().isEmpty()) {
                    sb.append("   Ingredientes: ");
                    for (Ingredient ingrediente : paso.getIngredients()) {
                        sb.append(ingrediente.getName()).append(", ");
                    }
                    // Elimina la última coma
                    sb.setLength(sb.length() - 2);
                    sb.append("\n");
                }

                // Equipo
                if (!paso.getEquipment().isEmpty()) {
                    sb.append("   Equipo: ");
                    for (Equipment equipo : paso.getEquipment()) {
                        sb.append(equipo.getName()).append(", ");
                    }
                    // Elimina la última coma
                    sb.setLength(sb.length() - 2);
                    sb.append("\n");
                }
            }
            sb.append("\n"); // Separador entre diferentes conjuntos de instrucciones
        }

        return sb.toString();
    }
}
