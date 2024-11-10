package com.pcr.procookingrecipes.ConexionAPI;
import android.util.Log;
import com.google.gson.Gson;
import com.pcr.procookingrecipes.Receta.Receta;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class APIResponse {
    private static final String API_KEY = "61adb1434eaa4266b233f21cc77d9931";
    private static final String COMPLEX_SEARCH_URL = "https://api.spoonacular.com/recipes/complexSearch";
    // Método para buscar recetas con "manzana" y limitar a 10 resultados
    public List<Receta> searchAppleRecipes() {
        String urlString = COMPLEX_SEARCH_URL + "?query=apple&number=10&addRecipeInformation=true&apiKey=" + API_KEY;
        HttpURLConnection urlConnection = null;
        String response = "";
        try {
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            // Leer la respuesta
            Scanner scanner = new Scanner(urlConnection.getInputStream());
            while (scanner.hasNext()) {
                response += scanner.nextLine();
            }
            scanner.close();
            // Convertir la respuesta JSON a objetos Java
            return parseResponse(response);
        } catch (IOException e) {
            Log.e("APIResponse", "Error al conectar con la API: " + e.getMessage());
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null;
    }

    /*public List<Receta> buscarPorIngredientes() {

        String urlString = COMPLEX_SEARCH_URL + "?query=apple&number="+num+"&addRecipeInformation=true&apiKey=" + API_KEY;
        HttpURLConnection urlConnection = null;
        String response = "";
        try {
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            // Leer la respuesta
            Scanner scanner = new Scanner(urlConnection.getInputStream());
            while (scanner.hasNext()) {
                response += scanner.nextLine();
            }
            scanner.close();
            // Convertir la respuesta JSON a objetos Java
            return parseResponse(response);
        } catch (IOException e) {
            Log.e("APIResponse", "Error al conectar con la API: " + e.getMessage());
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null;
    }*/
    // Método para convertir la respuesta JSON en una lista de recetas
    private List<Receta> parseResponse(String response) {
        Gson gson = new Gson();
        ApiResponse apiResponse = gson.fromJson(response, ApiResponse.class);
        return apiResponse.getResults();
    }
    // Clase interna que representa la respuesta de la API
    private class ApiResponse {
        private List<Receta> results;
        public List<Receta> getResults() {
            return results;
        }
        public void setResults(List<Receta> results) {
            this.results = results;
        }
    }
}
