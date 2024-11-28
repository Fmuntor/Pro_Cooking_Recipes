package com.pcr.procookingrecipes.ConexionAPI;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;
import android.content.Context;

public class SecurePreferences {

    private static final String PREFS_NAME = "encrypted_prefs";
    private static final String API_KEY_KEY = "api_key";

    // Método para guardar la clave API en EncryptedSharedPreferences
    public static void guardarApiKey(Context context, String apiKey) {
        try {
            // Crear la clave maestra para cifrado
            String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);

            // Crear un EncryptedSharedPreferences para guardar la clave de forma segura
            EncryptedSharedPreferences encryptedSharedPreferences = (EncryptedSharedPreferences) EncryptedSharedPreferences.create(
                    PREFS_NAME,
                    masterKeyAlias,
                    context,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );

            // Guardar la clave API de manera segura
            encryptedSharedPreferences.edit().putString(API_KEY_KEY, apiKey).apply();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String leerApiKey(Context context) {
        try {
            // Crear o recuperar la clave maestra para cifrado
            String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);

            // Crear un EncryptedSharedPreferences para leer la clave de manera segura
            EncryptedSharedPreferences encryptedSharedPreferences = (EncryptedSharedPreferences) EncryptedSharedPreferences.create(
                    PREFS_NAME,
                    masterKeyAlias,
                    context,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );

            // Leer y retornar la clave API
            return encryptedSharedPreferences.getString(API_KEY_KEY, null);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
