package com.pcr.procookingrecipes.ui.Fragment.resultados;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ResultadosViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ResultadosViewModel() {
        // Inicializa el MutableLiveData con el texto
        mText = new MutableLiveData<>();
        mText.setValue("Texto del fragmento Resultafos");
    }

    public LiveData<String> getText() {
        return mText;
    }
}