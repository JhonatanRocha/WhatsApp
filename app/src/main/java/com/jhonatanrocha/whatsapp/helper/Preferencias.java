package com.jhonatanrocha.whatsapp.helper;


import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class Preferencias {

    private Context contexto;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPreferencesEditor;
    private static final String NOME_ARQUIVO = "whatsapp.preferencias";
    private static final String CHAVE_NOME = "nome";
    private static final String CHAVE_TELEFONE = "telefone";
    private static final String CHAVE_TOKEN = "token";

    public Preferencias(Context contextoParam) {
        contexto = contextoParam;
        sharedPreferences = contexto.getSharedPreferences(NOME_ARQUIVO, Context.MODE_PRIVATE);
        sharedPreferencesEditor = sharedPreferences.edit();
    }

    public void salvarUsuarioPreferencias(String nome, String telefone, String token) {
        sharedPreferencesEditor.putString(CHAVE_NOME, nome);
        sharedPreferencesEditor.putString(CHAVE_TELEFONE, telefone);
        sharedPreferencesEditor.putString(CHAVE_TOKEN, token);

        sharedPreferencesEditor.commit();
    }

    public HashMap<String, String> getDadosUsuario() {
        final HashMap<String, String> dadosUsuario = new HashMap<String, String>();

        dadosUsuario.put(CHAVE_NOME, sharedPreferences.getString(CHAVE_NOME, null));
        dadosUsuario.put(CHAVE_TELEFONE, sharedPreferences.getString(CHAVE_TELEFONE, null));
        dadosUsuario.put(CHAVE_TOKEN, sharedPreferences.getString(CHAVE_TOKEN, null));

        return dadosUsuario;
    }
}
