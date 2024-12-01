package com.pcr.procookingrecipes.ui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.pcr.procookingrecipes.R;
import com.pcr.procookingrecipes.databinding.ActivityMainBinding;
import com.pcr.procookingrecipes.ui.Fragment.cuenta.CuentaFragmento;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflar el layout usando ViewBinding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Configurar el toolbar
        setSupportActionBar(binding.appBarMain.toolbar);
        //Mostrar un mensaje al dar click al item del toolbar
        binding.appBarMain.toolbar.setOnMenuItemClickListener(item -> {
            Intent intent = new Intent(MainActivity.this, CuentaFragmento.class);
            startActivity(intent);
            return true;

        });

        // Configurar el menú de navegación
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        // Configurar la app bar
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.cabecera_busqueda, R.id.cabecera_favoritos, R.id.cabecera_cuenta, R.id.cabecera_historial)
                .setOpenableLayout(drawer)
                .build();

        // Configurar el controlador de navegación
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Obtener el headerView y luego el TextView del nombre y mail
        View headerView = navigationView.getHeaderView(0);

        // Obtener las vistas del header
        ImageView imageView = headerView.findViewById(R.id.IVCabecera);
        TextView mailCabecera = headerView.findViewById(R.id.mailCabecera);

        // Establecer la imagen y el mail en el header
        imageView.setImageResource(R.drawable.icono_pcr_negro_transparente);
        mailCabecera.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        // Obtener el fragmento adecuado de la jerarquía de navegación
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_content_main);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflar el menú: Agregar elementos a la barra de acción si está presente
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        // Manejar la navegación hacia arriba
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);

        // Configurar la navegación hacia arriba
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

}
