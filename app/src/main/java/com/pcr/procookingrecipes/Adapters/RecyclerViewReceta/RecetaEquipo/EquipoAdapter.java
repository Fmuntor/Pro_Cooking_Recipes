package com.pcr.procookingrecipes.Adapters.RecyclerViewReceta.RecetaEquipo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.pcr.procookingrecipes.R;

public class EquipoAdapter extends RecyclerView.Adapter<EquipoAdapter.EquipoViewHolder> {
    private String[] equipo;

    public EquipoAdapter(String[] equipo) {
        this.equipo = equipo;
    }

    @Override
    public EquipoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_receta_equipo, parent, false);
        return new EquipoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EquipoViewHolder holder, int position) {
        holder.ingredienteTextView.setText(equipo[position]);
    }

    @Override
    public int getItemCount() {
        return equipo.length;
    }

    public void addItem(String equipoInsertar) {

        // Crear una nueva lista con un elemento extra
        String[] nuevaLista = new String[equipo.length + 1];
        System.arraycopy(equipo, 0, nuevaLista, 0, equipo.length);
        nuevaLista[equipo.length] = equipoInsertar;  // Agregar el nuevo ingrediente

        // Actualizar la lista de equipo
        equipo = nuevaLista;

        // Notificar que el item fue insertado
        notifyItemInserted(equipo.length - 1);
        
    }

    public static class EquipoViewHolder extends RecyclerView.ViewHolder {
        TextView ingredienteTextView;

        public EquipoViewHolder(View itemView) {
            super(itemView);
            ingredienteTextView = itemView.findViewById(R.id.textViewRecetaEquipo);
        }
    }
}

