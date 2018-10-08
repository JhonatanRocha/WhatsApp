package com.jhonatanrocha.whatsapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.jhonatanrocha.whatsapp.R;

public class LoginActivity extends Activity {

    private EditText editTextNome;
    private EditText editTextCodigoPais;
    private EditText editTextCodigoArea;
    private EditText editTextTelefone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextNome = (EditText) findViewById(R.id.editTextCodigoValidacao);
        editTextCodigoPais = (EditText) findViewById(R.id.editTextCodigoPais);
        editTextCodigoArea = (EditText) findViewById(R.id.editTextCodigoArea);
        editTextTelefone = (EditText) findViewById(R.id.editTextTelefone);

        SimpleMaskFormatter simpleMaskPaisTelefone = new SimpleMaskFormatter("+NN");
        SimpleMaskFormatter simpleMaskAreaTelefone = new SimpleMaskFormatter("NN");
        SimpleMaskFormatter simpleMaskTelefone = new SimpleMaskFormatter("NNNNN-NNNN");

        editTextCodigoPais.addTextChangedListener(new MaskTextWatcher(editTextCodigoPais, simpleMaskPaisTelefone));
        editTextCodigoArea.addTextChangedListener(new MaskTextWatcher(editTextCodigoArea, simpleMaskAreaTelefone));
        editTextTelefone.addTextChangedListener(new MaskTextWatcher(editTextTelefone, simpleMaskTelefone));
    }
}
