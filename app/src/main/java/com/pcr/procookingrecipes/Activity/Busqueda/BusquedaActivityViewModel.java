package com.pcr.procookingrecipes.Activity.Busqueda;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BusquedaActivityViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public BusquedaActivityViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Texto del fragmento Busqueda");
    }

    public LiveData<String> getText() {
        return mText;
    }
}