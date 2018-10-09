package com.jhonatanrocha.whatsapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.jhonatanrocha.whatsapp.R;

import java.util.Random;

public class LoginActivity extends Activity {

    private EditText editTextNome;
    private EditText editTextCodigoPais;
    private EditText editTextCodigoArea;
    private EditText editTextTelefone;
    private Button botaoCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextNome = (EditText) findViewById(R.id.editTextNome);
        editTextCodigoPais = (EditText) findViewById(R.id.editTextCodigoPais);
        editTextCodigoArea = (EditText) findViewById(R.id.editTextCodigoArea);
        editTextTelefone = (EditText) findViewById(R.id.editTextTelefone);
        botaoCadastrar = (Button) findViewById(R.id.botaoCadastrar);

        SimpleMaskFormatter simpleMaskPaisTelefone = new SimpleMaskFormatter("+NN");
        SimpleMaskFormatter simpleMaskAreaTelefone = new SimpleMaskFormatter("NN");
        SimpleMaskFormatter simpleMaskTelefone = new SimpleMaskFormatter("NNNNN-NNNN");

        editTextCodigoPais.addTextChangedListener(new MaskTextWatcher(editTextCodigoPais, simpleMaskPaisTelefone));
        editTextCodigoArea.addTextChangedListener(new MaskTextWatcher(editTextCodigoArea, simpleMaskAreaTelefone));
        editTextTelefone.addTextChangedListener(new MaskTextWatcher(editTextTelefone, simpleMaskTelefone));

        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               final String nomeUsuario = editTextNome.getText().toString();
               final String telefoneCompleto = editTextCodigoPais.getText().toString() +
                       editTextCodigoArea.getText().toString() + editTextTelefone.getText().toString();

                String telefoneSemFormatacao = telefoneCompleto.replace("+", "");
                telefoneSemFormatacao = telefoneSemFormatacao.replace("-", "");
                Log.i("JHOL:", telefoneSemFormatacao);

                //Gerar Token
                final int numeroRandomico = new Random().nextInt(9999 - 1000) + 1000;

                String token = String.valueOf(numeroRandomico);
                Log.i("JHOL:", token);
            }
        });
    }
}
