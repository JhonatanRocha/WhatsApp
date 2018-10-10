package com.jhonatanrocha.whatsapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.jhonatanrocha.whatsapp.R;
import com.jhonatanrocha.whatsapp.helper.Preferencias;

import java.util.HashMap;
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

                //Gerar Token
                final int numeroRandomico = new Random().nextInt(9999 - 1000) + 1000;
                final String token = String.valueOf(numeroRandomico);
                final String mensagemEnvio = "Whatsapp Jhol Código de Confirmação: " + token;

                //Salvar os dados para validação
                final Preferencias preferencias = new Preferencias(LoginActivity.this);
                preferencias.salvarUsuarioPreferencias(nomeUsuario, telefoneSemFormatacao, token);

                //Envio de SMS
                telefoneSemFormatacao = "5554";
                Boolean smsEnviadoComSucesso = enviarSMS("+" + telefoneSemFormatacao, mensagemEnvio);


//                HashMap<String, String> usuario = preferencias.getDadosUsuario();
//                Log.i("Recupernando Dados:", "T: " + usuario.get("token"));

            }
        });
    }

    /**
     * Envio de SMS
     */
    private Boolean enviarSMS(String telefone, String mensagem) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(telefone, null, mensagem, null, null);

            return Boolean.TRUE;
        } catch (Exception e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }
}
