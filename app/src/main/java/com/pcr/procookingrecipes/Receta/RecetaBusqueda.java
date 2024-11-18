package com.pcr.procookingrecipes.Receta;

import java.util.List;

public class RecetaBusqueda {
    private int id;
    private String title;
    private String image;
    private String imageType;
    private int servings;
    private int readyInMinutes;
    private int cookingMinutes;
    private int preparationMinutes;
    private String license;
    private String sourceName;
    private String sourceUrl;
    private String spoonacularSourceUrl;
    private double healthScore;
    private double spoonacularScore;
    private double pricePerServing;
    private boolean cheap;
    private String creditsText;
    private List<String> cuisines;
    private boolean dairyFree;
    private boolean glutenFree;
    private boolean vegan;
    private boolean vegetarian;
    private boolean veryHealthy;
    private boolean veryPopular;

    // Getters y setters para los campos de la receta


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public int getReadyInMinutes() {
        return readyInMinutes;
    }

    public void setReadyInMinutes(int readyInMinutes) {
        this.readyInMinutes = readyInMinutes;
    }
}

