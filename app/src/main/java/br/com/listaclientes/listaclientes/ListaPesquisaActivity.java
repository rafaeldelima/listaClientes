package br.com.listaclientes.listaclientes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import br.com.listaclientes.adapter.ClienteAdapter;
import br.com.listaclientes.constantes.ConstantesActivity;
import br.com.listaclientes.service.ClienteServiceUtil;

public class ListaPesquisaActivity extends AppCompatActivity {

    private String idUsuarioLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pesquisa);
        idUsuarioLogado = getIntent().getStringExtra(ConstantesActivity.ID_USUARIO_SESSAO);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_lista_pesquisa);

        recyclerView.setAdapter(new ClienteAdapter(ClienteServiceUtil.getClientesPesquisa(), this, idUsuarioLogado));

        RecyclerView.LayoutManager layout = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(layout);
    }
}
