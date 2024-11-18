package com.pcr.procookingrecipes.ui.busqueda;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BusquedaFragmentoViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public BusquedaFragmentoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Texto del fragmento Busqueda");
    }

    public LiveData<String> getText() {
        return mText;
    }
}