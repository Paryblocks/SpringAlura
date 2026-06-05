package br.com.screenmatch.model.veiculo;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosVeiculo(String Valor, String Marca, String Modelo,
                           @JsonAlias("AnoModelo") String Ano, String Combustivel) {
}
