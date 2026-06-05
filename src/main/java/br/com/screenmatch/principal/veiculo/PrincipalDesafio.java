package br.com.screenmatch.principal.veiculo;

import br.com.screenmatch.model.veiculo.Dados;
import br.com.screenmatch.model.veiculo.DadosModelo;
import br.com.screenmatch.model.veiculo.DadosVeiculo;
import br.com.screenmatch.service.ConsumoApi;
import br.com.screenmatch.service.ConverteDados;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class PrincipalDesafio {
    private final String ENDERECO = "https://parallelum.com.br/fipe/api/v1/";

    private Scanner ler = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverteDados converteDados = new ConverteDados();

    // Função para a impressão do retorno generico
    public void iterarDados(List<Dados> dados){
        dados.stream()
                .map(d -> "Cód: " + d.codigo() + "  Descrição: " + d.nome())
                .forEach(System.out::println);
    }

    public void exibirMenu(){
        System.out.println("""
                            *** OPÇÕES ***
                            Carro
                            Moto
                            Caminhão
                            
                            Digite uma das opções para consultar valores: 
                            """);
        var tipoVeiculo = ler.nextLine();

        // Montagem do endereço de acordo com o digitado
        var endereco = "";
        if (tipoVeiculo.toLowerCase().contains("carr")){
            endereco = ENDERECO + "carros/marcas";
        } else if (tipoVeiculo.toLowerCase().contains("mot")) {
            endereco = ENDERECO + "motos/marcas";
        } else if (tipoVeiculo.toLowerCase().contains("caminh")) {
            endereco = ENDERECO + "caminhoes/marcas";
        }

        // Verificação se o endereço é valído
        if (!endereco.isEmpty()){
            var json = consumoApi.obterDados(endereco);
            var marcas = converteDados.obterLista(json, Dados.class);

            // Impressão do próximo menu
            iterarDados(marcas);
            System.out.println("Informe o código da marca para a consulta: ");

            // Criação do proximo endereco e chamada seguinte
            var cod = ler.nextLine();
            endereco = endereco + "/" + cod + "/modelos";

            // Retorna uma lista dentro do Json
            json = consumoApi.obterDados(endereco);
            var modelos = converteDados.obterDados(json, DadosModelo.class);
            iterarDados(modelos.modelos());

            // Consulta de veiculo
            System.out.println("Digite um trecho do nome do veículo para a consulta: ");
            var veiculo = ler.nextLine();

            // Filtra carro selecionado
            modelos.modelos().stream()
                    .filter(v -> v.nome().toLowerCase().contains(veiculo.toLowerCase()))
                    .map(d -> "Cód: " + d.codigo() + "  Descrição: " + d.nome())
                    .forEach(System.out::println);

            System.out.println("Digite o código do modelo para consultar valores: ");
            var modelo = ler.nextLine();
            endereco = endereco + "/" + modelo + "/anos";

            // Busca todos os anos daquele modelo
            json = consumoApi.obterDados(endereco);
            var anos = converteDados.obterLista(json, Dados.class);

            List<DadosVeiculo> veiculos = new ArrayList<>();
            var enderecoPrevio = endereco;
            for(Dados item : anos){
                endereco = enderecoPrevio + "/" + item.codigo();
                json = consumoApi.obterDados(endereco);
                veiculos.add(converteDados.obterDados(json, DadosVeiculo.class));
            }

            System.out.println("Todos os veiculos com os valores por ano: ");
            veiculos.forEach(System.out::println);
        } else {
            System.out.println("Opção Inválida!");
        }
    }
}
