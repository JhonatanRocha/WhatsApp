package com.jhonatanrocha.whatsapp.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.jhonatanrocha.whatsapp.R;
import com.jhonatanrocha.whatsapp.helper.Permissao;
import com.jhonatanrocha.whatsapp.helper.Preferencias;

import java.util.HashMap;
import java.util.Random;

public class LoginActivity extends Activity {

    private EditText editTextNome;
    private EditText editTextCodigoPais;
    private EditText editTextCodigoArea;
    private EditText editTextTelefone;
    private Button botaoCadastrar;
    private String[] permissoesNecessarias = new String[] {
            Manifest.permission.SEND_SMS
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Permissao.validaPermissoes(1, this, permissoesNecessarias);

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
                //Usar isso apenas no Emulador
                telefoneSemFormatacao = "5554";
                Boolean smsEnviadoComSucesso = enviarSMS("+" + telefoneSemFormatacao, mensagemEnvio);

                if(smsEnviadoComSucesso) {
                    Intent intent = new Intent(LoginActivity.this, ValidadorActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Problema ao enviar SMS, tente novamente mais tarde!", Toast.LENGTH_LONG).show();
                }

//              HashMap<String, String> usuario = preferencias.getDadosUsuario();
//              Log.i("Recupernando Dados:", "T: " + usuario.get("token"));

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

    @TargetApi(Build.VERSION_CODES.M)
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for(int resultado : grantResults) {
            if(resultado == PackageManager.PERMISSION_DENIED) {
                alteraValidacaoPermissao();
            }
        }
    }

    private void alteraValidacaoPermissao() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissões Negadas");
        builder.setMessage("Para Utilizar este Aplicativo, é necessário aceitar as permissões");

        builder.setPositiveButton("CONFIRMAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
