package com.pcr.procookingrecipes.Adapters.RecyclerViewReceta.RecetaIngredientes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.pcr.procookingrecipes.R;

import java.util.ArrayList;

public class IngredientesAdapter extends RecyclerView.Adapter<IngredientesAdapter.IngredientesViewHolder> {
    private ArrayList<String> ingredientes;

    public IngredientesAdapter(ArrayList<String> ingredientes) {
        // Si los ingredientes son nulos o tienen menos de 2 elementos, lo llenamos con los mismos ítems.
        if (ingredientes == null) {
            this.ingredientes = new ArrayList<>();
        } else {
            // Añadir dos elementos iguales al final si solo hay uno
            this.ingredientes = new ArrayList<>(ingredientes);
            if (ingredientes.size() < 2) {
                this.ingredientes.add(ingredientes.get(0));  // Duplicar el primer item
            }
        }    }

    @Override
    public IngredientesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflar el layout del item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_receta_ingredientes, parent, false);
        return new IngredientesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientesViewHolder holder, int position) {
        // Asignar el texto del ingrediente a cada item
        String ingredienteItem = ingredientes.get(position);
        holder.ingredienteTextView.setText(ingredienteItem);
    }

    @Override
    public int getItemCount() {
        return ingredientes.size();
    }

    public static class IngredientesViewHolder extends RecyclerView.ViewHolder {
        TextView ingredienteTextView;

        public IngredientesViewHolder(View itemView) {
            super(itemView);
            ingredienteTextView = itemView.findViewById(R.id.textViewRecetaIngredientes);
        }
    }
}
