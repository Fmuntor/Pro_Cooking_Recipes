package com.pcr.procookingrecipes.Adapters.RecyclerViewReceta.RecetaInstrucciones;

import static com.pcr.procookingrecipes.ConexionAPI.Traductor.Traductor.traducir;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.pcr.procookingrecipes.R;

import java.util.ArrayList;

public class InstruccionesAdapter extends RecyclerView.Adapter<InstruccionesAdapter.InstruccionesViewHolder> {
    private ArrayList<String> instrucciones;

    public InstruccionesAdapter(ArrayList<String> instrucciones) {
        // Si instrucciones es nulo o tiene menos de 2 elementos, lo llenamos con los mismos ítems.
        if (instrucciones == null) {
            this.instrucciones = new ArrayList<>();
        } else {
            // Añadir dos elementos iguales al final si solo hay uno
            this.instrucciones = new ArrayList<>(instrucciones);
            if (instrucciones.size() < 2) {
                this.instrucciones.add(instrucciones.get(0));
                // Duplicamos el primer ítem
            }
        }
    }

    @Override
    public InstruccionesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_receta_instrucciones, parent, false);

        return new InstruccionesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(InstruccionesViewHolder holder, int position) {
        String instruccionesItem = instrucciones.get(position);
        holder.ingredienteTextView.setText(instruccionesItem);
    }

    @Override
    public int getItemCount() {
        return instrucciones.size();
    }

    public static class InstruccionesViewHolder extends RecyclerView.ViewHolder {
        TextView ingredienteTextView;

        public InstruccionesViewHolder(View itemView) {
            super(itemView);
            ingredienteTextView = itemView.findViewById(R.id.textViewRecetaInstrucciones);
        }
    }
}

