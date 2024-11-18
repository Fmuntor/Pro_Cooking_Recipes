package com.pcr.procookingrecipes.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pcr.procookingrecipes.Adapters.Busqueda.BusquedaActivityAdapter;
import com.pcr.procookingrecipes.Adapters.Busqueda.BusquedaDataModel;
import com.pcr.procookingrecipes.ConexionAPI.Spoonacular.APIResponse;
import com.pcr.procookingrecipes.R;
import com.pcr.procookingrecipes.Receta.RecetaBusqueda;
import com.pcr.procookingrecipes.databinding.ActivityBusquedaBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BusquedaActivity extends AppCompatActivity {

    private ActivityBusquedaBinding binding;
    private int id;
    ArrayList<String> listaIDs;
    private RecyclerView recyclerView;
    private APIResponse apiResponse;
    private BusquedaActivityAdapter adapter;
    private List<BusquedaDataModel> recetaList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityBusquedaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        inicializarRecyclerView();
        adapter = new BusquedaActivityAdapter(recetaList);
        recyclerView.setAdapter(adapter);

        // Recupera la lista de IDs desde el Intent
        listaIDs = getIntent().getStringArrayListExtra("listaIDs");

        //Hacer una busqueda a la API con los IDs recuperando titulo, imagen, comensales y tiempo.
        apiResponse = new APIResponse();
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<listaIDs.size(); i++) {
                    int id = Integer.parseInt(listaIDs.get(i));
                    RecetaBusqueda recetaBusqueda = apiResponse.leerDeID(id);

                    if (recetaBusqueda != null) {
                        Log.d("Respuesta", "Título: " + recetaBusqueda.getTitle());
                        Log.d("Respuesta", "Imagen: " + recetaBusqueda.getImage());
                        Log.d("Respuesta", "Comensales: " + recetaBusqueda.getServings());
                        Log.d("Respuesta", "Tiempo de preparación: " + recetaBusqueda.getReadyInMinutes());

                        // Aquí puedes agregar la receta a tu lista de resultados
                        // itemList.add(new BusquedaDataModel(receta.getTitle(), receta.getImage(), receta.getServings(), receta.getPreparationMinutes()));
                    }
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Actualiza el RecyclerView con la lista de resultados
                        // adapter.notifyDataSetChanged();
                    }
                });
            }
        });
        //private List<BusquedaDataModel> itemList;

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflar el menú; esto agrega elementos a la barra de acción si está presente
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void inicializarRecyclerView() {
        recyclerView = binding.recyclerViewBusqueda;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

}
