package com.pcr.procookingrecipes.ConexionAPI.Traductor;

import android.util.Log;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

import java.util.Objects;

public class Traductor {
    public static String traducir(String texto, String idioma) {
        Translate translate = TranslateOptions.newBuilder().setApiKey("AIzaSyAlF4NerB2lB0-SWaSSwnjzO7XEB8nSVCw").build().getService();
        Translation translation;
        if(Objects.equals(idioma, "espanol")){
            translation = translate.translate(texto, Translate.TranslateOption.sourceLanguage("en"), Translate.TranslateOption.targetLanguage("es"));
        }else{
            translation = translate.translate(texto, Translate.TranslateOption.sourceLanguage("es"), Translate.TranslateOption.targetLanguage("en"));
        }
        String translatedText = translation.getTranslatedText();
        Log.d("Translation", "Texto traducido: " + translatedText);
        return translatedText;
    }
}
