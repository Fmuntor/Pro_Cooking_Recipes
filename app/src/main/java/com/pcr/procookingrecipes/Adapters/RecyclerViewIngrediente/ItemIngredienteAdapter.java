package com.pcr.procookingrecipes.Adapters.RecyclerViewIngrediente;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ingrediente, parent, false);

        MyViewHolder holder = new MyViewHolder(view);

        holder.textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    dataList.get(position).setEditText(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };
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
        holder.editText.setText(item.getEditText()); // No sobreescribir valores anteriores



        // Configura el botón "borrar" para eliminar el elemento
        holder.borrar.setOnClickListener(v -> {
            int currentPosition = holder.getAdapterPosition();
            if (currentPosition != RecyclerView.NO_POSITION && dataList.size() > 1) {
                dataList.remove(currentPosition);
                notifyItemRemoved(currentPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    // Método para agregar un nuevo elemento a la lista y actualizar el RecyclerView
    public void addItem(IngredienteDataModel newItem) {
        dataList.add(newItem);
        notifyItemInserted(dataList.size() - 1); // Notifica que se agregó un nuevo elemento al final
    }

    // Método para actualizar la lista de datos después de una nueva búsqueda
    public void updateDataList(List<IngredienteDataModel> newDataList) {
        this.dataList.clear(); // Limpiar la lista actual
        this.dataList.addAll(newDataList); // Agregar los nuevos elementos
        notifyDataSetChanged(); // Notificar que se ha actualizado la lista
    }

    public void setErrorAtPosition(int position, boolean hasError, int tipoError) {
        if(tipoError==1){
            dataList.get(position).setMensajeError("Ingrediente no válido.");
        }else{
            dataList.get(position).setMensajeError("Ingrediente duplicado.");
        }
        dataList.get(position).setError(hasError);
        notifyItemChanged(position); // Actualizar el ítem en el RecyclerView
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        Button borrar;
        EditText editText;
        TextWatcher textWatcher; // Definir el TextWatcher aquí para evitar duplicación

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            borrar = itemView.findViewById(R.id.botonEliminar);
            editText = itemView.findViewById(R.id.ETDatos);
        }
    }
}
