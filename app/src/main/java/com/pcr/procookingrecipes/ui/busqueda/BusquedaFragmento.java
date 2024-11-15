package com.pcr.procookingrecipes.ui.busqueda;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.Spinner;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;

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

        final CheckBox checkCocina = binding.checkCocina;
        final Spinner spinnerCocina = binding.spinnerCocina;

        final CheckBox checkNacionalidad = binding.checkNacionalidad;
        final Spinner spinnerNacionalidad = binding.spinnerNacionalidad;

        final CheckBox checkDieta = binding.checkDieta;
        final Spinner spinnerDieta = binding.spinnerDieta;

        final CheckBox checkIntolerancias = binding.checkIntolerancias;
        final Spinner spinnerIntolerancias = binding.spinnerIntolerancias;

        final CheckBox checkCarbo = binding.checkCarbo;
        final TextView textViewCarbo = binding.textViewCarbo;
        final SeekBar seekBarCarbo = binding.seekBarCarbo;

        final CheckBox checkProteina = binding.checkProteina;
        final TextView textViewProteina = binding.textViewProteina;
        final SeekBar seekBarProteina = binding.seekBarProteina;

        final CheckBox checkCalorias = binding.checkCalorias;
        final TextView textViewCalorias = binding.textViewCalorias;
        final SeekBar seekBarCalorias = binding.seekBarCalorias;

        inicializarSpinners();
        inicializarSeekBars();

        spinnerCocina.setEnabled(false);
        checkCocina.setOnClickListener(v -> {
            //Desactivar el spinner si no esta activado el checkbox de tipo de cocina
            spinnerCocina.setEnabled(checkCocina.isChecked());
        });

        spinnerNacionalidad.setEnabled(false);
        checkNacionalidad.setOnClickListener(v -> {
            //Desactivar el spinner si no esta activado el checkbox de nacionalidad
            spinnerNacionalidad.setEnabled(checkNacionalidad.isChecked());
        });

        spinnerDieta.setEnabled(false);
        checkDieta.setOnClickListener(v -> {
            //Desactivar el spinner si no esta activado el checkbox de tipo de dieta
            spinnerDieta.setEnabled(checkDieta.isChecked());
        });

        spinnerIntolerancias.setEnabled(false);
        checkIntolerancias.setOnClickListener(v -> {
            //Desactivar el spinner si no esta activado el checkbox de intolerancias
            spinnerIntolerancias.setEnabled(checkIntolerancias.isChecked());
        });

        seekBarCarbo.setEnabled(false);
        checkCarbo.setOnClickListener(v -> {
            //Desactivar el spinner si no esta activado el checkbox de intolerancias
            seekBarCarbo.setEnabled(checkCarbo.isChecked());
        });

        seekBarProteina.setEnabled(false);
        checkProteina.setOnClickListener(v -> {
            //Desactivar el spinner si no esta activado el checkbox de intolerancias
            seekBarProteina.setEnabled(checkProteina.isChecked());
        });

        seekBarCalorias.setEnabled(false);
        checkCalorias.setOnClickListener(v -> {
            //Desactivar el spinner si no esta activado el checkbox de intolerancias
            seekBarCalorias.setEnabled(checkCalorias.isChecked());
        });




        return root;
        //Toast.makeText(v.getContext(), "OK", Toast.LENGTH_SHORT).show();

    }

    private void inicializarSpinners() {
        //Tipo de cocina
        ArrayAdapter<CharSequence> adapterCocina = ArrayAdapter.createFromResource(getContext(),
                R.array.menu_tipo, android.R.layout.simple_spinner_item);
        adapterCocina.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerCocina.setAdapter(adapterCocina);

        //Nacionalidad
        ArrayAdapter<CharSequence> adapterNacionalidad = ArrayAdapter.createFromResource(getContext(),
                R.array.menu_nacionalidad, android.R.layout.simple_spinner_item);
        adapterNacionalidad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerNacionalidad.setAdapter(adapterNacionalidad);

        //Dieta
        ArrayAdapter<CharSequence> adapterDieta = ArrayAdapter.createFromResource(getContext(),
                R.array.menu_dietas, android.R.layout.simple_spinner_item);
        adapterDieta.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerDieta.setAdapter(adapterDieta);

        //Intolerancias
        ArrayAdapter<CharSequence> adapterIntolerancias = ArrayAdapter.createFromResource(getContext(),
                R.array.menu_intolerancias, android.R.layout.simple_spinner_item);
        adapterIntolerancias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerIntolerancias.setAdapter(adapterIntolerancias);
    }

    private void inicializarSeekBars() {
    // Inicializar el SeekBar de carbohidratos y su TextView, y luego crear el listener
        binding.textViewCarbo.setText(String.valueOf(binding.seekBarCarbo.getProgress()));
        // Agregar el listener para el SeekBar
        binding.seekBarCarbo.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Actualiza el TextView con el valor del SeekBar
                binding.textViewCarbo.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Opcionalmente, puedes hacer algo cuando el usuario empieza a mover el SeekBar
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Opcionalmente, puedes hacer algo cuando el usuario deja de mover el SeekBar
            }
        });
    // Inicializar el SeekBar de proteína y su TextView, y luego crear el listener
        binding.textViewProteina.setText(String.valueOf(binding.seekBarProteina.getProgress()));
        binding.seekBarProteina.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Actualiza el TextView con el valor del SeekBar
                binding.textViewProteina.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Opcionalmente, puedes hacer algo cuando el usuario empieza a mover el SeekBar
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Opcionalmente, puedes hacer algo cuando el usuario deja de mover el SeekBar
            }
        });

    // Inicializar el SeekBar de calorías y su TextView, y luego crear el listener
        binding.textViewCalorias.setText(String.valueOf(binding.seekBarCalorias.getProgress()));
        binding.seekBarCalorias.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Actualiza el TextView con el valor del SeekBar
                binding.textViewCalorias.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Opcionalmente, puedes hacer algo cuando el usuario empieza a mover el SeekBar
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Opcionalmente, puedes hacer algo cuando el usuario deja de mover el SeekBar
            }
        });
    }

    private void inicializarRecyclerView(View root) {
        recyclerView = binding.recyclerView;
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


                // OBTENER 10 RECETAS CON MANZANA
                /*
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
                 */

                // Obtener todos los ingredientes
                List<String> ingredients = apiResponse.getAllIngredients();

                if (ingredients != null && !ingredients.isEmpty()) {
                    new Handler(Looper.getMainLooper()).post(() -> {
                        // Mostrar los ingredientes en el log
                        for (String ingredient : ingredients) {
                            Log.d("Ingredient", ingredient);
                        }
                        Toast.makeText(getContext(), "Ingredientes cargados en el log", Toast.LENGTH_SHORT).show();
                    });
                } else {
                    new Handler(Looper.getMainLooper()).post(() -> {
                        Log.e("APIResponse", "No se pudieron obtener los ingredientes.");
                        Toast.makeText(getContext(), "Error al cargar ingredientes", Toast.LENGTH_SHORT).show();
                    });
                }
            });
        });
    }
}