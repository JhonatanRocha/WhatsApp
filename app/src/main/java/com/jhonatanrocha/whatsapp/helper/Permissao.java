package com.jhonatanrocha.whatsapp.helper;


import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class Permissao {

    public static Boolean validaPermissoes(Integer requestCode, Activity activity, String[] permissoes) {

        Boolean validaPermissoes = Boolean.TRUE;

        if(Build.VERSION.SDK_INT >= 23) {

            List<String> listaPermissoes = new ArrayList<String>();

            for(String permissao : permissoes) {
                final Boolean validaPermissao = ContextCompat.checkSelfPermission(activity, permissao) == PackageManager.PERMISSION_GRANTED;
                if(!validaPermissao) {
                    listaPermissoes.add(permissao);
                }
            }

            if(listaPermissoes.isEmpty()) {
                return Boolean.TRUE;
            } else {
                String[] arrayPermissoes = new String[listaPermissoes.size()];
                arrayPermissoes = listaPermissoes.toArray(arrayPermissoes);

                ActivityCompat.requestPermissions(activity, arrayPermissoes, requestCode);
            }
        }

        return validaPermissoes;
    }
}
