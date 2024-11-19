package com.pcr.procookingrecipes.InstruccionesReceta;

import java.util.List;

public class Instruccion {
    private String name;
    private List<Paso> steps;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Paso> getSteps() {
        return steps;
    }

    public void setSteps(List<Paso> steps) {
        this.steps = steps;
    }

    public static class Equipo {
        private int id;
        private String image;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class Ingrediente {
        private int id;
        private String image;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class Paso {
        private int number;
        private String step;
        private List<Equipo> equipment;
        private List<Ingrediente> ingredients;

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public String getStep() {
            return step;
        }

        public void setStep(String step) {
            this.step = step;
        }

        public List<Equipo> getEquipment() {
            return equipment;
        }

        public void setEquipment(List<Equipo> equipment) {
            this.equipment = equipment;
        }

        public List<Ingrediente> getIngredients() {
            return ingredients;
        }

        public void setIngredients(List<Equipo> ingrediente) {
            this.ingredients = ingredients;
        }
    }
}
