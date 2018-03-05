package br.com.listaclientes.service;

import java.util.List;

import br.com.listaclientes.commons.Cliente;
import br.com.listaclientes.commons.Usuario;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by rafab on 03/03/2018.
 */

public interface ClienteService {

    @GET("/cliente/usuarioId/{usuarioId}")
    Call<List<Cliente>> buscarClientes(@Path("usuarioId") String usuarioId);

    @GET("/cliente/pesquisar/{usuarioId}//nome/{nome}")
    Call<List<Cliente>> pesquisarClientes(@Path("usuarioId") String usuarioId, @Path("nome") String nome);

    @POST("/cliente")
    Call<Cliente> cadastrarCliente(@Body Cliente cliente);

    @PUT("/cliente")
    Call<Void> atualizarCliente(@Body Cliente cliente);

    @DELETE("/cliente/id/{id}")
    Call<Void> deletarCliente(@Path("id") String id);
}
