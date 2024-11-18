package com.pcr.procookingrecipes.Adapters.Ingrediente;

public class IngredienteDataModel {
    private String editText;
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
    public boolean hasError() {
        return hasError;
    }

    public void setError(boolean hasError) {
        this.hasError = hasError;
    }

}