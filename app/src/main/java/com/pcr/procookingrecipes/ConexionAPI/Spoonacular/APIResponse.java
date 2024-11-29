package com.pcr.procookingrecipes.ConexionAPI.Spoonacular;

import android.content.Context;
import android.util.Log;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.pcr.procookingrecipes.ConexionAPI.SecurePreferences;
import com.pcr.procookingrecipes.InstruccionesReceta.Instruction;
import com.pcr.procookingrecipes.Receta.Receta;
import com.pcr.procookingrecipes.Receta.RecetaBusqueda;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class APIResponse {

    private static final String COMPLEX_SEARCH_URL = "https://api.spoonacular.com/recipes/complexSearch";
    private static final String URL_INFORMACION = "https://api.spoonacular.com/recipes/";

    // Variable para almacenar la API_KEY cargada
    private String API_KEY="83a0e0c5b56948ca83dd4e3ffbaecdf4";

    public APIResponse(Context context) {
        //setApiKey(context);
    }

    public void setApiKey(Context context) {
        API_KEY = SecurePreferences.leerApiKey(context);
    }

    // Método para verificar si el ingrediente es correcto
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
            } else {
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

    // Método para realizar una búsqueda completa de recetas
    public List<Receta> busquedaCompleta(String consulta, int numero) {
        String urlString = COMPLEX_SEARCH_URL + "?query=" + consulta + "&number=" + numero + "&addRecipeInstructions=true&apiKey=" + API_KEY;
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
                return apiResponse.getResults();
            } else {
                return new ArrayList<>();
            }

        } catch (IOException e) {
            Log.e("APIResponse", "Error al conectar con la API: " + e.getMessage());
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return new ArrayList<>();
    }

    // Método para obtener información de una receta
    public RecetaBusqueda getInformacionReceta(int id) {
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

    // Método para obtener las instrucciones de una receta
    public List<Instruction> getInstrucciones(int id) {
        String urlString = URL_INFORMACION + id + "/analyzedInstructions?apiKey=" + API_KEY;
        HttpURLConnection urlConnection = null;
        StringBuilder response = new StringBuilder();
        try {
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

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
            Log.d("APIResponse", "Instrucciones: " + response.toString());

            // Usa TypeToken para especificar el tipo genérico
            Type listType = new TypeToken<List<Instruction>>() {}.getType();
            return gson.fromJson(response.toString(), listType);

        } catch (IOException e) {
            Log.e("APIResponse", "Error al conectar con la API: " + e.getMessage());
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return new ArrayList<>();
    }

    // Clase interna que representa la respuesta de la API de recetas
    private class ApiResponse {
        private List<Receta> results;

        public List<Receta> getResults() {
            return results;
        }
    }
}
