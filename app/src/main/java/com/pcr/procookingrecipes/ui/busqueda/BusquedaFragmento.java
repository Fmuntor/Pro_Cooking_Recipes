package com.pcr.procookingrecipes.ui.busqueda;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import com.pcr.procookingrecipes.Activity.BusquedaActivity;
import com.pcr.procookingrecipes.Activity.MainActivity;
import com.pcr.procookingrecipes.Adapters.BusquedaDataModel;
import com.pcr.procookingrecipes.Adapters.ItemBusquedaAdapter;
import com.pcr.procookingrecipes.ConexionAPI.Spoonacular.APIResponse;
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
    private FloatingActionButton botonIntroducirItem, botonBuscar;
    private APIResponse apiResponse;
    private final Executor executor = Executors.newSingleThreadExecutor();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        BusquedaViewModel busquedaViewModel = new ViewModelProvider(this).get(BusquedaViewModel.class);
        binding = FragmentoBusquedaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        inicializarRecyclerView();
        inicializarSpinners();
        inicializarSeekBars();

        itemList = new ArrayList<>();
        itemList.add(new BusquedaDataModel("EditText"));

        adapter = new ItemBusquedaAdapter(itemList);
        binding.recyclerView.setAdapter(adapter);

        configurarCheckBoxes();

        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Configuración del botón flotante para añadir nuevos ítems al RecyclerView
        botonIntroducirItem = requireActivity().findViewById(R.id.botonIntroducirItem);
        botonIntroducirItem.setImageResource(R.drawable.ic_plus);

        botonIntroducirItem.setOnClickListener(v -> {
            // Añadir un nuevo ítem al RecyclerView
            itemList.add(new BusquedaDataModel(""));
            adapter.notifyItemInserted(itemList.size() - 1); // Notificar al adaptador
        });

        // Configuración del botón flotante para buscar
        botonBuscar = requireActivity().findViewById(R.id.botonBuscar);
        botonBuscar.setOnClickListener(v -> {
            executor.execute(() -> {
                apiResponse = new APIResponse();
                int errores=0;
                // Realizar validación de los ingredientes y actualizar el adaptador
                for (int i = 0; i < itemList.size(); i++) {
                    BusquedaDataModel item = itemList.get(i);
                    boolean esCorrecto = apiResponse.esIngredienteCorrecto(traducirPalabra(item.getEditText()));
                    if(!esCorrecto){
                        errores++;
                    }
                    int finalI = i;
                    requireActivity().runOnUiThread(() -> {
                        adapter.setErrorAtPosition(finalI, !esCorrecto); // Marcar error si no es correcto
                    });
                }
                if(errores==0){
                    abrirBusquedaActivity();
                }



                // Obtener la lista de ingredientes del RecyclerView (manteniendo los valores de los EditText)
                List<String> listaIngredientes = new ArrayList<>();
                for (BusquedaDataModel item : itemList) {
                    listaIngredientes.add(item.getEditText());
                }
                // Actualiza el adaptador con los nuevos datos
                //adapter.updateDataList(nuevaListaDeDatos); // nuevaListaDeDatos contiene los resultados de la búsqueda
            });
        });
    }

    private String traducirPalabra(String texto) {
        Translate translate = TranslateOptions.newBuilder().setApiKey("AIzaSyAlF4NerB2lB0-SWaSSwnjzO7XEB8nSVCw").build().getService();
        Translation translation = translate.translate(texto, Translate.TranslateOption.targetLanguage("en"));
        String translatedText = translation.getTranslatedText();
        Log.d("Translation", "Texto traducido: " + translatedText);
        return translatedText;
    }

    private void inicializarRecyclerView() {
        recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void inicializarSpinners() {
        configurarSpinner(binding.spinnerCocina, R.array.menu_tipo);
        configurarSpinner(binding.spinnerNacionalidad, R.array.menu_nacionalidad);
        configurarSpinner(binding.spinnerDieta, R.array.menu_dietas);
        configurarSpinner(binding.spinnerIntolerancias, R.array.menu_intolerancias);
    }

    private void configurarSpinner(Spinner spinner, int arrayResource) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getContext(), arrayResource, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void inicializarSeekBars() {
        configurarSeekBar(binding.seekBarCarbo, binding.textViewCarbo);
        configurarSeekBar(binding.seekBarProteina, binding.textViewProteina);
        configurarSeekBar(binding.seekBarCalorias, binding.textViewCalorias);
    }

    private void configurarSeekBar(SeekBar seekBar, TextView textView) {
        textView.setText(String.valueOf(seekBar.getProgress()));
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textView.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    private void configurarCheckBoxes() {
        configurarCheckBox(binding.checkCocina, binding.spinnerCocina);
        configurarCheckBox(binding.checkNacionalidad, binding.spinnerNacionalidad);
        configurarCheckBox(binding.checkDieta, binding.spinnerDieta);
        configurarCheckBox(binding.checkIntolerancias, binding.spinnerIntolerancias);
        configurarCheckBox(binding.checkCarbo, binding.seekBarCarbo);
        configurarCheckBox(binding.checkProteina, binding.seekBarProteina);
        configurarCheckBox(binding.checkCalorias, binding.seekBarCalorias);
    }

    private void configurarCheckBox(CheckBox checkBox, View viewToEnable) {
        viewToEnable.setEnabled(false);
        checkBox.setOnClickListener(v -> viewToEnable.setEnabled(checkBox.isChecked()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void obtenerTodosIngredientes() {
        List<String> ingredients = apiResponse.getAllIngredients();
        if (ingredients != null && !ingredients.isEmpty()) {
            new Handler(Looper.getMainLooper()).post(() -> {
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
    }

    private void obtener10RecetasManzana() {
        List<Receta> recipes = apiResponse.searchAppleRecipes();
        if (recipes != null) {
            new Handler(Looper.getMainLooper()).post(() -> {
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
    }

    private void abrirBusquedaActivity() {

        Intent intent = new Intent(BusquedaFragmento.this, BusquedaActivity.class);
        intent.putExtra("userEmail", mail);
        intent.putExtra("userName", nombre);
        startActivity(intent);
    }
}
