package br.com.listaclientes.listaclientes;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionMenu;

import java.util.List;

import br.com.listaclientes.adapter.ClienteAdapter;
import br.com.listaclientes.commons.Cliente;
import br.com.listaclientes.constantes.ConstantesActivity;
import br.com.listaclientes.mascara.MascaraTelefone;
import br.com.listaclientes.service.ClienteServiceUtil;

public class ListaActivity extends AppCompatActivity {

    private FloatingActionMenu fMenu​;
    private String idUsuarioLogado = null;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        this.context = this;
        this.fMenu​ = (FloatingActionMenu) findViewById(R.id.fMenu);
        idUsuarioLogado = getIntent().getStringExtra(ConstantesActivity.ID_USUARIO_SESSAO);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);

        recyclerView.setAdapter(new ClienteAdapter(ClienteServiceUtil.getClientes(), this, idUsuarioLogado));

        RecyclerView.LayoutManager layout = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(layout);

    }

    public void inicio(View view){
        ListaActivity.this.finish();
    }

    public void novoCliente(View view){
        this.fMenu​.close(true);
        dialogCadastrarCliente(new Cliente());
    }

    public void pesquisar(View viem){
        this.fMenu​.close(true);
        dialogPesquisarCliente();
    }

    private void dialogPesquisarCliente(){
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        final Dialog dialog = new Dialog(this);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.TOP);
        dialog.setContentView(R.layout.activity_pesquisar_cliente);
        dialog.setTitle("Pesquisar Cliente");

        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

        final List<Cliente> clientesLista = ClienteServiceUtil.getClientes();
        final EditText etNome = (EditText) dialog.findViewById(R.id.etNome);

        Button btnPesquisar = (Button) dialog.findViewById(R.id.btnPesquisar);
        btnPesquisar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String nome = etNome.getText().toString();
                if(nome == null || nome.trim().equals("")){

                    Toast.makeText(ListaActivity.this,context.getString(R.string.informe_um_nome_para_pesquisa),Toast.LENGTH_SHORT).show();
                }else{
                    ClienteServiceUtil.pesquisarClientes(idUsuarioLogado, etNome.getText().toString());
                    Intent intent = new Intent(context, ListaPesquisaActivity.class);
                    intent.putExtra(ConstantesActivity.ID_USUARIO_SESSAO, idUsuarioLogado);
                    startActivity(intent);

                    dialog.dismiss();
                    Toast.makeText(ListaActivity.this,context.getString(R.string.pesquisa_efetuada),Toast.LENGTH_SHORT).show();
                }

            }

        });

        Button btCancelar = (Button) dialog.findViewById(R.id.btnCancelar);
        btCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void dialogCadastrarCliente(final Cliente cliente){
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        final Dialog dialog = new Dialog(this);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.TOP);
        dialog.setContentView(R.layout.activity_novo_cliente);

        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

        final EditText etNome = (EditText) dialog.findViewById(R.id.etNome);
        final EditText etTelefone = (EditText) dialog.findViewById(R.id.etTelefone);
        final EditText etEndereco = (EditText) dialog.findViewById(R.id.etEndereco);
        final EditText etObservacao = (EditText) dialog.findViewById(R.id.etObservacao);

        etTelefone.addTextChangedListener(MascaraTelefone.insert("(##)#####-####",etTelefone));

        Button btnCadastrar = (Button) dialog.findViewById(R.id.btnCadastrar);
        btnCadastrar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                cliente.setNome(etNome.getText().toString());
                cliente.setTelefone(etTelefone.getText().toString());
                cliente.setEndereco(etEndereco.getText().toString());
                cliente.setObservacao(etObservacao.getText().toString());

                ClienteServiceUtil.adicionarClientes(cliente);

                dialog.dismiss();
                Toast.makeText(ListaActivity.this,context.getString(R.string.cliente_salvo_com_sucesso),Toast.LENGTH_SHORT).show();

            }

        });

        Button btCancelar = (Button) dialog.findViewById(R.id.btnCancelar);
        btCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


}
