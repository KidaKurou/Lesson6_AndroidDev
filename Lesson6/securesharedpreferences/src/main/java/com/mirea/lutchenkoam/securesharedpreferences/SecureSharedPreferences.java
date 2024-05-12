package com.mirea.lutchenkoam.securesharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class SecureSharedPreferences {
    private static final String PREF_FILE_NAME = "secure_prefs";
    private static final String PREF_KEY_NAME = "secure";

    private SharedPreferences sharedPreferences;

    public SecureSharedPreferences(Context context) {
        try {
            String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
            sharedPreferences = EncryptedSharedPreferences.create(
                    PREF_FILE_NAME,
                    masterKeyAlias,
                    context,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }
    }

    public void saveData(String data) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PREF_KEY_NAME, data);
        editor.apply();
    }

    public String getData() {
        return sharedPreferences.getString(PREF_KEY_NAME, "");
    }
}
