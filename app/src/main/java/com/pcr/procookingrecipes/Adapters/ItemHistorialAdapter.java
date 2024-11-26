package com.pcr.procookingrecipes.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pcr.procookingrecipes.R;
import com.pcr.procookingrecipes.Receta.RecetaHistorial;

import java.util.ArrayList;
import java.util.List;

public class ItemHistorialAdapter extends RecyclerView.Adapter<ItemHistorialAdapter.MyViewHolder> {
    private List<RecetaHistorial> dataList;

    // Constructor
    public ItemHistorialAdapter(List<RecetaHistorial> dataList) {
        // Si dataList es null, inicializar con una lista vacía
        this.dataList = (dataList != null) ? dataList : new ArrayList<>();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_historial, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // Asegurarse de que dataList no sea null y tenga elementos
        if (dataList != null && position < dataList.size()) {
            RecetaHistorial item = dataList.get(position);

            // Asignar los datos al ViewHolder
            holder.TextViewFecha.setText(item.getFecha());
            holder.TextViewNumBusquedas.setText("Hay " + item.getRecetas().size() + " recetas.");
            // Mostrar parámetros en el TextView
            holder.TextViewParametros.setText(getParametrosTexto(item));

            // Configurar el botón Ver para abrir los detalles
            holder.botonHistorialVer.setOnClickListener(v -> {
                // Implementar la acción del botón para ver detalles si es necesario
            });

            // Configurar el botón Borrar
            holder.botonHistorialBorrar.setOnClickListener(v -> {
                // Eliminar el item de la lista local
                dataList.remove(position);

                // Eliminar el item de Firebase usando la clave 'pushKey'
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference referenciaHistorial = database.getReference("historial");

                String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                if (email.contains(".")) {
                    email = email.replace(".", "·");
                }

                // Obtener la clave 'pushKey' desde el item
                String pushKey = item.getPushKey();

                // Referencia al nodo del usuario
                DatabaseReference usuarioHistorial = referenciaHistorial.child(email + " - " + FirebaseAuth.getInstance().getUid());

                // Eliminar el item usando la clave 'pushKey'
                usuarioHistorial.child(pushKey).removeValue()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                // Actualizar la lista local
                                notifyItemRemoved(position);
                                Toast.makeText(v.getContext(), "Elemento eliminado correctamente.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(v.getContext(), "Error al eliminar el elemento.", Toast.LENGTH_SHORT).show();
                            }
                        });
            });
        }
    }

    private String getParametrosTexto(RecetaHistorial item) {
        StringBuilder parametrosTexto = new StringBuilder();
        if (!item.getParametros().get(0).equals("")) {
            parametrosTexto.append("Tipo de cocina: ").append(item.getParametros().get(0)).append(".");
        }
        if (!item.getParametros().get(1).equals("")) {
            parametrosTexto.append(" Nacionalidad: ").append(item.getParametros().get(1)).append(".");
        }
        if (!item.getParametros().get(2).equals("")) {
            parametrosTexto.append(" Tipo de dieta: ").append(item.getParametros().get(2)).append(".");
        }
        if (!item.getParametros().get(3).equals("")) {
            parametrosTexto.append(" Intolerancias: ").append(item.getParametros().get(3)).append(".");
        }
        if (!item.getParametros().get(4).equals("")) {
            parametrosTexto.append(" Carbohidratos: ").append(item.getParametros().get(4)).append("g/plato.");
        }
        if (!item.getParametros().get(5).equals("")) {
            parametrosTexto.append(" Proteínas: ").append(item.getParametros().get(5)).append("g/plato.");
        }
        if (!item.getParametros().get(6).equals("")) {
            parametrosTexto.append(" Calorías: ").append(item.getParametros().get(6)).append("kcal/plato.");
        }
        if (!item.getParametros().get(7).equals("")) {
            parametrosTexto.append(" Ingredientes: ").append(item.getParametros().get(7)).append(".");
        }
        return parametrosTexto.toString();
    }

    @Override
    public int getItemCount() {
        // Verificar si dataList es null antes de acceder a su tamaño
        return (dataList != null) ? dataList.size() : 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public Button botonHistorialVer, botonHistorialBorrar;
        TextView TextViewFecha, TextViewNumBusquedas, TextViewParametros;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            TextViewFecha = itemView.findViewById(R.id.fechaTextView);
            TextViewNumBusquedas = itemView.findViewById(R.id.numBusquedasTextView);
            TextViewParametros = itemView.findViewById(R.id.parametrosTextView);
            botonHistorialVer = itemView.findViewById(R.id.botonHistorialVer);
            botonHistorialBorrar = itemView.findViewById(R.id.botonHistorialBorrar);
        }
    }
}