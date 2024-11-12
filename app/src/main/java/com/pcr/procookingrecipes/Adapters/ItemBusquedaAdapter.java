package com.pcr.procookingrecipes.Adapters;


import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pcr.procookingrecipes.R;

import java.util.List;

public class ItemBusquedaAdapter extends RecyclerView.Adapter<ItemBusquedaAdapter.MyViewHolder> {
    private List<BusquedaDataModel> dataList;

    public ItemBusquedaAdapter(List<BusquedaDataModel> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_busqueda, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        BusquedaDataModel item = dataList.get(position);

        // Configura el Spinner principal
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(holder.itemView.getContext(),
                R.array.menu_busqueda, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.spinner.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapterSpinnerExtra;
        // Configura el valor seleccionado en el Spinner principal, sin disparar el listener
        holder.spinner.setOnItemSelectedListener(null);
        int selectedPosition = ((ArrayAdapter) holder.spinner.getAdapter()).getPosition(item.getCampo2());
        holder.spinner.setSelection(selectedPosition, false);

        // Configura el nuevo listener para detectar cambios en el Spinner
        holder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                switch (pos) {
                    case 0:
                        // Acción para ingredientes
                        item.setMostrarSpinnerExtra(false);
                        item.setMostrarSeekBar(false);
                        holder.spinnerExtra.setVisibility(View.GONE);
                        holder.campo.setVisibility(View.VISIBLE);
                        holder.seekBar.setVisibility(View.GONE);
                        holder.valorActualSeekBar.setVisibility(View.GONE);
                        break;

                    case 1:
                        // Acción para el tipo de cocina
                        item.setMostrarSpinnerExtra(true);
                        item.setMostrarSeekBar(false);
                        holder.spinnerExtra.setVisibility(View.VISIBLE);
                        holder.campo.setVisibility(View.GONE);
                        holder.seekBar.setVisibility(View.GONE);
                        holder.valorActualSeekBar.setVisibility(View.GONE);
                        // Cargar valores para spinnerExtra dependiendo de la opción seleccionada
                        ArrayAdapter<CharSequence> adapterSpinnerExtraTipoCocina = ArrayAdapter.createFromResource(
                                holder.itemView.getContext(),
                                R.array.menu_tipo, android.R.layout.simple_spinner_item);
                        adapterSpinnerExtraTipoCocina.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        holder.spinnerExtra.setAdapter(adapterSpinnerExtraTipoCocina);
                        break;

                    case 2:
                        // Acción para la nacionalidad
                        item.setMostrarSpinnerExtra(true);
                        item.setMostrarSeekBar(false);
                        holder.spinnerExtra.setVisibility(View.VISIBLE);
                        holder.campo.setVisibility(View.GONE);
                        holder.seekBar.setVisibility(View.GONE);
                        holder.valorActualSeekBar.setVisibility(View.GONE);
                        // Cargar valores para spinnerExtra dependiendo de la opción seleccionada
                        ArrayAdapter<CharSequence> adapterSpinnerExtraNacionalidad = ArrayAdapter.createFromResource(
                                holder.itemView.getContext(),
                                R.array.menu_nacionalidad, android.R.layout.simple_spinner_item);
                        adapterSpinnerExtraNacionalidad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        holder.spinnerExtra.setAdapter(adapterSpinnerExtraNacionalidad);
                        break;

                    case 3:
                        // Acción para el tipo de dieta
                        item.setMostrarSpinnerExtra(true);
                        item.setMostrarSeekBar(false);
                        holder.spinnerExtra.setVisibility(View.VISIBLE);
                        holder.campo.setVisibility(View.GONE);
                        holder.seekBar.setVisibility(View.GONE);
                        holder.valorActualSeekBar.setVisibility(View.GONE);// Cargar valores para spinnerExtra dependiendo de la opción seleccionada
                        ArrayAdapter<CharSequence> adapterSpinnerExtraDieta = ArrayAdapter.createFromResource(
                                holder.itemView.getContext(),
                                R.array.menu_dietas, android.R.layout.simple_spinner_item);
                        adapterSpinnerExtraDieta.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        holder.spinnerExtra.setAdapter(adapterSpinnerExtraDieta);
                        break;

                    case 4:
                        // Acción para las intolerancias
                        item.setMostrarSpinnerExtra(true);
                        item.setMostrarSeekBar(false);
                        holder.spinnerExtra.setVisibility(View.VISIBLE);
                        holder.campo.setVisibility(View.GONE);
                        holder.seekBar.setVisibility(View.GONE);
                        holder.valorActualSeekBar.setVisibility(View.GONE);// Cargar valores para spinnerExtra dependiendo de la opción seleccionada
                        ArrayAdapter<CharSequence> adapterSpinnerExtraIntolerancias = ArrayAdapter.createFromResource(
                                holder.itemView.getContext(),
                                R.array.menu_intolerancias, android.R.layout.simple_spinner_item);
                        adapterSpinnerExtraIntolerancias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        holder.spinnerExtra.setAdapter(adapterSpinnerExtraIntolerancias);
                        break;



                    case 5:
                        // Acción para los carbohidratos
                        item.setMostrarSpinnerExtra(false);
                        item.setMostrarSeekBar(true);
                        holder.spinnerExtra.setVisibility(View.GONE);
                        holder.campo.setVisibility(View.GONE);
                        holder.seekBar.setVisibility(View.VISIBLE);
                        holder.valorActualSeekBar.setVisibility(View.VISIBLE);  // Mostrar
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            holder.seekBar.setMin(10);
                            holder.seekBar.setMax(100);
                        }
                        break;

                    case 6:
                        // Acción para la proteína
                        item.setMostrarSpinnerExtra(false);
                        item.setMostrarSeekBar(true);
                        holder.spinnerExtra.setVisibility(View.GONE);
                        holder.campo.setVisibility(View.GONE);
                        holder.seekBar.setVisibility(View.VISIBLE);
                        holder.valorActualSeekBar.setVisibility(View.VISIBLE);  // Mostrar
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            holder.seekBar.setMin(10);
                            holder.seekBar.setMax(100);
                        }
                        break;

                    case 7:
                        // Acción para las calorías
                        item.setMostrarSpinnerExtra(false);
                        item.setMostrarSeekBar(true);
                        holder.spinnerExtra.setVisibility(View.GONE);
                        holder.campo.setVisibility(View.GONE);
                        holder.seekBar.setVisibility(View.VISIBLE);
                        holder.valorActualSeekBar.setVisibility(View.VISIBLE);  // Mostrar
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            holder.seekBar.setMin(50);
                            holder.seekBar.setMax(800);
                        }
                        break;

