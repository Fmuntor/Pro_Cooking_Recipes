package com.pcr.procookingrecipes.Adapters.RecyclerViewHistorial;

import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
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
import com.pcr.procookingrecipes.ui.Activity.BusquedaActivity;
import com.pcr.procookingrecipes.ConexionAPI.SecurePreferences;
import com.pcr.procookingrecipes.ConexionAPI.Spoonacular.Spoonacular_API_Response;
import com.pcr.procookingrecipes.R;
import com.pcr.procookingrecipes.Receta.RecetaBusqueda;
import com.pcr.procookingrecipes.Receta.RecetaHistorial;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ItemHistorialAdapter extends RecyclerView.Adapter<ItemHistorialAdapter.MyViewHolder> {
    private List<RecetaHistorial> dataList;
    private final Executor executor = Executors.newSingleThreadExecutor();

    // Constructor
    public ItemHistorialAdapter(List<RecetaHistorial> dataList) {
        // Si dataList es null, inicializar con una lista vacía
        this.dataList = (dataList != null) ? dataList : new ArrayList<>();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflar la vista del item
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
            if(item.getRecetas().size() == 1){
                holder.TextViewNumBusquedas.setText("Hay " + item.getRecetas().size() + " receta.");
            }else{
                holder.TextViewNumBusquedas.setText("Hay " + item.getRecetas().size() + " recetas.");
            }
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

                // Obtener el email del usuario
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

            holder.botonHistorialVer.setOnClickListener(v -> {
                // Pasar de listaIDS a objetos tipo RecetaBusqueda
                List<RecetaBusqueda> recetasBusqueda = new ArrayList<>();

                SecurePreferences.cargarClavesDesdeArchivo(v.getContext());
                Spoonacular_API_Response apiResponse = new Spoonacular_API_Response(v.getContext());

                // Ejecutar en un hilo separado para evitar bloquear la UI
                executor.execute(() -> {
                    // Obtener los objetos RecetaBusqueda de la API
                    for (int i = 0; i < item.getRecetas().size(); i++) {
                        // Obtener el objeto RecetaBusqueda de la API y agregarlo a la lista
                        recetasBusqueda.add(apiResponse.getInformacionReceta(Integer.parseInt(item.getRecetas().get(i))));
                    }

                    // Actualizar la UI en el hilo principal
                    Intent intent = new Intent(v.getContext(), BusquedaActivity.class);
                    Log.d("historial", recetasBusqueda.toString());
                    intent.putParcelableArrayListExtra("recetasCompletas", (ArrayList<? extends Parcelable>) recetasBusqueda);
                    holder.itemView.getContext().startActivity(intent);
                });
            });
        }
    }

    private String getParametrosTexto(RecetaHistorial item) {
        // Construir el texto de los parámetros
        StringBuilder parametrosTexto = new StringBuilder();
        // Verificar si hay ingredientes
        if (!item.getParametros().get(7).isEmpty()) {
            parametrosTexto.append("Ingredientes: ").append(item.getParametros().get(7)).append(".");
        }else{
            parametrosTexto.append("No se han seleccionado ingredientes.");
        }
        return parametrosTexto.toString();
    }

    @Override
    public int getItemCount() {
        // Verificar si dataList es null antes de acceder a su tamaño
        return (dataList != null) ? dataList.size() : 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // Declarar las vistas
        public Button botonHistorialVer, botonHistorialBorrar;
        TextView TextViewFecha, TextViewNumBusquedas, TextViewParametros;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            // Inicializar las vistas
            TextViewFecha = itemView.findViewById(R.id.fechaTextView);
            TextViewNumBusquedas = itemView.findViewById(R.id.numBusquedasTextView);
            TextViewParametros = itemView.findViewById(R.id.parametrosTextView);
            botonHistorialVer = itemView.findViewById(R.id.botonHistorialVer);
            botonHistorialBorrar = itemView.findViewById(R.id.botonHistorialBorrar);
        }
    }
}
