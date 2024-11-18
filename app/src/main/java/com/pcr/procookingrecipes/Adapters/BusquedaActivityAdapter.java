package com.pcr.procookingrecipes.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pcr.procookingrecipes.Adapters.Busqueda.BusquedaDataModel;
import com.pcr.procookingrecipes.R;

import java.util.List;

public class BusquedaActivityAdapter extends RecyclerView.Adapter<BusquedaActivityAdapter.MyViewHolder> {
    private List<BusquedaDataModel> dataList;

    public BusquedaActivityAdapter(List<BusquedaDataModel> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_busqueda, parent, false);

        MyViewHolder holder = new MyViewHolder(view);
        addItem(new BusquedaDataModel("","",1,1));
        notifyItemInserted(dataList.size() - 1);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        BusquedaDataModel item = dataList.get(position);

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

    // Método para actualizar la lista de datos después de una nueva búsqueda
    public void updateDataList(List<BusquedaDataModel> newDataList) {
        this.dataList.clear(); // Limpiar la lista actual
        this.dataList.addAll(newDataList); // Agregar los nuevos elementos
        notifyDataSetChanged(); // Notificar que se ha actualizado la lista
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        Button botonVerReceta;
         // Definir el TextWatcher aquí para evitar duplicación

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }
}
