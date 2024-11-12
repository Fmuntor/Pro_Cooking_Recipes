package com.pcr.procookingrecipes.ConexionAPI;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class APIAutocompletado {
    private static final String API_KEY = "61adb1434eaa4266b233f21cc77d9931";  // Reemplaza con tu clave de API

    public List<String> obtenerIngredientesSugeridos(String query) {
        List<String> ingredientes = new ArrayList<>();

        try {
            // Construir la URL para la API de autocompletar
            String url = "https://api.spoonacular.com/food/ingredients/autocomplete?query=" + query + "&number=10&apiKey=" + API_KEY;

            // Hacer la solicitud GET a la API
            URL apiUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            // Parsear la respuesta JSON
            JSONArray jsonArray = new JSONArray(response.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String ingredientName = jsonObject.getString("name");
                ingredientes.add(ingredientName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ingredientes;
    }
}
