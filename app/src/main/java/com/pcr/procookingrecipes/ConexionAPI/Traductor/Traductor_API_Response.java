package com.pcr.procookingrecipes.ConexionAPI.Traductor;

import android.content.Context;
import android.util.Log;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import com.pcr.procookingrecipes.ConexionAPI.SecurePreferences;

import java.util.Objects;

// Clase para traducir texto
public class Traductor_API_Response {
    // Método estático para traducir texto
    public static String traducir(Context context, String texto, String idioma) {
        // Crear una instancia de Translate
        SecurePreferences.cargarClavesDesdeArchivo(context);
        Translate translate = TranslateOptions.newBuilder().setApiKey(SecurePreferences.leerApiKey(context, "API_KEY_TRADUCTOR")).build().getService();

        Translation translation;
        // Traducir el texto según el idioma especificado
        if(Objects.equals(idioma, "espanol")){
            translation = translate.translate(texto, Translate.TranslateOption.sourceLanguage("en"), Translate.TranslateOption.targetLanguage("es"));
        }else{
            translation = translate.translate(texto, Translate.TranslateOption.sourceLanguage("es"), Translate.TranslateOption.targetLanguage("en"));
        }
        // Obtener el texto traducido
        String translatedText = translation.getTranslatedText();
        Log.d("Translation", "Texto traducido: " + translatedText);
        return translatedText;
    }
}
