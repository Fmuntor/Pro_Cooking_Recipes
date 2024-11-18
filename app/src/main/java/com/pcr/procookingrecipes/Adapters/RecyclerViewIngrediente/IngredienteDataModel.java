package com.pcr.procookingrecipes.Adapters.RecyclerViewIngrediente;

public class IngredienteDataModel {
    private String editText, mensajeError;
    private boolean hasError; // Indica si hay error

    public IngredienteDataModel(String editText) {

        this.editText = editText;

    }

    public String getEditText() {
        return editText;
    }

    public void setEditText(String editText) {
        this.editText = editText;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }

    public boolean hasError() {
        return hasError;
    }

    public void setError(boolean hasError) {
        this.hasError = hasError;;
    }

}