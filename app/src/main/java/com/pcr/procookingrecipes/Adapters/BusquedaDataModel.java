package com.pcr.procookingrecipes.Adapters;

public class BusquedaDataModel {
    private String campo1;
    private String campo2;

    public BusquedaDataModel(String campo1, String campo2) {
        this.campo1 = campo1;
        this.campo2 = campo2;
    }

    public String getCampo1() {
        return campo1;
    }

    public String getCampo2() {
        return campo2;
    }
}