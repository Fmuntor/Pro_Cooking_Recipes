package com.pcr.procookingrecipes.ui.busqueda;

import static com.pcr.procookingrecipes.ConexionAPI.Traductor.Traductor.traducir;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import com.pcr.procookingrecipes.Activity.Busqueda.BusquedaActivity;
import com.pcr.procookingrecipes.Adapters.RecyclerViewIngrediente.IngredienteDataModel;
import com.pcr.procookingrecipes.Adapters.RecyclerViewIngrediente.ItemIngredienteAdapter;
import com.pcr.procookingrecipes.ConexionAPI.Spoonacular.APIResponse;
import com.pcr.procookingrecipes.InstruccionesReceta.Equipment;
import com.pcr.procookingrecipes.InstruccionesReceta.Ingredient;
import com.pcr.procookingrecipes.InstruccionesReceta.Instruction;
import com.pcr.procookingrecipes.InstruccionesReceta.Step;
import com.pcr.procookingrecipes.R;
import com.pcr.procookingrecipes.Receta.Receta;
import com.pcr.procookingrecipes.Receta.RecetaBusqueda;
import com.pcr.procookingrecipes.databinding.FragmentoBusquedaBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class BusquedaFragmento extends Fragment {

    private FragmentoBusquedaBinding binding;
    private RecyclerView recyclerView;
    private ItemIngredienteAdapter adapter;
    private List<IngredienteDataModel> itemList;
    private FloatingActionButton botonIntroducirItem, botonBuscar;
    private APIResponse apiResponse;
    private final Executor executor = Executors.newSingleThreadExecutor();
    private List<RecetaBusqueda> recetasCompletas;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        BusquedaFragmentoViewModel busquedaViewModel = new ViewModelProvider(this).get(BusquedaFragmentoViewModel.class);
        binding = FragmentoBusquedaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        inicializarRecyclerView();
        inicializarSpinners();
        inicializarSeekBars();

        itemList = new ArrayList<>();
        itemList.add(new IngredienteDataModel("manzana"));
        itemList.add(new IngredienteDataModel("uva"));


        adapter = new ItemIngredienteAdapter(itemList);
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
            itemList.add(new IngredienteDataModel(""));
            adapter.notifyItemInserted(itemList.size() - 1); // Notificar al adaptador
        });

        // Configuración del botón flotante para buscar
        botonBuscar = requireActivity().findViewById(R.id.botonBuscar);
        botonBuscar.setOnClickListener(v -> {
            executor.execute(() -> {
                apiResponse = new APIResponse();
                int errores=0;
                recetasCompletas = new ArrayList<>();

                // Realizar validación de los ingredientes y actualizar el adaptador
                for (int i = 0; i < itemList.size(); i++) {
                    //Comprobar si ya existe el ingrediente en la lista
                    for (int j = 0; j < i; j++) {
                        if (itemList.get(i).getEditText().equals(itemList.get(j).getEditText())) {
                            errores++;
                            int finalI = i;
                            requireActivity().runOnUiThread(() -> {
                                adapter.setErrorAtPosition(finalI, true,2); // Marcar error
                            });
                        }
                    }
                    IngredienteDataModel item = itemList.get(i);
                    String respuesta = apiResponse.esIngredienteCorrecto(traducir(item.getEditText(), "ingles"));
                    if(respuesta.equals("Error")){
                        errores++;
                        int finalI = i;
                        requireActivity().runOnUiThread(() -> {
                            adapter.setErrorAtPosition(finalI, true,1); // Marcar error
                        });
                    }
                }
                if(errores==0){
                    // Si no hay errores, se realiza la busqueda completa, con ingredientes y opciones seleccionadas

                    List<Receta> idRecetas = apiResponse.busquedaCompleta(escribirConsultaFinal(),6);

                    for (Receta receta : idRecetas) {

                        recetasCompletas.add(apiResponse.getInformacionReceta(receta.getId()));
                    }
                    List<Instruction> instrucciones = apiResponse.getInstrucciones(632631);

                    if (instrucciones != null && !instrucciones.isEmpty()) {
                        String recetaFormateada = formatearInstrucciones(instrucciones);
                        Log.d("Receta", recetaFormateada); // Muestra en la consola
                    } else {
                        Log.d("Receta", "No se encontraron instrucciones.");
                    }

                    /*
                    for (RecetaBusqueda receta : recetasCompletas) {
                        Log.d("Receta",
                                "ID: " + receta.getId() +
                                    ", Título: " + receta.getTitle() +
                                    ", URL Imagen: " + receta.getImage() +
                                    ", Servings: " + receta.getServings() +
                                    ", Ready In Minutes: " + receta.getReadyInMinutes());
                    }
                    */
                    abrirBusquedaActivity();
                }
            });
        });
    }

    private String escribirConsultaFinal() {
        // Crear un StringBuilder para almacenar la consulta final
        StringBuilder consultaFinal = new StringBuilder();

        if(!itemList.isEmpty()){
            for(int i=0;i<itemList.size();i++){
                consultaFinal.append(traducir(itemList.get(i).getEditText(), "ingles")).append(",");
            }
        }

        // Eliminar el último carácter "," si existe
        if (consultaFinal.length() > 0 && consultaFinal.charAt(consultaFinal.length() - 1) == ',') {
            consultaFinal.deleteCharAt(consultaFinal.length() - 1);
        }

        // CheckBox: Cocina
        if (binding.checkCocina.isChecked()) {
            consultaFinal.append("&type=");

            String opcionSeleccionada = binding.spinnerCocina.getSelectedItem().toString();
            consultaFinal.append(traducir(opcionSeleccionada, "ingles"));
        }

        // CheckBox: Nacionalidad
        if (binding.checkNacionalidad.isChecked()) {
            consultaFinal.append("&cuisine=");

            String opcionSeleccionada = binding.spinnerNacionalidad.getSelectedItem().toString();
            consultaFinal.append(traducir(opcionSeleccionada, "ingles"));
        }

        // CheckBox: Dieta
        if (binding.checkDieta.isChecked()) {
            consultaFinal.append("&diet=");

            String opcionSeleccionada = binding.spinnerDieta.getSelectedItem().toString();
            consultaFinal.append(traducir(opcionSeleccionada, "ingles"));
        }

        // CheckBox: Intolerancias
        if (binding.checkIntolerancias.isChecked()) {
            consultaFinal.append("&intolerances=");
            String opcionSeleccionada = binding.spinnerIntolerancias.getSelectedItem().toString();
            consultaFinal.append(traducir(opcionSeleccionada, "ingles"));
        }

        // CheckBox: Carbohidratos
        if (binding.checkCarbo.isChecked()) {
            consultaFinal.append("&minCarbs=");
            int valorSeleccionado = binding.seekBarCarbo.getProgress();
            consultaFinal.append(valorSeleccionado);
        }

        // CheckBox: Proteínas
        if (binding.checkProteina.isChecked()) {
            consultaFinal.append("&minProtein=");
            int valorSeleccionado = binding.seekBarProteina.getProgress();
            consultaFinal.append(valorSeleccionado);
        }

        // CheckBox: Calorías
        if (binding.checkCalorias.isChecked()) {
            consultaFinal.append("&maxCalories=");

            int valorSeleccionado = binding.seekBarCalorias.getProgress();
            consultaFinal.append(valorSeleccionado);
        }



        // Eliminar el último carácter "," si existe
        if (consultaFinal.length() > 0 && consultaFinal.charAt(consultaFinal.length() - 1) == ',') {
            consultaFinal.deleteCharAt(consultaFinal.length() - 1);
        }


        // Debug para comprobar la consulta generada
        Log.d("ConsultaFinal", "Consulta generada: " + consultaFinal.toString());
        return consultaFinal.toString();
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

    private void abrirBusquedaActivity() {

        Intent intent = new Intent(getActivity(), BusquedaActivity.class);
        intent.putParcelableArrayListExtra("recetasCompletas", (ArrayList<? extends Parcelable>) recetasCompletas);
        startActivity(intent);
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
