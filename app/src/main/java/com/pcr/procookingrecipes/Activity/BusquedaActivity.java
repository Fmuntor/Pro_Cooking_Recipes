package com.pcr.procookingrecipes.Activity;

import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.
import com.pcr.procookingrecipes.ConexionAPI.Spoonacular.APIResponse;
import com.pcr.procookingrecipes.R;
import com.pcr.procookingrecipes.databinding.ActivityBusquedaBinding;
import java.util.ArrayList;

public class BusquedaActivity extends AppCompatActivity {

    private ActivityBusquedaBinding binding;
    private int id;
    ArrayList<String> listaIDs;
    private RecyclerView recyclerView;
    private APIResponse apiResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityBusquedaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Recupera la lista de IDs desde el Intent
        listaIDs = getIntent().getStringArrayListExtra("listaIDs");

        //Hacer una busqueda a la API con los IDs recuperando titulo, imagen, comensales y tiempo.
        apiResponse = new APIResponse();
        for(int i=0; i<listaIDs.size(); i++){
            String respuesta = apiResponse.leerDeID(716429);
            Toast.makeText(this, respuesta, Toast.LENGTH_SHORT).show();
        }


        inicializarRecyclerView();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflar el menú; esto agrega elementos a la barra de acción si está presente
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void inicializarRecyclerView() {
        recyclerView = binding.recyclerViewBusqueda;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));;
    }

}
