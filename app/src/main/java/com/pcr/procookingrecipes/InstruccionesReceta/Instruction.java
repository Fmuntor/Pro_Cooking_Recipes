package com.pcr.procookingrecipes.InstruccionesReceta;

import java.util.List;

public class Instruction {
    private String name;
    private List<Step> steps;

    // Getters y setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<Step> getSteps() { return steps; }
    public void setSteps(List<Step> steps) { this.steps = steps; }
}
