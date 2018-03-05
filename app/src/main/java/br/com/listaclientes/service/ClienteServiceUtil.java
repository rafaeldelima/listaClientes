package br.com.listaclientes.service;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.listaclientes.commons.Cliente;
import br.com.listaclientes.constantes.ConstantesActivity;
import br.com.listaclientes.listaclientes.ListaPesquisaActivity;
import br.com.listaclientes.listaclientes.MainActivity;
import br.com.listaclientes.listaclientes.NovoUsuarioActivity;
import br.com.listaclientes.listaclientes.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rafab on 02/03/2018.
 */

public class ClienteServiceUtil {

    private static List<Cliente> clientes =  new ArrayList<>();
    private static List<Cliente> clientesPesquisa =  new ArrayList<>();

    private ClienteServiceUtil(){

    }

    public static List<Cliente> buscarClientes(String usuarioId) {

        Call<List<Cliente>> call = new RetrofitConfig().getClienteService().buscarClientes(usuarioId);
        try {
            List<Cliente> retorno = call.execute().body();
            if(retorno != null){
                clientes = retorno;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return clientes;
    }


    public static void pesquisarClientes(String idUsuarioLogado, String nome) {

        Call<List<Cliente>> call =new RetrofitConfig().getClienteService().pesquisarClientes(idUsuarioLogado, nome);
        try {
            clientesPesquisa = call.execute().body();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void excluirClientes(Cliente cliente) {
        Call<Void> call = new RetrofitConfig().getClienteService().deletarCliente(cliente.getId());
        try {
            call.execute().body();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void adicionarClientes(Cliente cliente) {

        Call<Cliente> call =new RetrofitConfig().getClienteService().cadastrarCliente(cliente);
        try {
            clientes.add(call.execute().body());
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public static void atualizar(Cliente cliente) {
        Call<Void> call = new RetrofitConfig().getClienteService().atualizarCliente(cliente);
        try {
            call.execute().body();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static List<Cliente> getClientes() {
        return clientes;
    }

    public static List<Cliente> getClientesPesquisa() {
        return clientesPesquisa;
    }
}
