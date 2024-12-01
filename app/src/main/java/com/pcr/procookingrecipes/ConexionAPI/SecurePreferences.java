package com.pcr.procookingrecipes.ConexionAPI;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;
import android.content.Context;

import org.json.JSONObject;

import java.io.InputStream;

public class SecurePreferences {

    private static final String PREFS_NAME = "encrypted_prefs";

    // Método para guardar una clave API identificada por un nombre
    public static void guardarApiKey(Context context, String keyName, String apiKey) {
        try {
            String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);

            EncryptedSharedPreferences encryptedSharedPreferences = (EncryptedSharedPreferences) EncryptedSharedPreferences.create(
                    PREFS_NAME,
                    masterKeyAlias,
                    context,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );

            encryptedSharedPreferences.edit().putString(keyName, apiKey).apply();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para leer una clave API identificada por un nombre
    public static String leerApiKey(Context context, String keyName) {
        try {
            String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);

            EncryptedSharedPreferences encryptedSharedPreferences = (EncryptedSharedPreferences) EncryptedSharedPreferences.create(
                    PREFS_NAME,
                    masterKeyAlias,
                    context,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );

            return encryptedSharedPreferences.getString(keyName, null);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void cargarClavesDesdeArchivo(Context context) {
        try {
            // Lee el archivo JSON de assets
            InputStream is = context.getAssets().open("API_KEYS.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");

            // Parsear el JSON
            JSONObject jsonObject = new JSONObject(json);

            // Guardar las claves encriptadas
            SecurePreferences.guardarApiKey(context, "API_KEY_SPOONACULAR", jsonObject.getString("API_KEY_SPOONACULAR"));
            SecurePreferences.guardarApiKey(context, "API_KEY_TRADUCTOR", jsonObject.getString("API_KEY_TRADUCTOR"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

