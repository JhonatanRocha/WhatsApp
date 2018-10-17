package com.jhonatanrocha.whatsapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.jhonatanrocha.whatsapp.R;
import com.jhonatanrocha.whatsapp.helper.Preferencias;

import java.util.HashMap;

public class ValidadorActivity extends Activity {

    private EditText editTextValidacao;
    private Button botaoValidar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validador);

        final SimpleMaskFormatter simpleMaskCodigoValidacao = new SimpleMaskFormatter("NNNN");

        editTextValidacao = (EditText) findViewById(R.id.editTextCodigoValidacao);
        botaoValidar = (Button) findViewById(R.id.botaoValidar);

        editTextValidacao.addTextChangedListener(new MaskTextWatcher(editTextValidacao, simpleMaskCodigoValidacao));

        botaoValidar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Recuperar Dados das Preferencias do Usuário
                Preferencias preferencias = new Preferencias(ValidadorActivity.this);
                HashMap<String, String> usuario = preferencias.getDadosUsuario();

                String tokenGerado = usuario.get("token");
                String tokenDigitado = editTextValidacao.getText().toString();

                if(tokenDigitado.equals(tokenGerado)) {
                    Toast.makeText(ValidadorActivity.this, "Token Validado com Sucesso!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ValidadorActivity.this, "Token Invalidado! Verifique suas mensagens SMS!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
