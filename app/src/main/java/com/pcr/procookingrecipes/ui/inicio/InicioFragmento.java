package com.pcr.procookingrecipes.ui.inicio;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pcr.procookingrecipes.Adapters.BusquedaDataModel;
import com.pcr.procookingrecipes.Adapters.ItemBusquedaAdapter;
import com.pcr.procookingrecipes.Adapters.SharedViewModel;
import com.pcr.procookingrecipes.databinding.FragmentoInicioBinding;

import java.util.ArrayList;
import java.util.List;

public class InicioFragmento extends Fragment {

    private FragmentoInicioBinding binding;
    private RecyclerView recyclerView;
    private ItemBusquedaAdapter adapter;
    private List<BusquedaDataModel> dataList = new ArrayList<>();
    private SharedViewModel sharedViewModel;

    // Interfaz para comunicar con MainActivity
    public interface OnNewItemListener {
        void onAddNewItem();
    }

    private OnNewItemListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // Asegurarse de que MainActivity implemente la interfaz
        if (context instanceof OnNewItemListener) {
            listener = (OnNewItemListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnNewItemListener");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentoInicioBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Configuración del RecyclerView
        recyclerView = binding.recyclerView; // Asegúrate de tener un RecyclerView en el layout "fragmento_inicio.xml"


        // Inicializar el adaptador y asignarlo al RecyclerView
        adapter = new ItemBusquedaAdapter(dataList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        sharedViewModel.getNewItem().observe(getViewLifecycleOwner(), newItem -> {
            if (newItem != null) {
                adapter.addItem(newItem);
                adapter.notifyDataSetChanged();
            }
        });
        return root;
    }

    // Método para agregar un nuevo elemento al RecyclerView
    public void addNewItemToRecycler(String campo1, String campo2) {
        BusquedaDataModel newItem = new BusquedaDataModel(campo1, campo2);
        dataList.add(newItem);
        adapter.notifyItemInserted(dataList.size() - 1);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
