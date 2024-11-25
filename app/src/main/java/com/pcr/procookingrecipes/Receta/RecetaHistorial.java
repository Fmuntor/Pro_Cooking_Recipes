package com.pcr.procookingrecipes.Receta;

import java.util.List;

public class RecetaHistorial {
    private String fecha;
    private List<String> recetas;
    private List<String> parametros;
    private String pushKey;  // Campo añadido para almacenar la clave generada por push()

    // Constructor
    public RecetaHistorial(String fecha, List<String> recetas, List<String> parametros, String pushKey) {
        this.fecha = fecha;
        this.recetas = recetas;
        this.parametros = parametros;
        this.pushKey = pushKey;
    }

    public RecetaHistorial() {

    }

    // Getters y setters
    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public List<String> getRecetas() {
        return recetas;
    }

    public void setRecetas(List<String> recetas) {
        this.recetas = recetas;
    }

    public List<String> getParametros() {
        return parametros;
    }

    public void setParametros(List<String> parametros) {
        this.parametros = parametros;
    }

    public String getPushKey() {
        return pushKey;
    }

    public void setPushKey(String pushKey) {
        this.pushKey = pushKey;
    }
}
