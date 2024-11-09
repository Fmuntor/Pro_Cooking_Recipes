package com.pcr.procookingrecipes.ui.cuenta;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CuentaViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public CuentaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Texto del fragmento Cuenta");
    }

    public LiveData<String> getText() {
        return mText;
    }
}