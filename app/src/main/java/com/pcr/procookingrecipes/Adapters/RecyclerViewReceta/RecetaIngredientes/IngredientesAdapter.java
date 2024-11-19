package com.pcr.procookingrecipes.Adapters.RecyclerViewReceta.RecetaIngredientes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.pcr.procookingrecipes.R;

public class IngredientesAdapter extends RecyclerView.Adapter<IngredientesAdapter.IngredientesViewHolder> {
    private String[] ingredientes;

    public IngredientesAdapter(String[] ingredientes) {
        this.ingredientes = ingredientes;
    }

    @Override
    public IngredientesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_receta_ingredientes, parent, false);
        return new IngredientesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientesViewHolder holder, int position) {
        holder.ingredienteTextView.setText(ingredientes[position]);
    }

    @Override
    public int getItemCount() {
        return ingredientes.length;
    }

    public void addItem(String ingredientesInsertar) {
        // Crear una nueva lista con un elemento extra
        String[] nuevaLista = new String[ingredientes.length + 1];
        System.arraycopy(ingredientes, 0, nuevaLista, 0, ingredientes.length);
        nuevaLista[ingredientes.length] = ingredientesInsertar;  // Agregar el nuevo ingrediente

        // Actualizar la lista de ingredientes
        ingredientes = nuevaLista;

        // Notificar que el item fue insertado
        notifyItemInserted(ingredientes.length - 1);
    }

    public static class IngredientesViewHolder extends RecyclerView.ViewHolder {
        TextView ingredienteTextView;

        public IngredientesViewHolder(View itemView) {
            super(itemView);
            ingredienteTextView = itemView.findViewById(R.id.textViewRecetaIngredientes);
        }
    }
}
