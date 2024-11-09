package com.pcr.procookingrecipes.ui.resultados;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ResultadosViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ResultadosViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Texto del fragmento Resultafos");
    }

    public LiveData<String> getText() {
        return mText;
    }
}