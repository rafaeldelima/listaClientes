package br.com.listaclientes.listaclientes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import br.com.listaclientes.constantes.ConstantesActivity;
import br.com.listaclientes.service.ClienteServiceUtil;

public class MainActivity extends AppCompatActivity {

    private String idUsuarioLogado = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        idUsuarioLogado = getIntent().getStringExtra(ConstantesActivity.ID_USUARIO_SESSAO);

        ClienteServiceUtil.buscarClientes(idUsuarioLogado);
    }

    public void listar(View v){
        Intent intent = new Intent(this, ListaActivity.class);
        intent.putExtra(ConstantesActivity.ID_USUARIO_SESSAO, idUsuarioLogado);
        startActivity(intent);
    }

    public void deslogar(View v){

        SharedPreferences pref = getSharedPreferences(ConstantesActivity.KEY_APP_PREFERENCES,
                MODE_PRIVATE);

        String idUsuario = pref.getString(ConstantesActivity.KEY_LOGIN, "");
        if(idUsuario != null || !idUsuario.equals("")){
            SharedPreferences.Editor editor = pref.edit();

            editor.remove(ConstantesActivity.KEY_LOGIN);
            editor.commit();
            pref.edit().remove(ConstantesActivity.KEY_LOGIN);
            pref.getAll().remove(ConstantesActivity.KEY_LOGIN);
            pref.edit().apply();
        }

        String idUsuario2 = pref.getString(ConstantesActivity.KEY_LOGIN, "");
        if(idUsuario2 != null || !idUsuario2.equals("")){
            SharedPreferences.Editor editor = pref.edit();

            editor.remove(ConstantesActivity.KEY_LOGIN);
            editor.commit();
            pref.edit().remove(ConstantesActivity.KEY_LOGIN);
            pref.getAll().remove(ConstantesActivity.KEY_LOGIN);
            pref.edit().apply();
        }
        MainActivity.this.finish();
    }

    public void sobre(View v){
        Intent intent = new Intent(this, SobreActivity.class);
        startActivity(intent);

    }

}
