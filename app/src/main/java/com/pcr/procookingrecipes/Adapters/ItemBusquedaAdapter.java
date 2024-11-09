package com.pcr.procookingrecipes.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pcr.procookingrecipes.R;

import java.util.List;

public class ItemBusquedaAdapter extends RecyclerView.Adapter<ItemBusquedaAdapter.MyViewHolder>{
    private List<BusquedaDataModel> dataList;

    public ItemBusquedaAdapter(List<BusquedaDataModel> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_busqueda, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        BusquedaDataModel item = dataList.get(position);
        holder.textField1.setText(item.getCampo1());
        holder.textField2.setText(item.getCampo2());
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
        TextView textField1, textField2;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textField1 = itemView.findViewById(R.id.textField1);
            textField2 = itemView.findViewById(R.id.textField2);
        }
    }
}
