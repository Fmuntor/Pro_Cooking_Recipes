package com.pcr.procookingrecipes.ui.cuenta;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.pcr.procookingrecipes.databinding.FragmentoCuentaBinding;

public class CuentaFragment extends Fragment {

    private FragmentoCuentaBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CuentaViewModel slideshowViewModel =
                new ViewModelProvider(this).get(CuentaViewModel.class);

        binding = FragmentoCuentaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textCuenta;
        slideshowViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}