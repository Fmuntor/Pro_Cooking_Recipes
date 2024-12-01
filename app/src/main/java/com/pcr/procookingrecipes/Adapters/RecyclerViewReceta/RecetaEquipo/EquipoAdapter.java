package com.pcr.procookingrecipes.Adapters.RecyclerViewReceta.RecetaEquipo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.pcr.procookingrecipes.R;

import java.util.ArrayList;

public class EquipoAdapter extends RecyclerView.Adapter<EquipoAdapter.EquipoViewHolder> {
    private ArrayList<String> equipo;

    public EquipoAdapter(ArrayList<String> equipo) {
        // Si el equipo es nulo o tiene menos de 2 elementos, lo llenamos con los mismos ítems.
        if (equipo == null) {
            this.equipo = new ArrayList<>();
        } else {
            // Añadir dos elementos iguales al final si solo hay uno
            this.equipo = new ArrayList<>(equipo);
            if (equipo.size() < 2) {
                this.equipo.add(equipo.get(0));  // Duplicamos el primer ítem
            }
        }
    }

    @Override
    public EquipoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflar el layout del item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_receta_equipo, parent, false);
        return new EquipoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EquipoViewHolder holder, int position) {
        // Asignar el texto del equipo a cada item
        String equipoItem = equipo.get(position);
        holder.ingredienteTextView.setText(equipoItem);
    }

    @Override
    public int getItemCount() {
        // Devolver el tamaño de la lista (2 si estamos duplicando elementos)
        return equipo.size();
    }

    public static class EquipoViewHolder extends RecyclerView.ViewHolder {
        TextView ingredienteTextView;

        public EquipoViewHolder(View itemView) {
            super(itemView);
            ingredienteTextView = itemView.findViewById(R.id.textViewRecetaEquipo);
        }
    }
}

