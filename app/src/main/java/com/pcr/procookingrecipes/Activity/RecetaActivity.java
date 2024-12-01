package com.pcr.procookingrecipes.Activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pcr.procookingrecipes.Adapters.RecyclerViewReceta.RecetaEquipo.EquipoAdapter;
import com.pcr.procookingrecipes.Adapters.RecyclerViewReceta.RecetaIngredientes.IngredientesAdapter;
import com.pcr.procookingrecipes.Adapters.RecyclerViewReceta.RecetaInstrucciones.InstruccionesAdapter;
import com.pcr.procookingrecipes.ConexionAPI.SecurePreferences;
import com.pcr.procookingrecipes.ConexionAPI.Spoonacular.Spoonacular_API_Response;
import com.pcr.procookingrecipes.Receta.InstruccionesReceta.Equipment;
import com.pcr.procookingrecipes.Receta.InstruccionesReceta.Ingredient;
import com.pcr.procookingrecipes.Receta.InstruccionesReceta.Instruction;
import com.pcr.procookingrecipes.Receta.InstruccionesReceta.Step;
import com.pcr.procookingrecipes.Receta.RecetaBusqueda;
import com.pcr.procookingrecipes.databinding.ActivityRecetaBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class RecetaActivity extends AppCompatActivity {

    private ActivityRecetaBinding binding;
    private Spoonacular_API_Response apiResponse;
    private final Executor executor = Executors.newSingleThreadExecutor();
    private List<Instruction> instruccionesCompletas;
    private ArrayList<String> listaPasos, listaIngredientes, listaEquipo;
    private int id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inicializar Firebase
        FirebaseApp.initializeApp(this);

        // Inflar el layout usando ViewBinding
        binding = ActivityRecetaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Inicialización de datos
        SecurePreferences.cargarClavesDesdeArchivo(this);
        apiResponse = new Spoonacular_API_Response(this);
        id = getIntent().getIntExtra("ID", -1);
        RecetaBusqueda receta = getIntent().getParcelableExtra("Receta");

        // Inicializar UI con los datos de la receta
        inicializarInterfaz(receta);

        // Cargar instrucciones de la receta en segundo plano
        loadRecetaInstrucciones();

        // Comprobar si la receta está en favoritos y actualizar el botón
        comprobarFavorito(this::actualizarBotonFavorito);

        // Configurar el botón de favoritos
        configurarBotonFavoritos();
    }

    // Inicializar UI con los datos de la receta
    private void inicializarInterfaz(RecetaBusqueda receta) {
        binding.valoracion.setText("Valoración: " + (Math.round((receta.getSpoonacularScore() / 10) * 100.0) / 100.0));
        binding.sinGluten.setText(Objects.equals(receta.getGlutenFree(), "true") ? "Sin Gluten." : "Con Gluten.");
        binding.precioReceta.setText("Precio: " + (Math.round(receta.getPricePerServing() / 10) + "€"));
        binding.tituloReceta.setText(receta.getTitle());

        // Cargar la imagen de la receta
        Picasso.get().load(receta.getImage()).into(binding.imagenReceta);
        binding.comensalesReceta.setText("Para " + receta.getServings() + " personas.");
    }

    // Cargar instrucciones de la receta de manera asíncrona
    private void loadRecetaInstrucciones() {
        // Iniciar un hilo para cargar las instrucciones
        executor.execute(() -> {
            // Obtener las instrucciones de la receta
            instruccionesCompletas = apiResponse.getInstrucciones(id);

            // Actualizar la UI en el hilo principal
            runOnUiThread(() -> {
                // Parsear las instrucciones
                parsearInstrucciones(instruccionesCompletas);
                configurarRecyclerViews();
            });
        });
    }

    // Configurar los adaptadores de los RecyclerViews
    private void configurarRecyclerViews() {
        // Configurar los adaptadores de los RecyclerViews si hay datos
        if (listaPasos != null && !listaPasos.isEmpty()) {
            InstruccionesAdapter instruccionesAdapter = new InstruccionesAdapter(listaPasos);
            binding.recyclerInstrucciones.setAdapter(instruccionesAdapter);
            binding.recyclerInstrucciones.setLayoutManager(new LinearLayoutManager(this));
        }

        // Configurar los adaptadores de los RecyclerViews de los ingredientes si hay datos
        if (listaIngredientes != null) {
            IngredientesAdapter ingredientesAdapter = new IngredientesAdapter(listaIngredientes);
            binding.recyclerIngredientes.setAdapter(ingredientesAdapter);
            binding.recyclerIngredientes.setLayoutManager(new LinearLayoutManager(this));
        }

        // Configurar los adaptadores de los RecyclerViews del equipo si hay datos
        if (listaEquipo != null) {
            EquipoAdapter equipoAdapter = new EquipoAdapter(listaEquipo);
            binding.recyclerEquipo.setAdapter(equipoAdapter);
            binding.recyclerEquipo.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    // Comprobar si la receta está en favoritos y ejecutar el callback
    private void comprobarFavorito(Callback callback) {
        // Obtener el ID del usuario y el email
        String userId = FirebaseAuth.getInstance().getUid();
        String email = getMailFormateado(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        // Obtener los favoritos del usuario
        DatabaseReference referenciaFavoritos = FirebaseDatabase.getInstance().getReference("favoritos");
        DatabaseReference usuarioFavoritos = referenciaFavoritos.child(email + " - " + userId);

        // Consultar los favoritos del usuario
        usuarioFavoritos.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DataSnapshot dataSnapshot = task.getResult();
                boolean isFavorite = comprobarFavorito(dataSnapshot);

                // Ejecutar el callback con el resultado
                callback.onFavoritoComprobado(isFavorite);
            } else {
                Log.e("RecetaActivity", "Error al leer los datos de favoritos", task.getException());
            }
        });
    }

    // Comprobar si la receta está en los favoritos
    private boolean comprobarFavorito(DataSnapshot dataSnapshot) {
        // Recorrer los favoritos
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

            // Verificar si el ID de la receta en el snapshot coincide con el ID de la receta actual
            String idReceta = snapshot.child("ID").getValue(String.class);
            if (idReceta != null && Integer.parseInt(idReceta) == id) {
                return true;
            }
        }
        return false;
    }

    // Formatear el email para eliminar puntos (necesario para Firebase)
    private String getMailFormateado(String email) {
        if (email.contains(".")) {
            return email.replace(".", "·");
        }
        return email;
    }

    // Actualizar el botón de favoritos según si la receta está o no en favoritos
    private void actualizarBotonFavorito(boolean isFavorite) {
        // Cambiar el texto del botón según si la receta está o no en favoritos
        binding.botonFav.setText(isFavorite ? "Eliminar de favoritos" : "Añadir a favoritos");
    }

    // Configurar la acción del botón de favoritos
    private void configurarBotonFavoritos() {
        binding.botonFav.setOnClickListener(view -> {
            // Obtener el estado actual del botón
            boolean isFavorite = binding.botonFav.getText().toString().equals("Eliminar de favoritos");

            // Cambiar el texto del botón
            binding.botonFav.setText(isFavorite ? "Añadir a favoritos" : "Eliminar de favoritos");

            // Operar en Firebase para añadir o eliminar el favorito
            String email = getMailFormateado(FirebaseAuth.getInstance().getCurrentUser().getEmail());
            String userId = FirebaseAuth.getInstance().getUid();
            DatabaseReference referencia = FirebaseDatabase.getInstance().getReference("favoritos");

            if (isFavorite) {
                removeFromFavorites(referencia, email, userId);
            } else {
                addToFavorites(referencia, email, userId);
            }
        });
    }

    // Eliminar receta de favoritos
    private void removeFromFavorites(DatabaseReference referencia, String email, String userId) {
        // Obtener la referencia de los favoritos del usuario
        DatabaseReference usuarioFavoritosRef = referencia.child(email + " - " + userId);

        // Consultar las recetas favoritas del usuario
        usuarioFavoritosRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DataSnapshot dataSnapshot = task.getResult();
                // Recorrer las recetas en los favoritos
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Verificar si el ID de la receta en el snapshot coincide con el ID de la receta actual
                    String idReceta = snapshot.child("ID").getValue(String.class);
                    if (idReceta != null && Integer.parseInt(idReceta) == id) {
                        // Eliminar el nodo usando la clave única de Firebase (key)
                        snapshot.getRef().removeValue();
                        Toast.makeText(RecetaActivity.this, "Receta eliminada de favoritos", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                Log.e("RecetaActivity", "Error al leer los datos de favoritos", task.getException());
            }
        });
    }

    // Añadir receta a favoritos
    private void addToFavorites(DatabaseReference referencia, String email, String userId) {
        // Crear el mapa con los datos de la receta
        Map<String, String> datos = new HashMap<>();
        datos.put("ID", String.valueOf(id));

        // Referencia a los favoritos del usuario
        DatabaseReference usuarioFavoritosRef = referencia.child(email + " - " + userId);

        // Añadir la receta a favoritos usando push() para generar una clave única
        usuarioFavoritosRef.push().setValue(datos)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(RecetaActivity.this, "Receta añadida a favoritos", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e("RecetaActivity", "Error al añadir la receta a favoritos", task.getException());
                    }
                });
    }


    // Parsear instrucciones de la receta en listas independientes para el RecyclerView
    private void parsearInstrucciones(List<Instruction> instrucciones) {
        // Inicializar las listas
        listaPasos = new ArrayList<>();
        listaIngredientes = new ArrayList<>();
        listaEquipo = new ArrayList<>();

        // Recorrer las instrucciones
        for (Instruction instruccion : instrucciones) {
            // Recorrer los pasos de la instrucción
            for (Step paso : instruccion.getSteps()) {
                // Agregar el paso a la lista de pasos
                listaPasos.add("Paso " + paso.getNumber() + ": " + paso.getStep());

                // Recorrer los ingredientes del paso
                for (Ingredient ingrediente : paso.getIngredients()) {
                    // Agregar el ingrediente a la lista de ingredientes si no está ya en la lista
                    if (!listaIngredientes.contains(ingrediente.getName())) {
                        listaIngredientes.add(ingrediente.getName());
                    }
                }

                // Recorrer el equipo del paso
                for (Equipment equipo : paso.getEquipment()) {
                    // Agregar el equipo a la lista de equipo si no está ya en la lista
                    if (!listaEquipo.contains(equipo.getName())) {
                        listaEquipo.add(equipo.getName());
                    }
                }
            }
        }
    }

    // Interfaz para el callback de favoritos
    public interface Callback {
        // Llamar cuando se haya comprobado si la receta está en favoritos
        void onFavoritoComprobado(boolean isFavorite);
    }
}
