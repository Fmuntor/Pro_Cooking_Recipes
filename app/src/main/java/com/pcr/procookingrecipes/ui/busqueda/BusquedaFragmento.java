package com.pcr.procookingrecipes.ui.busqueda;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pcr.procookingrecipes.Adapters.BusquedaDataModel;
import com.pcr.procookingrecipes.Adapters.ItemBusquedaAdapter;
import com.pcr.procookingrecipes.R;
import com.pcr.procookingrecipes.databinding.FragmentoBusquedaBinding;
import java.util.ArrayList;
import java.util.List;


public class BusquedaFragmento extends Fragment {

    private FragmentoBusquedaBinding binding;
    private RecyclerView recyclerView;
    private ItemBusquedaAdapter adapter;
    private List<BusquedaDataModel> itemList;
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        BusquedaViewModel busquedaViewModel =
                new ViewModelProvider(this).get(BusquedaViewModel.class);

        binding = FragmentoBusquedaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        inicializarRecyclerView(root);
        // Crear la lista de elementos
        itemList = new ArrayList<>();

        // Agregar elementos a la lista
        itemList.add(new BusquedaDataModel("Elemento 1", "Descripción 1"));
        itemList.add(new BusquedaDataModel("Elemento 2", "Descripción 2"));
        itemList.add(new BusquedaDataModel("Elemento 1", "Descripción 1"));
        itemList.add(new BusquedaDataModel("Elemento 2", "Descripción 2"));
        itemList.add(new BusquedaDataModel("Elemento 1", "Descripción 1"));
        itemList.add(new BusquedaDataModel("Elemento 2", "Descripción 2"));
        itemList.add(new BusquedaDataModel("Elemento 1", "Descripción 1"));
        itemList.add(new BusquedaDataModel("Elemento 2", "Descripción 2"));
        itemList.add(new BusquedaDataModel("Elemento 1", "Descripción 1"));
        itemList.add(new BusquedaDataModel("Elemento 2", "Descripción 2"));
        itemList.add(new BusquedaDataModel("Elemento 1", "Descripción 1"));
        itemList.add(new BusquedaDataModel("Elemento 2", "Descripción 2"));
        itemList.add(new BusquedaDataModel("Elemento 1", "Descripción 1"));
        itemList.add(new BusquedaDataModel("Elemento 2", "Descripción 2"));itemList.add(new BusquedaDataModel("Elemento 1", "Descripción 1"));
        itemList.add(new BusquedaDataModel("Elemento 2", "Descripción 2"));itemList.add(new BusquedaDataModel("Elemento 1", "Descripción 1"));
        itemList.add(new BusquedaDataModel("Elemento 2", "Descripción 2"));itemList.add(new BusquedaDataModel("Elemento 1", "Descripción 1"));
        itemList.add(new BusquedaDataModel("Elemento 2", "Descripción 2"));itemList.add(new BusquedaDataModel("Elemento 1", "Descripción 1"));
        itemList.add(new BusquedaDataModel("Elemento 2", "Descripción 2"));itemList.add(new BusquedaDataModel("Elemento 1", "Descripción 1"));
        itemList.add(new BusquedaDataModel("Elemento 2", "Descripción 2"));itemList.add(new BusquedaDataModel("Elemento 1", "Descripción 1"));
        itemList.add(new BusquedaDataModel("Elemento 2", "Descripción 2"));itemList.add(new BusquedaDataModel("Elemento 1", "Descripción 1"));
        itemList.add(new BusquedaDataModel("Elemento 2", "Descripción 2"));








        // Inicializar el adaptador
        adapter = new ItemBusquedaAdapter(itemList);
        binding.recyclerView.setAdapter(adapter);

        // Aquí puedes añadir elementos al RecyclerView
        // Ejemplo: itemList.add(new BusquedaDataModel("Campo1", "Campo2"));
        adapter.notifyDataSetChanged();

        final TextView textView = binding.textBusqueda;
        busquedaViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    private void inicializarRecyclerView(View root) {
        recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}