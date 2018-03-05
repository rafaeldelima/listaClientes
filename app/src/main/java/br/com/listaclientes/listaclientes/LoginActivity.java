package br.com.listaclientes.listaclientes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import br.com.listaclientes.commons.Usuario;
import br.com.listaclientes.constantes.ConstantesActivity;
import br.com.listaclientes.service.RetrofitConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText etLogin​;
    private EditText etSenha​;
    private Context context;
    private CheckBox checkManterConectador;
    private String idUsuarioLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        context = this;
        etLogin​ = (EditText) findViewById(R.id.etLogin);
        etSenha​ = (EditText) findViewById(R.id.etSenha);
        checkManterConectador = (CheckBox)findViewById(R.id.checkManterConectador);

        if(isConectado()){
            iniciarApp(idUsuarioLogado);
        }

    }
    public void novoCadastro(View view){
        Intent intent = new Intent(this, NovoUsuarioActivity.class);
        startActivity(intent);
    }
    public void logar(View view){
        final String login = etLogin​.getText().toString();
        final String senha = etSenha​.getText().toString();
        etLogin​.setEnabled(false);
        etSenha​.setEnabled(false);

        try{
            if(!dadosDoLoginValidos(login, senha)){

                Call<Usuario> call = new RetrofitConfig().getUsuarioService().buscarUsuario(login);
                try {
                    Usuario usuarioLogado = call.execute().body();
                    if(usuarioLogado == null){
                        Toast.makeText(context, context.getString(R.string.usuario_ou_senha_invalidos),
                                Toast.LENGTH_LONG).show();
                    }else if(usuarioLogado.getSenha().equals(senha)) {
                        if(checkManterConectador.isChecked()){
                            manterConectado(usuarioLogado.getId());
                        }
                        iniciarApp(usuarioLogado.getId());
                    }else{
                        Toast.makeText(context,context.getString(R.string.senha_incorreta),
                                Toast.LENGTH_LONG).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(context, context.getString(R.string.usuario_ou_senha_invalidos),
                            Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(this, context.getString(R.string.usuario_ou_senha_invalidos),
                        Toast.LENGTH_LONG).show();
            }
        }finally {
            etLogin​.setEnabled(true);
            etSenha​.setEnabled(true);
        }
    }

    private boolean dadosDoLoginValidos(String login, String senha){
        return login == null || senha == null || login.trim().equals("") || senha.trim().equals("");
    }

    private void iniciarApp(String idUsuarioLogado){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(ConstantesActivity.ID_USUARIO_SESSAO, idUsuarioLogado);
        startActivity(intent);
        LoginActivity.this.finish();
    }

    private void manterConectado(String idUsuarioLogado){
        SharedPreferences pref = getSharedPreferences(ConstantesActivity.KEY_APP_PREFERENCES,
                MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(ConstantesActivity.KEY_LOGIN, idUsuarioLogado);
        editor.apply();
    }

    private boolean isConectado(){
        SharedPreferences pref = getSharedPreferences(ConstantesActivity.KEY_APP_PREFERENCES,
                MODE_PRIVATE);
        String idUsuario = pref.getString(ConstantesActivity.KEY_LOGIN, "");
        if(idUsuario == null || idUsuario.equals("")){
            return false;
        }else{
            idUsuarioLogado = idUsuario;
            return true;
        }

    }
}
