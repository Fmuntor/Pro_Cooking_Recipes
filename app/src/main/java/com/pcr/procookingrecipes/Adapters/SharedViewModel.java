package com.pcr.procookingrecipes.Adapters;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<BusquedaDataModel> newItem = new MutableLiveData<>();

    public void addItem(BusquedaDataModel item) {
        newItem.setValue(item);
    }

    public LiveData<BusquedaDataModel> getNewItem() {
        return newItem;
    }
}
