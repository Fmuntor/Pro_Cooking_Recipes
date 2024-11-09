package com.pcr.procookingrecipes.ui.favoritos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.pcr.procookingrecipes.databinding.FragmentoFavoritosBinding;


public class FavoritosFragmento extends Fragment {

    private FragmentoFavoritosBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FavoritosViewModel galleryViewModel =
                new ViewModelProvider(this).get(FavoritosViewModel.class);

        binding = FragmentoFavoritosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textFavoritos;
        galleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}