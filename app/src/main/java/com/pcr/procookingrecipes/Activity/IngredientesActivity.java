package com.pcr.procookingrecipes.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pcr.procookingrecipes.databinding.ActivityIngredientesBinding;

public class IngredientesActivity extends AppCompatActivity {

    private ActivityIngredientesBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflar el layout usando ViewBinding
        binding = ActivityIngredientesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Configurar el botón para guardar y volver
        binding.botonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener el estado de los checkboxes
                boolean opcion1 = binding.checkboxOption1.isChecked();
                boolean opcion2 = binding.checkboxOption2.isChecked();

                // Crear un intent para devolver los datos
                Intent data = new Intent();
                data.putExtra("option1", opcion1);
                data.putExtra("option2", opcion2);

                // Configurar el resultado y cerrar la Activity
                setResult(Activity.RESULT_OK, data);
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Liberar el binding cuando la actividad se destruye
        binding = null;
    }

}
