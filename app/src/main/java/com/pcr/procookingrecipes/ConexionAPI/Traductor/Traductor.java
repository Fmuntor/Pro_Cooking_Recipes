package com.pcr.procookingrecipes.ConexionAPI.Traductor;

import android.util.Log;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

import java.util.Objects;

// Clase para traducir texto
public class Traductor {
    // Método estático para traducir texto
    public static String traducir(String texto, String idioma) {
        // Crear una instancia de Translate
        Translate translate = TranslateOptions.newBuilder().setApiKey("AIzaSyAlF4NerB2lB0-SWaSSwnjzO7XEB8nSVCw").build().getService();

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
