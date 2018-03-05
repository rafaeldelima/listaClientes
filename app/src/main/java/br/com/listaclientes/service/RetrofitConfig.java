package br.com.listaclientes.service;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by rafab on 03/03/2018.
 */

public class RetrofitConfig {

    private final Retrofit retrofit;

    public RetrofitConfig() {
        this.retrofit =
                new Retrofit.Builder()
                        .baseUrl("http://192.168.0.101:8083/")
                        .addConverterFactory(JacksonConverterFactory.create())
                        .build();
    }

    public UsuarioService getUsuarioService() {
        return this.retrofit.create(UsuarioService.class);
    }

    public ClienteService getClienteService() {
        return this.retrofit.create(ClienteService.class);
    }

}
