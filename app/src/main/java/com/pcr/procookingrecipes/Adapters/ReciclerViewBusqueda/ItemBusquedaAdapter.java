package com.pcr.procookingrecipes.Adapters.ReciclerViewBusqueda;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pcr.procookingrecipes.R;
import com.squareup.picasso.Picasso;

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
        MyViewHolder holder = new  MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        BusquedaDataModel item = dataList.get(position);

        // Asignar los datos al ViewHolder
        holder.tituloTextView.setText(item.getTitle());
        holder.comensalesTextView.setText("Para " + item.getServings()+" personas.");
        holder.tiempoPreparacionTextView.setText("Tiempo de preparación: " + item.getPreparationMinutes() + " minutos.");

        // Usar Picasso para cargar la imagen
        Picasso.get().load(item.getImage()).into(holder.imagenReceta);
        // Configura el botón "borrar" para eliminar el elemento
        holder.verReceta.setOnClickListener(v -> {
            int currentPosition = holder.getAdapterPosition();
            if (currentPosition != RecyclerView.NO_POSITION) {
                Toast.makeText(v.getContext(), "Receta seleccionada", Toast.LENGTH_SHORT).show();
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
        public Button verReceta;
        TextView tituloTextView, comensalesTextView, tiempoPreparacionTextView;
        ImageView imagenReceta;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tituloTextView = itemView.findViewById(R.id.tituloReceta);
            comensalesTextView = itemView.findViewById(R.id.comensalesReceta);
            tiempoPreparacionTextView = itemView.findViewById(R.id.tiempoReceta);
            imagenReceta = itemView.findViewById(R.id.imagenReceta);
            verReceta = itemView.findViewById(R.id.botonVerReceta);
        }
    }
}
