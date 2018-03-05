package br.com.listaclientes.adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.com.listaclientes.commons.Cliente;
import br.com.listaclientes.holder.ClienteViewHolder;
import br.com.listaclientes.listaclientes.R;
import br.com.listaclientes.mascara.MascaraTelefone;
import br.com.listaclientes.service.ClienteServiceUtil;

/**
 * Created by rafab on 01/03/2018.
 */

public class ClienteAdapter extends RecyclerView.Adapter{

    private List<Cliente> clientes;
    private Context context;
    private String usuarioId;

    public ClienteAdapter(List<Cliente> clientes, Context context, String usuarioId) {
        this.clientes = clientes;
        this.context = context;
        this.usuarioId = usuarioId;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.activity_lista_cliente_personalizado, parent, false);
        ClienteViewHolder holder = new ClienteViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        final ClienteViewHolder holder = (ClienteViewHolder) viewHolder;

        final Cliente cliente  = clientes.get(position);
        holder.nome.setText(cliente.getNome());
        holder.telefone.setText(cliente.getTelefone());
        holder.endereco.setText(cliente.getEndereco());

        holder.nome.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.activity_detalhe_lista);
                Window window = dialog.getWindow();
                window.setGravity(Gravity.TOP);

                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                lp.copyFrom(window.getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                window.setAttributes(lp);

                final TextView etNome = (TextView) dialog.findViewById(R.id.etNome);
                final TextView etTelefone = (TextView) dialog.findViewById(R.id.etTelefone);
                final TextView etEndereco = (TextView) dialog.findViewById(R.id.etEndereco);
                final TextView etObservacao = (TextView) dialog.findViewById(R.id.etObservacao);

                etNome.setText( holder.nome.getText().toString());
                etTelefone.setText( holder.telefone.getText().toString());
                etEndereco.setText( holder.endereco.getText().toString());

                Button btCancelar = (Button) dialog.findViewById(R.id.btnCancelar);
                btCancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                Button btExcluir = (Button) dialog.findViewById(R.id.btnExcluir);
                btExcluir.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ClienteServiceUtil.excluirClientes(clientes.get(position));
                        clientes.remove(position);
                        notifyItemRemoved(position);
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });

                Button btnEditarCliente = (Button) dialog.findViewById(R.id.btnEditarCliente);
                btnEditarCliente.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                        final Dialog dialogEditar = new Dialog(context);
                        Window window = dialogEditar.getWindow();
                        window.setGravity(Gravity.TOP);
                        dialogEditar.setContentView(R.layout.activity_editar_cliente);
                        dialogEditar.setTitle("Editar Cliente");

                        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                        lp.copyFrom(window.getAttributes());
                        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                        window.setAttributes(lp);

                        final EditText etNome = (EditText) dialogEditar.findViewById(R.id.etNome);
                        final EditText etTelefone = (EditText) dialogEditar.findViewById(R.id.etTelefone);
                        final EditText etEndereco = (EditText) dialogEditar.findViewById(R.id.etEndereco);
                        final EditText etObservacao = (EditText) dialogEditar.findViewById(R.id.etObservacao);

                        etTelefone.addTextChangedListener(MascaraTelefone.insert("(##)#####-####",etTelefone));

                        etNome.setText(holder.nome.getText().toString());
                        etTelefone.setText(holder.telefone.getText().toString());
                        etEndereco.setText(holder.endereco.getText().toString());

                        Button btnSalvar = (Button) dialogEditar.findViewById(R.id.btnSalvar);
                        btnSalvar.setOnClickListener(new View.OnClickListener(){

                            @Override
                            public void onClick(View v) {
                                cliente.setNome(etNome.getText().toString());
                                cliente.setTelefone(etTelefone.getText().toString());
                                cliente.setEndereco(etEndereco.getText().toString());
                                cliente.setObservacao(etObservacao.getText().toString());

                                ClienteServiceUtil.atualizar(cliente);
                                notifyItemChanged(position);
                                notifyDataSetChanged();

                                dialogEditar.dismiss();
                                Toast.makeText(context,context.getString(R.string.cliente_atualizado_com_sucesso),Toast.LENGTH_SHORT).show();


                            }

                        });
                        Button btnCancelar = (Button) dialogEditar.findViewById(R.id.btnCancelar);
                            btnCancelar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialogEditar.dismiss();
                            }
                        });


                        dialogEditar.show();
                        dialog.dismiss();
                    }
                });
                Button btnCancelar = (Button) dialog.findViewById(R.id.btnCancelar);
                btnCancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();


            }


        });
    }
    @Override
    public int getItemCount() {
        return clientes.size();
    }



    public List<Cliente> getClientes() {
        return clientes;
    }
}
