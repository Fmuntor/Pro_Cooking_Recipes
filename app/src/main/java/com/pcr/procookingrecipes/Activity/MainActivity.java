package com.pcr.procookingrecipes.Activity;

import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.pcr.procookingrecipes.Adapters.BusquedaDataModel;
import com.pcr.procookingrecipes.Adapters.SharedViewModel;
import com.pcr.procookingrecipes.R;
import com.pcr.procookingrecipes.databinding.ActivityMainBinding;
import com.pcr.procookingrecipes.ui.inicio.InicioFragmento;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class MainActivity extends AppCompatActivity implements InicioFragmento.OnNewItemListener {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private FirebaseFirestore db;
    private String userEmail;
    private String userName;
    private SharedViewModel sharedViewModel;

    private InicioFragmento inicioFragmento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);

        binding.appBarMain.fab.setOnClickListener(view -> {
            BusquedaDataModel newItem = new BusquedaDataModel("Campo1", "Campo2");
            sharedViewModel.addItem(newItem);
        });

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.cabecera_inicio, R.id.cabecera_favoritos, R.id.cabecera_cuenta)
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Obtén el headerView y luego el TextView del nombre y mail
        View headerView = navigationView.getHeaderView(0);
        TextView nombreCabecera = headerView.findViewById(R.id.nombreCabecera);
        TextView mailCabecera = headerView.findViewById(R.id.mailCabecera);

        // Obtén la instancia de Firestore
        db = FirebaseFirestore.getInstance();

        // Recupera el correo electrónico y nombre del usuario conectado desde el Intent
        userEmail = getIntent().getStringExtra("userEmail");
        userName = getIntent().getStringExtra("userName");

        // Asigna el nombre del usuario (ejemplo)
        nombreCabecera.setText(userName);
        mailCabecera.setText(userEmail);

        // Intentar obtener el fragmento adecuado de la jerarquía de navegación
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_content_main);

        if (navHostFragment != null) {
            // Buscamos el fragmento hijo 'InicioFragmento' dentro del NavHostFragment
            Fragment fragment = navHostFragment.getChildFragmentManager().findFragmentById(R.id.inicioFragmento);

            if (fragment != null && fragment instanceof InicioFragmento) {
                inicioFragmento = (InicioFragmento) fragment;
            }
        }

        // Configuración del botón flotante
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inicioFragmento != null) {
                    // Agregar un nuevo elemento al fragmento `InicioFragmento`
                    addItemToInicioFragmento("Campo1", "Campo2");
                }
            }
        });
    }

    private void addItemToInicioFragmento(String campo1, String campo2) {
        if (inicioFragmento != null) {
            inicioFragmento.addNewItemToRecycler(campo1, campo2);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflar el menú; esto agrega elementos a la barra de acción si está presente
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onAddNewItem() {
        if (inicioFragmento != null) {
            // Puedes abrir un diálogo para pedir valores o simplemente agregar valores de prueba
            String nuevoCampo1 = "Nuevo valor 1";
            String nuevoCampo2 = "Nuevo valor 2";
            inicioFragmento.addNewItemToRecycler(nuevoCampo1, nuevoCampo2);
        }
    }
}
