package com.pcr.procookingrecipes.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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
import com.pcr.procookingrecipes.databinding.ActivityRecetaBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RecetaActivity extends AppCompatActivity {
    private ActivityRecetaBinding binding;
    private APIResponse apiResponse;
    private final Executor executor = Executors.newSingleThreadExecutor();
    private List<Instruction> instruccionesCompletas;
    private ArrayList<String> listaPasos, listaIngredientes, listaEquipo;
    ;  // Usamos ArrayList para poder agregar dinámicamente

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
                // Actualizamos la UI en el hilo principal después de la ejecución en segundo plano
                runOnUiThread(() -> {
                    // Establecer valores en los TextViews
                    binding.tituloReceta.setText(receta.getTitle());
                    Picasso.get().load(receta.getImage()).into(binding.imagenReceta);
                    binding.comensalesReceta.setText("Para " + receta.getServings() + " personas.");

                    // Parsear las instrucciones
                    parseInstrucciones(instruccionesCompletas);

                    // Verificar que la lista no esté vacía
                    if (listaPasos != null && !listaPasos.isEmpty()) {
                        // Crear el adaptador con los pasos procesados
                        InstruccionesAdapter instruccionesAdapter = new InstruccionesAdapter(listaPasos);

                        // Inicializar el RecyclerView
                        binding.recyclerInstrucciones.setAdapter(instruccionesAdapter);
                        binding.recyclerInstrucciones.setLayoutManager(new LinearLayoutManager(this));
                        if (listaIngredientes != null) {
                            // Crear el adaptador con los ingredientes procesados
                            IngredientesAdapter ingredientesAdapter = new IngredientesAdapter(listaIngredientes);
                            // Inicializar el RecyclerView
                            binding.recyclerIngredientes.setAdapter(ingredientesAdapter);
                            binding.recyclerIngredientes.setLayoutManager(new LinearLayoutManager(this));

                        }
                        if (listaEquipo != null) {
                            // Crear el adaptador con los ingredientes
                            EquipoAdapter equipoAdapter = new EquipoAdapter(listaEquipo);
                            // Inicializar el RecyclerView
                            binding.recyclerEquipo.setAdapter(equipoAdapter);
                            binding.recyclerEquipo.setLayoutManager(new LinearLayoutManager(this));
                        }
                    } else {
                        Log.e("Receta", "La lista de pasos está vacía");
                    }
                });
            } else {
                Log.e("Receta", "No se han encontrado instrucciones");
            }
        });

        binding.botonCrearCarta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                executor.execute(() -> {
                    String carta = apiResponse.generarCarta(id);
                    // Extraer la url y estado de la carta con expresiones regulares
                    String regex = "\"url\":\\s*\"([^\"]*)\".*?\"status\":\\s*\"([^\"]*)\"";

                    // Crear el patrón y el matcher
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(carta);
                    // Buscar y extraer los valores de "url" y "status"
                    String url = "";
                    String status = "";
                    if (matcher.find()) {
                        url = matcher.group(1);  // El primer grupo es la URL
                        status = matcher.group(2);  // El segundo grupo es el status
                    }
                    if (!matcher.find() || !status.equals("success")){
                        runOnUiThread(() -> {
                            Toast.makeText(RecetaActivity.this, "Error al generar la carta", Toast.LENGTH_SHORT).show();
                        });
                    }

                    Intent intent = new Intent(RecetaActivity.this, CardActivity.class);
                    intent.putExtra("Carta", url);
                    startActivity(intent);
                });
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Liberar el binding cuando la actividad se destruye
        binding = null;
    }

    // Método que parsea las instrucciones
    public void parseInstrucciones(List<Instruction> instrucciones) {
        listaPasos = new ArrayList<>(); // Creamos la lista de pasos
        listaIngredientes = new ArrayList<>(); // Creamos la lista de ingredientes
        listaEquipo = new ArrayList<>(); // Creamos la lista de equipo

        for (Instruction instruccion : instrucciones) {
            for (Step paso : instruccion.getSteps()) {
                // Añadimos cada paso de forma segura a la lista
                listaPasos.add("Paso " + paso.getNumber() + ": " + paso.getStep());
                // Ingredientes
                if (!paso.getIngredients().isEmpty()) {
                    for (Ingredient ingrediente : paso.getIngredients()) {
                        listaIngredientes.add(ingrediente.getName());
                    }
                }

                // Equipo
                if (!paso.getEquipment().isEmpty()) {
                    for (Equipment equipo : paso.getEquipment()) {
                        listaEquipo.add(equipo.getName());
                        ;
                    }
                }
            }
        }
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
