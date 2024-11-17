package com.pcr.procookingrecipes.ConexionAPI.Traduccion;

import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
public class APITraduccion {
    private static final String API_KEY = "AIzaSyAlF4NerB2lB0-SWaSSwnjzO7XEB8nSVCw";
    private static final String TRANSLATION_URL = "https://translation.googleapis.com/language/translate/v2";

    // Método para traducir texto
    public static void translate(String text, TranslationCallback callback) {
        OkHttpClient client = new OkHttpClient();

        // Crear el cuerpo de la solicitud
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("q", text);
            jsonBody.put("source", "es");
            jsonBody.put("target", "en");
            jsonBody.put("format", "text");
            //jsonBody.put("key", API_KEY);

            Log.d("APITraduccion", "Cuerpo de la solicitud: " + jsonBody.toString());


        } catch (Exception e) {
            callback.onError(e);
            return;
        }

        RequestBody body = RequestBody.create(
                jsonBody.toString(),
                MediaType.parse("application/json; charset=utf-8")
        );

        Request request = new Request.Builder()
                .url(TRANSLATION_URL)
                .post(body)
                .build();

        Log.d("APITraduccion", "Solicitud enviada a la API.");

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("APITraduccion", "Error en la solicitud", e);
                callback.onError(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response.body().string());
                        String translatedText = jsonResponse
                                .getJSONObject("data")
                                .getJSONArray("translations")
                                .getJSONObject(0)
                                .getString("translatedText");

                        Log.d("APITraduccion", "Texto traducido: " + translatedText);
                        callback.onSuccess(translatedText);
                    } catch (Exception e) {
                        Log.e("APITraduccion", "Error al parsear la respuesta", e);
                        callback.onError(e);
                    }
                } else {
                    Log.e("APITraduccion", "Error en la respuesta: " + response.code());
                    callback.onError(new IOException("Error en la respuesta: " + response.code()));
                }
            }
        });
    }


    // Interfaz para manejar los resultados
    public interface TranslationCallback {
        void onSuccess(String translatedText);
        void onError(Exception e);
    }
}
