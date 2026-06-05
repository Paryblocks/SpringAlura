package br.com.screenmatch.service;

import java.util.List;

public interface IConverteDados {
    // <T> T ---> Significa objeto generico, usado para trabalhar com parametros desconhecidos
    <T> T obterDados(String json, Class<T> classe);

    <T> List<T> obterLista(String json, Class<T> classe);
}