                    default:
                        // Acción por defecto si ninguna condición coincide
                        holder.spinnerExtra.setVisibility(View.GONE);
                        holder.campo.setVisibility(View.VISIBLE);
                        holder.seekBar.setVisibility(View.GONE);
                        holder.valorActualSeekBar.setVisibility(View.GONE);  // Hacer GONE
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Acción por defecto si nada es seleccionado
            }
        });


        // Establece visibilidad inicial de las vistas basado en el modelo
        holder.spinnerExtra.setVisibility(item.isMostrarSpinnerExtra() ? View.VISIBLE : View.GONE);
        holder.campo.setVisibility(item.isMostrarSpinnerExtra() ? View.GONE : View.VISIBLE);

        // Configura el botón "borrar" para eliminar el elemento
        holder.borrar.setOnClickListener(v -> {
            int currentPosition = holder.getAdapterPosition();
            if (currentPosition != RecyclerView.NO_POSITION && dataList.size() > 1) {
                dataList.remove(currentPosition);
                notifyItemRemoved(currentPosition);
                Toast.makeText(v.getContext(), "Elemento eliminado", Toast.LENGTH_SHORT).show();
            }
        });
        holder.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Actualiza el valor en el TextView con el progreso del SeekBar
                holder.valorActualSeekBar.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Puedes agregar lógica aquí si lo deseas, por ejemplo, para mostrar un mensaje al empezar a mover
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Puedes agregar lógica aquí si lo deseas, por ejemplo, para hacer algo cuando el usuario deja de mover
            }
        });
        // Configura el EditText de ingrediente para el autocompletado

    }



    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        Spinner spinner;
        Spinner spinnerExtra;
        AutoCompleteTextView campo;
        Button borrar;
        SeekBar seekBar;  // SeekBar
        TextView valorActualSeekBar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            spinner = itemView.findViewById(R.id.spinnerMenu);
            spinnerExtra = itemView.findViewById(R.id.spinnerExtra);
            campo = itemView.findViewById(R.id.ETDatos);
            borrar = itemView.findViewById(R.id.botonEliminar);
            seekBar = itemView.findViewById(R.id.seekBar);
            valorActualSeekBar = itemView.findViewById(R.id.valorActualSeekBar);
        }
    }

}