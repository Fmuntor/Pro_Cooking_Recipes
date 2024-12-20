package com.pcr.procookingrecipes.Adapters.ReciclerViewBusqueda;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pcr.procookingrecipes.ui.Activity.RecetaActivity;
import com.pcr.procookingrecipes.R;
import com.pcr.procookingrecipes.Receta.RecetaBusqueda;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ItemBusquedaAdapter extends RecyclerView.Adapter<ItemBusquedaAdapter.MyViewHolder> {
    private List<RecetaBusqueda> dataList;

    // Constructor
    public ItemBusquedaAdapter(List<RecetaBusqueda> dataList) {
        // Si dataList es null, inicializar con una lista vacía
        this.dataList = (dataList != null) ? dataList : new ArrayList<>();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflar la vista del item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_busqueda, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // Asegurarse de que dataList no sea null y tenga elementos
        if (dataList != null && position < dataList.size()) {
            // Obtener el item en la posición
            RecetaBusqueda item = dataList.get(position);

            // Asignar los datos al ViewHolder
            holder.tituloTextView.setText(item.getTitle());
            Picasso.get().load(item.getImage()).into(holder.imagenReceta);
            holder.comensalesTextView.setText("Para " + item.getServings() + " personas.");
            holder.tiempoPreparacionTextView.setText("Listo en: " + item.getReadyInMinutes() + " minutos.");

            // Configurar el botón "ver receta" para abrir los detalles
            holder.verReceta.setOnClickListener(v -> {
                int currentPosition = holder.getAdapterPosition();
                if (currentPosition != RecyclerView.NO_POSITION) {
                    int id = dataList.get(currentPosition).getId();
                    Intent intent = new Intent(holder.itemView.getContext(), RecetaActivity.class);

                    // Pasar el ID de la receta, la receta completa y la lista completa a la nueva actividad
                    intent.putExtra("ID", id); // Pasar el ID de la receta a la nueva actividad
                    intent.putExtra("Receta", item);
                    intent.putExtra("RecetasCompletas", (ArrayList<RecetaBusqueda>) dataList);

                    // Iniciar la nueva actividad
                    holder.itemView.getContext().startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        // Verificar si dataList es null antes de acceder a su tamaño
        return (dataList != null) ? dataList.size() : 0;
    }

    // Actualizar la lista de datos
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // Declarar las vistas
        public Button verReceta;
        TextView tituloTextView, comensalesTextView, tiempoPreparacionTextView;
        ImageView imagenReceta;

        // Constructor de la clase ViewHolder
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            // Inicializar las vistas
            tituloTextView = itemView.findViewById(R.id.tituloReceta);
            comensalesTextView = itemView.findViewById(R.id.comensalesReceta);
            tiempoPreparacionTextView = itemView.findViewById(R.id.tiempoReceta);
            imagenReceta = itemView.findViewById(R.id.imagenReceta);
            verReceta = itemView.findViewById(R.id.botonVerReceta);
        }
    }
}
