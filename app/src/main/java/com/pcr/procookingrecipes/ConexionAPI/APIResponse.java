package com.pcr.procookingrecipes.ConexionAPI;

import android.util.Log;
import com.google.gson.Gson;
import com.pcr.procookingrecipes.Receta.Receta;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class APIResponse {
    private static final String API_KEY = "61adb1434eaa4266b233f21cc77d9931";
    private static final String INGREDIENT_SEARCH_URL = "https://api.spoonacular.com/food/ingredients/autocomplete";
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
            return parseRecipeResponse(response);
        } catch (IOException e) {
            Log.e("APIResponse", "Error al conectar con la API: " + e.getMessage());
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null;
    }

    // Método para obtener todos los ingredientes posibles en Spoonacular
    public List<String> getAllIngredients() {
        Set<String> ingredients = new HashSet<>(); // Para evitar duplicados
        String letters = "abcdefghijklmnopqrstuvwxyz";

        for (char letter : letters.toCharArray()) {
            String urlString = INGREDIENT_SEARCH_URL + "?query=" + letter + "&number=100&apiKey=" + API_KEY;
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

                // Convertir la respuesta JSON a una lista de ingredientes
                List<String> letterIngredients = parseIngredientResponse(response);
                ingredients.addAll(letterIngredients); // Agregar sin duplicados

            } catch (IOException e) {
                Log.e("APIResponse", "Error al conectar con la API para la letra " + letter + ": " + e.getMessage());
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
        }
        return new ArrayList<>(ingredients); // Convertir Set a List antes de retornar
    }
    // Método para autocompletar ingredientes
    public List<String> autocompleteIngredient(String query) {
        String urlString = INGREDIENT_SEARCH_URL + "?query=" + query + "&number=10&apiKey=" + API_KEY;
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

            // Parsear la respuesta JSON
            return parseIngredientResponse(response);

        } catch (IOException e) {
            Log.e("APIResponse", "Error al conectar con la API: " + e.getMessage());
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null;
    }

    // Método para convertir la respuesta JSON en una lista de recetas
    private List<Receta> parseRecipeResponse(String response) {
        Gson gson = new Gson();
        ApiResponse apiResponse = gson.fromJson(response, ApiResponse.class);
        return apiResponse.getResults();
    }

    // Método para convertir la respuesta JSON en una lista de ingredientes
    private List<String> parseIngredientResponse(String response) {
        Gson gson = new Gson();
        IngredientResponse[] ingredients = gson.fromJson(response, IngredientResponse[].class);
        List<String> ingredientNames = new ArrayList<>();
        for (IngredientResponse ingredient : ingredients) {
            ingredientNames.add(ingredient.getName());
        }
        return ingredientNames;
    }

    public boolean comprobarIngredientes(List<String> listaIngredientes) {
        return true;
    }

    // Clase interna que representa la respuesta de la API de recetas
    private class ApiResponse {
        private List<Receta> results;
        public List<Receta> getResults() {
            return results;
        }
        public void setResults(List<Receta> results) {
            this.results = results;
        }
    }

    // Clase interna que representa la respuesta de la API de ingredientes
    private class IngredientResponse {
        private String name;
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
    }
}
