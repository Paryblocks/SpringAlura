package br.com.screenmatch.service;

public interface IConverteDados {
    // <T> T ---> Significa objeto generico, usado para trabalhar com parametros desconhecidos
    <T> T obterDados(String json, Class<T> classe);
}
