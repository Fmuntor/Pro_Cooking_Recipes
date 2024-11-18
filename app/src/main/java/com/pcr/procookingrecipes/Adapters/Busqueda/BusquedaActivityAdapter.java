package com.pcr.procookingrecipes.Adapters.Busqueda;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pcr.procookingrecipes.R;
import com.squareup.picasso.Picasso;

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
                .inflate(R.layout.item_ingrediente, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        BusquedaDataModel item = dataList.get(position);

        // Asignar los datos al ViewHolder
        holder.titleTextView.setText(item.getTitle());
        holder.servingsTextView.setText("Servings: " + item.getServings());
        holder.preparationTimeTextView.setText("Prep time: " + item.getPreparationMinutes() + " mins");

        // Usar Picasso para cargar la imagen
        Picasso.get().load(item.getImage()).into(holder.imageView);
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
        TextView titleTextView, servingsTextView, preparationTimeTextView;
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.tituloReceta);
            servingsTextView = itemView.findViewById(R.id.comensalesReceta);
            preparationTimeTextView = itemView.findViewById(R.id.tiempoReceta);
            imageView = itemView.findViewById(R.id.imagenReceta);
        }
    }
}
