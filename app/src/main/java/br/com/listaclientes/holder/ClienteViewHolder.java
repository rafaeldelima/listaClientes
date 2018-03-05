package br.com.listaclientes.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import br.com.listaclientes.listaclientes.R;

/**
 * Created by rafab on 01/03/2018.
 */

public class ClienteViewHolder  extends RecyclerView.ViewHolder {

    public final TextView nome;
    public final TextView telefone;
    public final TextView endereco;

    public ClienteViewHolder(View view) {
        super(view);
        nome = (TextView) view.findViewById(R.id.tvNome);
        telefone = (TextView) view.findViewById(R.id.tvTelefone);
        endereco = (TextView) view.findViewById(R.id.tvEndereco);
    }
}
