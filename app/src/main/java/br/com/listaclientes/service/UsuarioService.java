package br.com.listaclientes.service;

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

public interface UsuarioService {

    @GET("/usuario/login/{login}")
    Call<Usuario> buscarUsuario(@Path("login") String login);

    @POST("/usuario")
    Call<Void> cadastrarUsuario(@Body Usuario usuario);

    @PUT("/usuario")
    Call<Void> atualizarUsuario(@Body Usuario usuario);

    @DELETE("/usuario")
    Call<Void> deletarUsuario(@Body Usuario usuario);
}
