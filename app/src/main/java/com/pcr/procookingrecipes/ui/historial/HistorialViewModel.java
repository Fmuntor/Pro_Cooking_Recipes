package com.pcr.procookingrecipes.ui.historial;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HistorialViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public HistorialViewModel() {
        // Inicializa el MutableLiveData con el texto
        mText = new MutableLiveData<>();
        mText.setValue("Texto del fragmento Historial");
    }

    public LiveData<String> getText() {
        return mText;
    }
}

