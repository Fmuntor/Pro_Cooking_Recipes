package com.pcr.procookingrecipes.ui.historial;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.pcr.procookingrecipes.databinding.FragmentoHistorialBinding;

public class HistorialFragmento extends Fragment {

    private FragmentoHistorialBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HistorialViewModel slideshowViewModel =
                new ViewModelProvider(this).get(HistorialViewModel.class);

        binding = FragmentoHistorialBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}