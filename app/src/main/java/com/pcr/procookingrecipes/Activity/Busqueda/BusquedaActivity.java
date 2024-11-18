package com.pcr.procookingrecipes.Activity.Busqueda;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pcr.procookingrecipes.Adapters.ReciclerViewBusqueda.ItemBusquedaAdapter;
import com.pcr.procookingrecipes.Adapters.ReciclerViewBusqueda.BusquedaDataModel;
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
    private ArrayList<String> listaIDs;
    private ArrayList<String> listaImagenes;
    private RecyclerView recyclerView;
    private APIResponse apiResponse;
    private ItemBusquedaAdapter adapter;
    private List<BusquedaDataModel> recetaList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityBusquedaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        inicializarRecyclerView();

        recetaList = new ArrayList<>();
        //recetaList.add(new BusquedaDataModel("titulo", "imagen", 1, 1));
        adapter = new ItemBusquedaAdapter(recetaList);
        binding.recyclerViewBusqueda.setAdapter(adapter);

        // Recupera la lista de IDs desde el Intent
        listaIDs = getIntent().getStringArrayListExtra("listaIDs");
        listaImagenes = getIntent().getStringArrayListExtra("listaImagenes");

        // Configura la conexión con la API
        apiResponse = new APIResponse();
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < listaIDs.size(); i++) {
                    int id = Integer.parseInt(listaIDs.get(i));
                    RecetaBusqueda recetaBusqueda = apiResponse.leerDeID(id);
                    recetaBusqueda.setImage(listaImagenes.get(i));

                    if (recetaBusqueda != null) {

                        // Agrega la receta a la lista de resultados
                        recetaList.add(new BusquedaDataModel(
                                recetaBusqueda.getTitle(),
                                recetaBusqueda.getImage(),
                                recetaBusqueda.getServings(),
                                recetaBusqueda.getReadyInMinutes()
                        ));

                        // Actualiza el RecyclerView en el hilo principal
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }
                }
            }
        });
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
