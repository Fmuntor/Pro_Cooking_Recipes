package com.pcr.procookingrecipes.ui.favoritos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FavoritosViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public FavoritosViewModel() {
        // Inicializa el MutableLiveData con el texto
        mText = new MutableLiveData<>();
        mText.setValue("Texto del fragmento Favoritos");
    }

    public LiveData<String> getText() {
        return mText;
    }
}