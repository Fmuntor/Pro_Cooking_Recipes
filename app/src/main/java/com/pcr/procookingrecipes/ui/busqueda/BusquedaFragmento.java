package com.pcr.procookingrecipes.ui.busqueda;

import static com.pcr.procookingrecipes.ConexionAPI.Traductor.Traductor_API_Response.traducir;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pcr.procookingrecipes.Activity.BusquedaActivity;
import com.pcr.procookingrecipes.Adapters.RecyclerViewIngrediente.IngredienteDataModel;
import com.pcr.procookingrecipes.Adapters.RecyclerViewIngrediente.ItemIngredienteAdapter;
import com.pcr.procookingrecipes.ConexionAPI.SecurePreferences;
import com.pcr.procookingrecipes.ConexionAPI.Spoonacular.Spoonacular_API_Response;
import com.pcr.procookingrecipes.R;
import com.pcr.procookingrecipes.Receta.Receta;
import com.pcr.procookingrecipes.Receta.RecetaBusqueda;
import com.pcr.procookingrecipes.databinding.FragmentoBusquedaBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class BusquedaFragmento extends Fragment {

    private FragmentoBusquedaBinding binding;
    private RecyclerView recyclerView;
    private ItemIngredienteAdapter adapter;
    private List<IngredienteDataModel> itemList;
    private FloatingActionButton botonIntroducirItem, botonBuscar;
    private Spoonacular_API_Response apiResponse;
    private final Executor executor = Executors.newSingleThreadExecutor();
    private List<RecetaBusqueda> recetasCompletas;
    private String[] listaParametros;
    TextView tvRecyclerSinDatos;
    private EditText numeroRecetas;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        BusquedaFragmentoViewModel busquedaViewModel = new ViewModelProvider(this).get(BusquedaFragmentoViewModel.class);
        binding = FragmentoBusquedaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        inicializarRecyclerView();
        inicializarSpinners();
        inicializarSeekBars();

        itemList = new ArrayList<>();
        itemList.add(new IngredienteDataModel("uva"));

        adapter = new ItemIngredienteAdapter(itemList);
        binding.recyclerView.setAdapter(adapter);

        tvRecyclerSinDatos = root.findViewById(R.id.tvRecyclerSinDatos);
        if (itemList.isEmpty()) {
            tvRecyclerSinDatos.setVisibility(View.VISIBLE);
        } else {
            tvRecyclerSinDatos.setVisibility(View.GONE);
        }

        configurarCheckBoxes();



        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        SecurePreferences.cargarClavesDesdeArchivo(getContext());
        apiResponse = new Spoonacular_API_Response(getContext());

        // Configuración del botón flotante para añadir nuevos ítems al RecyclerView
        botonIntroducirItem = requireActivity().findViewById(R.id.botonIntroducirItem);
        botonIntroducirItem.setImageResource(R.drawable.ic_plus);

        botonIntroducirItem.setOnClickListener(v -> {
            // Añadir un nuevo ítem al RecyclerView
            if (itemList.isEmpty()) {
                tvRecyclerSinDatos.setVisibility(View.GONE);
            }
            itemList.add(new IngredienteDataModel(""));
            adapter.notifyItemInserted(itemList.size() - 1); // Notificar al adaptador
        });

        // Configuración del botón flotante para buscar
        botonBuscar = requireActivity().findViewById(R.id.botonBuscar);
        botonBuscar.setOnClickListener(v -> {
            new android.app.AlertDialog.Builder(requireContext())
                    .setTitle("Realizar búsqueda")
                    .setMessage("¿Seguro que quieres realizar la búsqueda?.")
                    .setPositiveButton("Aceptar", (dialog, which) -> {
                        executor.execute(this::realizarBusqueda);
                        dialog.dismiss();
                    })
                    .setNegativeButton("Cancelar", (dialog, which) -> {
                        dialog.dismiss();
                    })
                    .setCancelable(false)
                    .show();
        });
        // Activar los botones flotantes
        FloatingActionButton botonIntroducirItem = requireActivity().findViewById(R.id.botonIntroducirItem);
        botonIntroducirItem.setVisibility(View.VISIBLE);
        FloatingActionButton botonBuscar = requireActivity().findViewById(R.id.botonBuscar);
        botonBuscar.setVisibility(View.VISIBLE);
    }

    private void realizarBusqueda() {


        int errores = 0;
        recetasCompletas = new ArrayList<>();

        // Realizar validación de los ingredientes y actualizar el adaptador
        for (int i = 0; i < itemList.size(); i++) {
            //Comprobar si el ingrediente está vacío
            if (itemList.get(i).getEditText().equals("")) {
                errores++;
                int finalI = i;
                requireActivity().runOnUiThread(() -> {
                    adapter.setErrorAtPosition(finalI, true, 3); // Marcar error
                });
            }
            //Comprobar si ya existe el ingrediente en la lista
            for (int j = 0; j < i; j++) {
                if (itemList.get(i).getEditText().equals(itemList.get(j).getEditText())) {
                    errores++;
                    int finalI = i;
                    requireActivity().runOnUiThread(() -> {
                        adapter.setErrorAtPosition(finalI, true, 2); // Marcar error
                    });
                }
            }
            //Comprobar si el ingrediente es correcto
            IngredienteDataModel item = itemList.get(i);
            String respuesta = apiResponse.esIngredienteCorrecto(traducir(getContext(),item.getEditText(), "ingles"));
            if (respuesta != null) {
                if (respuesta.equals("Error")) {
                    errores++;
                    int finalI = i;
                    requireActivity().runOnUiThread(() -> {
                        adapter.setErrorAtPosition(finalI, true, 1); // Marcar error
                    });
                }
            }
        }

        // Validación de errores
        if (errores == 0) {
            requireActivity().runOnUiThread(() -> {
                Toast.makeText(getContext(), "Buscando recetas...", Toast.LENGTH_SHORT).show();

            });

            // Validación para el número de recetas
            numeroRecetas = requireActivity().findViewById(R.id.numeroRecetas);
            String numRecetasText = numeroRecetas.getText().toString();

            // Validar el número de recetas
            try {
                int numRecetas = Integer.parseInt(numRecetasText);
                if (numRecetas < 1 || numRecetas > 12) {
                    requireActivity().runOnUiThread(() -> {
                        numeroRecetas.setError("El número de recetas debe estar entre 1 y 12");
                    });
                    return;
                }

                // Si la validación pasa, realizar la búsqueda
                List<Receta> idRecetas = apiResponse.busquedaCompleta(escribirConsultaFinal(), numRecetas);

                // Obtener las recetas completas
                for (Receta receta : idRecetas) {
                    recetasCompletas.add(apiResponse.getInformacionReceta(receta.getId()));
                }

                // Crear una lista de IDs
                List<String> listaID = new ArrayList<>();
                for (Receta receta : idRecetas) {
                    listaID.add(String.valueOf(receta.getId()));
                }

                // Guardar en la base de datos
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference referenciaHistorial = database.getReference("historial");

                // Crear un mapa con los datos del historial
                Map<String, Object> historialData = new HashMap<>();
                historialData.put("fecha", new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()));
                historialData.put("parametros", new ArrayList<>(Arrays.asList(listaParametros)));
                historialData.put("recetas", listaID);

                // Obtener el correo electrónico del usuario
                String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                //Eliminar el punto
                if (email.contains(".")) {
                    email = email.replace(".", "·");
                }
                // Crear una etiqueta compuesta
                String etiquetaCompleta = email + " - " + FirebaseAuth.getInstance().getCurrentUser().getUid();

                // Guardar en la base de datos
                referenciaHistorial.child(etiquetaCompleta).push().setValue(historialData);

                // Mostrar un mensaje de éxito
                if (recetasCompletas.isEmpty()) {
                    // Mostrar una ventana de confirmación (AlertDialog)
                    requireActivity().runOnUiThread(() -> {
                        // Mostrar una ventana de confirmación (AlertDialog)
                        new android.app.AlertDialog.Builder(requireContext())
                                .setTitle("No se encontraron recetas")
                                .setMessage("Lo siento, no hemos encontrado recetas que coincidan con tu búsqueda.")
                                .setPositiveButton("Aceptar", (dialog, which) -> {
                                    // Acción al presionar "Aceptar", puedes cerrar el diálogo o realizar otra acción
                                    dialog.dismiss();
                                })
                                .setCancelable(false)
                                .show();
                    });

                }else{
                    abrirBusquedaActivity();
                }

            } catch (NumberFormatException e) {
                // Manejo de error si el valor no es un número válido
                numeroRecetas.setError("Por favor ingresa un número válido");
            }
        }
    }

    private String escribirConsultaFinal() {
        // Crear un StringBuilder para almacenar la consulta final
        StringBuilder consultaFinal = new StringBuilder();
        listaParametros = new String[8];

        if (!itemList.isEmpty()) {
            StringBuilder ingredientesFinal = new StringBuilder();  // Usar StringBuilder para eficiencia en concatenación
            for (int i = 0; i < itemList.size(); i++) {
                String ingrediente = itemList.get(i).getEditText();  // Obtener el ingrediente
                ingredientesFinal.append(ingrediente).append(",");  // Agregar el ingrediente a ingredientesFinal
                consultaFinal.append(traducir(getContext(),ingrediente, "ingles")).append(",");  // Traducir y agregar a consultaFinal

            }
            //eliminar la ultima coma
            ingredientesFinal.deleteCharAt(ingredientesFinal.length() - 1);
            listaParametros[7] = ingredientesFinal.toString();
        } else {
            listaParametros[7] = "";
        }

        // Eliminar el último carácter "," si existe
        if (consultaFinal.length() > 0 && consultaFinal.charAt(consultaFinal.length() - 1) == ',') {
            consultaFinal.deleteCharAt(consultaFinal.length() - 1);
        }

        // CheckBox: Cocina
        if (binding.checkCocina.isChecked()) {
            consultaFinal.append("&type=");

            String opcionSeleccionada = binding.spinnerCocina.getSelectedItem().toString();
            listaParametros[0] = opcionSeleccionada;
            consultaFinal.append(traducir(getContext(),opcionSeleccionada, "ingles"));
        } else {
            listaParametros[0] = "";
        }

        // CheckBox: Nacionalidad
        if (binding.checkNacionalidad.isChecked()) {
            consultaFinal.append("&cuisine=");

            String opcionSeleccionada = binding.spinnerNacionalidad.getSelectedItem().toString();
            listaParametros[1] = opcionSeleccionada;

            consultaFinal.append(traducir(getContext(),opcionSeleccionada, "ingles"));
        } else {
            listaParametros[1] = "";
        }

        // CheckBox: Dieta
        if (binding.checkDieta.isChecked()) {
            consultaFinal.append("&diet=");

            String opcionSeleccionada = binding.spinnerDieta.getSelectedItem().toString();
            listaParametros[2] = opcionSeleccionada;

            consultaFinal.append(traducir(getContext(),opcionSeleccionada, "ingles"));
        } else {
            listaParametros[2] = "";
        }

        // CheckBox: Intolerancias
        if (binding.checkIntolerancias.isChecked()) {
            consultaFinal.append("&intolerances=");
            String opcionSeleccionada = binding.spinnerIntolerancias.getSelectedItem().toString();
            listaParametros[3] = opcionSeleccionada;

            consultaFinal.append(traducir(getContext(),opcionSeleccionada, "ingles"));
        } else {
            listaParametros[3] = "";
        }

        // CheckBox: Carbohidratos
        if (binding.checkCarbo.isChecked()) {
            consultaFinal.append("&minCarbs=");
            int valorSeleccionado = binding.seekBarCarbo.getProgress();
            listaParametros[4] = String.valueOf(valorSeleccionado);
            consultaFinal.append(valorSeleccionado);
        } else {
            listaParametros[4] = "";
        }

        // CheckBox: Proteínas
        if (binding.checkProteina.isChecked()) {
            consultaFinal.append("&minProtein=");
            int valorSeleccionado = binding.seekBarProteina.getProgress();
            listaParametros[5] = String.valueOf(valorSeleccionado);
            consultaFinal.append(valorSeleccionado);
        } else {
            listaParametros[5] = "";
        }

        // CheckBox: Calorías
        if (binding.checkCalorias.isChecked()) {
            consultaFinal.append("&maxCalories=");

            int valorSeleccionado = binding.seekBarCalorias.getProgress();
            listaParametros[6] = String.valueOf(valorSeleccionado);
            consultaFinal.append(valorSeleccionado);
        } else {
            listaParametros[6] = "";
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
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
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


}
