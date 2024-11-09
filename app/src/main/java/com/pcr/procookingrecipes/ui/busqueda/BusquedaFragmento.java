package com.pcr.procookingrecipes.ui.busqueda;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
    private FloatingActionButton fab;
    private Button borrar;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        BusquedaViewModel busquedaViewModel =
                new ViewModelProvider(this).get(BusquedaViewModel.class);

        binding = FragmentoBusquedaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        inicializarRecyclerView(root);
        itemList = new ArrayList<>();
        itemList.add(new BusquedaDataModel("Elemento 1", "Descripción 1"));

        adapter = new ItemBusquedaAdapter(itemList);
        binding.recyclerView.setAdapter(adapter);
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

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fab = requireActivity().findViewById(R.id.botonFlotante1);
        fab.setImageResource(R.drawable.ic_plus);

        fab.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Añadir nuevo fav", Toast.LENGTH_SHORT).show();
            itemList.add(new BusquedaDataModel("Elemento 1", "Descripción 1"));
            adapter.notifyDataSetChanged();

        });
    }

}
