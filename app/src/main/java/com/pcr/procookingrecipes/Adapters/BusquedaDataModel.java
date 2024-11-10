package com.pcr.procookingrecipes.Adapters;

public class BusquedaDataModel {
    private String campo1;
    private String campo2;
    private boolean mostrarSpinnerExtra;
    private boolean mostrarSeekBar;

    public BusquedaDataModel(String campo1, String campo2) {
        this.campo1 = campo1;
        this.campo2 = campo2;
        this.mostrarSpinnerExtra = false; // Por defecto muestra el EditText
        this.mostrarSeekBar = false;
    }

    public String getCampo1() {
        return campo1;
    }

    public String getCampo2() {
        return campo2;
    }
    public boolean isMostrarSpinnerExtra() {
        return mostrarSpinnerExtra;
    }

    public void setMostrarSpinnerExtra(boolean mostrarSpinnerExtra) {
        this.mostrarSpinnerExtra = mostrarSpinnerExtra;
    }
    public void setMostrarSeekBar(boolean mostrarSeekBar) {
        this.mostrarSeekBar = mostrarSeekBar;
    }
}