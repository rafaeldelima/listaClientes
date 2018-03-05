package br.com.listaclientes.listaclientes;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import br.com.listaclientes.commons.Usuario;
import br.com.listaclientes.service.RetrofitConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NovoUsuarioActivity extends AppCompatActivity {

    private EditText etLogin​;
    private EditText etSenha​;
    private EditText etSenha​2;

    private Context context;
    private Boolean usuarioJaExiste = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_usuario);
        context = this;

        etLogin​ = (EditText) findViewById(R.id.etLogin);
        etSenha​ = (EditText) findViewById(R.id.etSenha);
        etSenha​2 = (EditText) findViewById(R.id.etSenha2);
    }

    public void cadastrar(View view){
        String login = etLogin​.getText().toString();
        String senha = etSenha​.getText().toString();
        String senha2 = etSenha​2.getText().toString();
        if(!dadosDoLoginValidos(login, senha, senha2) && senhasIguais(senha, senha2)){
            Usuario usuario = new Usuario();
            usuario.setLogin(login);
            usuario.setSenha(senha);
            new RetrofitConfig().getUsuarioService().cadastrarUsuario(usuario).enqueue(
                    new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if(response.code() == 200){
                                Toast.makeText(context, context.getString(R.string.cadastro_realizado_com_sucesso),
                                        Toast.LENGTH_LONG).show();
                                NovoUsuarioActivity.this.finish();
                            }else if(response.code() == 409){
                                    Toast.makeText(context, context.getString(R.string.ja_existe_usuario),
                                            Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(context, context.getString(R.string.erro_ao_cadastrar_usuario),
                                        Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            t.printStackTrace();
                            Toast.makeText(context, context.getString(R.string.erro_ao_cadastrar_usuario),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
            );
        }else{
            if(senhasIguais(senha, senha2)){
                Toast.makeText(this, context.getString(R.string.usuario_ou_senha_invalidos),
                        Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(this, context.getString(R.string.senhas_diferentes),
                        Toast.LENGTH_LONG).show();
            }
        }
    }
    private boolean senhasIguais(String senha, String senha2){
        return senha.trim().equals(senha2.trim());
    }

    private boolean dadosDoLoginValidos(String login, String senha, String senha2){
        return login == null || senha == null || senha2 == null  ||
                login.trim().equals("") || senha.trim().equals("") || senha2.trim().equals("");
    }
}
