package com.pcr.procookingrecipes.Adapters.RecyclerViewReceta.RecetaInstrucciones;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.pcr.procookingrecipes.R;

public class InstruccionesAdapter extends RecyclerView.Adapter<InstruccionesAdapter.InstruccionesViewHolder> {
    private String[] instrucciones;

    public InstruccionesAdapter(String[] instrucciones) {
        this.instrucciones = instrucciones;
    }

    @Override
    public InstruccionesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_receta_instrucciones, parent, false);
        return new InstruccionesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(InstruccionesViewHolder holder, int position) {
        holder.ingredienteTextView.setText(instrucciones[position]);
    }

    @Override
    public int getItemCount() {
        return instrucciones.length;
    }

    public void addItem(String instruccionesInsertar) {
        // Crear una nueva lista con un elemento extra
        String[] nuevaLista = new String[instrucciones.length + 1];
        System.arraycopy(instrucciones, 0, nuevaLista, 0, instrucciones.length);
        nuevaLista[instrucciones.length] = instruccionesInsertar;  // Agregar el nuevo ingrediente

        // Actualizar la lista de equipo
        instrucciones = nuevaLista;

        // Notificar que el item fue insertado
        notifyItemInserted(instrucciones.length - 1);
    }

    public static class InstruccionesViewHolder extends RecyclerView.ViewHolder {
        TextView ingredienteTextView;

        public InstruccionesViewHolder(View itemView) {
            super(itemView);
            ingredienteTextView = itemView.findViewById(R.id.textViewRecetaInstrucciones);
        }
    }
}

