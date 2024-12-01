package com.pcr.procookingrecipes.Adapters.RecyclerViewIngrediente;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pcr.procookingrecipes.R;

import java.util.List;

public class ItemIngredienteAdapter extends RecyclerView.Adapter<ItemIngredienteAdapter.MyViewHolder> {
    private List<IngredienteDataModel> dataList;

    public ItemIngredienteAdapter(List<IngredienteDataModel> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflar la vista del item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ingrediente, parent, false);

        MyViewHolder holder = new MyViewHolder(view);

        // Configurar el TextWatcher para el EditText para cada elemento
        holder.textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            // Actualizar el valor del EditText en el modelo de datos cuando cambie
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Obtener la posición del elemento en la lista
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    dataList.get(position).setEditText(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };
        // Asignar el TextWatcher al EditText
        holder.editText.addTextChangedListener(holder.textWatcher);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        IngredienteDataModel item = dataList.get(position);

        // Verificar si el ítem tiene error
        if (item.hasError()) {
            holder.editText.setError(item.getMensajeError());
        } else {
            holder.editText.setError(null); // Limpiar error si no existe
        }

        // Establecer el texto en el EditText solo cuando se recarga
        holder.editText.setText(item.getEditText());

        // Configura el botón "borrar" para eliminar el elemento
        holder.borrar.setOnClickListener(v -> {
            int currentPosition = holder.getAdapterPosition();

            // Eliminar el elemento de la lista y notificar el cambio
            if (currentPosition != RecyclerView.NO_POSITION) {
                dataList.remove(currentPosition);
                notifyItemRemoved(currentPosition);
            }
            // Mostrar el TextView si la lista está vacía
            if (dataList.isEmpty()) {
                TextView tvRecyclerSinDatos = holder.itemView.getRootView().findViewById(R.id.tvRecyclerSinDatos);
                tvRecyclerSinDatos.setVisibility(View.VISIBLE);
            // Ocultar el TextView si la lista no está vacía
            } else {
                TextView tvRecyclerSinDatos = holder.itemView.getRootView().findViewById(R.id.tvRecyclerSinDatos);
                tvRecyclerSinDatos.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    // Actualizar la lista de datos
    public void setErrorAtPosition(int position, boolean hasError, int tipoError) {
        // Verificar el tipo de error
        if(tipoError==1){
            dataList.get(position).setMensajeError("Ingrediente no válido.");
        }else if(tipoError==2){
            dataList.get(position).setMensajeError("Ingrediente duplicado.");
        }else if(tipoError==3){
            dataList.get(position).setMensajeError("Ingrediente vacío.");
        }
        // Establecer el error en el modelo de datos
        dataList.get(position).setError(hasError);
        // Actualizar el ítem en el RecyclerView
        notifyItemChanged(position);
    }

    // Actualizar la lista de datos
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // Declarar las vistas
        Button borrar;
        EditText editText;
        TextWatcher textWatcher; // Definir el TextWatcher aquí para evitar duplicación
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            // Inicializar las vistas
            borrar = itemView.findViewById(R.id.botonEliminar);
            editText = itemView.findViewById(R.id.ETDatos);
        }
    }
}
