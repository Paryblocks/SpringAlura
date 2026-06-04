package br.com.screenmatch.service;

public interface IConverteDados {
    // <T> T ---> Significa objeto generico, usado para não especificar
    <T> T obterDados(String json, Class<T> classe);
}
