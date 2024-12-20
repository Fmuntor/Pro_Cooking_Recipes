package com.pcr.procookingrecipes.ui.Fragment.resultados;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.pcr.procookingrecipes.databinding.FragmentoResultadosBinding;

public class ResultadosFragmento extends Fragment {

    private FragmentoResultadosBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ResultadosViewModel homeViewModel =
                new ViewModelProvider(this).get(ResultadosViewModel.class);

        // Inflar el layout
        binding = FragmentoResultadosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Observar el LiveData del ViewModel y actualizar la UI
        final TextView textView = binding.textResultados;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}