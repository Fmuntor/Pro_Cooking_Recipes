package com.pcr.procookingrecipes.ui.Fragment.busqueda;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BusquedaFragmentoViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public BusquedaFragmentoViewModel() {
        // Inicializa el MutableLiveData con el texto
        mText = new MutableLiveData<>();
        mText.setValue("Texto del fragmento Busqueda");
    }

    public LiveData<String> getText() {
        return mText;
    }
}