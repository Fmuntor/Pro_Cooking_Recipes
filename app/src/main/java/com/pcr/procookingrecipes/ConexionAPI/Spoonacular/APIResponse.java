package com.pcr.procookingrecipes.ConexionAPI.Spoonacular;

import android.util.Log;

import com.google.gson.Gson;
import com.pcr.procookingrecipes.Receta.Receta;
import com.pcr.procookingrecipes.Receta.RecetaBusqueda;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class APIResponse {
    /*dam*/ private static final String API_KEY = "61adb1434eaa4266b233f21cc77d9931";
    //jue*/ private static final String API_KEY = "30ccc31545e94349a94f95e9aa2578f8";
    //xew*/ private static final String API_KEY = "150217e73f7f43698b23de34401341c8";

    private static final String INGREDIENT_SEARCH_URL = "https://api.spoonacular.com/food/ingredients/autocomplete";
    private static final String COMPLEX_SEARCH_URL = "https://api.spoonacular.com/recipes/complexSearch";
    private static final String URL_INFORMACION = "https://api.spoonacular.com/recipes/";

    public String esIngredienteCorrecto(String ingrediente) {
        String urlString = COMPLEX_SEARCH_URL + "?query=" + ingrediente + "&number=1&addRecipeInstructions=false&apiKey=" + API_KEY;
        HttpURLConnection urlConnection = null;
        String response = "";

        try {
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Leer la respuesta de la API
            Scanner scanner = new Scanner(urlConnection.getInputStream());
            while (scanner.hasNext()) {
                response += scanner.nextLine();
            }
            scanner.close();

            Log.d("APIResponse", "Respuesta de la API: " + response);

            // Parsear la respuesta JSON
            Gson gson = new Gson();
            ApiResponse apiResponse = gson.fromJson(response, ApiResponse.class);

            // Verificar si la lista de resultados no está vacía
            if (apiResponse.getResults() != null && !apiResponse.getResults().isEmpty()) {
                return response;
            }else{
                return "Error";
            }

        } catch (IOException e) {
            Log.e("APIResponse", "Error al conectar con la API: " + e.getMessage());
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null;
    }

    public List<Receta> busquedaCompleta(String consulta){
        String urlString = COMPLEX_SEARCH_URL + "?query=" + consulta + "&number=1&addRecipeInstructions=true&apiKey=" + API_KEY;
        HttpURLConnection urlConnection = null;
        String response = "";
        Log.d("APIResponse CONSULTA FINAL", "Consulta final: " + urlString);

        try {
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Leer la respuesta de la API
            Scanner scanner = new Scanner(urlConnection.getInputStream());
            while (scanner.hasNext()) {
                response += scanner.nextLine();
            }
            scanner.close();

            Log.d("APIResponse CONSULTA FINAL", "Respuesta de la API: " + response);

            // Parsear la respuesta JSON
            Gson gson = new Gson();
            ApiResponse apiResponse = gson.fromJson(response, ApiResponse.class);

            // Verificar si la lista de resultados no está vacía y devolver las recetas
            if (apiResponse.getResults() != null && !apiResponse.getResults().isEmpty()) {
                return apiResponse.getResults(); // Retornar la lista de recetas
            } else {
                return new ArrayList<>(); // Retornar una lista vacía si no hay resultados
            }

        } catch (IOException e) {
            Log.e("APIResponse", "Error al conectar con la API: " + e.getMessage());
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return new ArrayList<>(); // Retornar una lista vacía si hubo un error
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

    public RecetaBusqueda leerDeID(int id) {
        String urlString = URL_INFORMACION + id + "/information?includeNutrition=false&apiKey=" + API_KEY;
        HttpURLConnection urlConnection = null;
        StringBuilder response = new StringBuilder();
        try {
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Leer la respuesta
            int statusCode = urlConnection.getResponseCode();
            if (statusCode == HttpURLConnection.HTTP_OK) {
                Scanner scanner = new Scanner(urlConnection.getInputStream(), "UTF-8");
                while (scanner.hasNext()) {
                    response.append(scanner.nextLine());
                }
                scanner.close();
            } else {
                Log.e("APIResponse", "Error en la conexión. Código de respuesta: " + statusCode);
            }

            Gson gson = new Gson();
            return gson.fromJson(response.toString(), RecetaBusqueda.class);

        } catch (IOException e) {
            Log.e("APIResponse", "Error al conectar con la API: " + e.getMessage());
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null;
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
