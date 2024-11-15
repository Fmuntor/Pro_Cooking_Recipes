package com.pcr.procookingrecipes.Adapters;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
        //holder.spinner.setAdapter(adapter);

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
    public void addItem(BusquedaDataModel newItem) {
        dataList.add(newItem);
        notifyItemInserted(dataList.size() - 1); // Notifica que se agregó un nuevo elemento al final
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        Button borrar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            borrar = itemView.findViewById(R.id.botonEliminar);
        }
    }

}