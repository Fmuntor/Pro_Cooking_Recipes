package com.pcr.procookingrecipes.Adapters.Busqueda;

public class BusquedaDataModel {
    private String title;
    private String image;
    private int servings;
    private int preparationMinutes;

    // Constructor
    public BusquedaDataModel(String title, String image, int servings, int preparationMinutes) {
        this.title = title;
        this.image = image;
        this.servings = servings;
        this.preparationMinutes = preparationMinutes;
    }

    // Getters y setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public int getPreparationMinutes() {
        return preparationMinutes;
    }

    public void setPreparationMinutes(int preparationMinutes) {
        this.preparationMinutes = preparationMinutes;
    }
}

