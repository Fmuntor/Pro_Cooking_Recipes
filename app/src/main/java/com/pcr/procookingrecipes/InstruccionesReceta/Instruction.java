package com.pcr.procookingrecipes.InstruccionesReceta;

import java.util.List;

public class Instruction {
    private String name;
    private List<Step> steps;

    // Getters y setters para el nombre y la lista de pasos
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<Step> getSteps() { return steps; }
}
