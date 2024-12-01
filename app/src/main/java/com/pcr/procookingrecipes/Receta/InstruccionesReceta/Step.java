package com.pcr.procookingrecipes.Receta.InstruccionesReceta;

import java.util.List;

public class Step {
    private int number;
    private String step;
    private List<Ingredient> ingredients;
    private List<Equipment> equipment;

    // Getters del numero, paso, ingredientes y equipo
    public int getNumber() { return number; }

    public String getStep() { return step; }

    public List<Ingredient> getIngredients() { return ingredients; }

    public List<Equipment> getEquipment() { return equipment; }
}