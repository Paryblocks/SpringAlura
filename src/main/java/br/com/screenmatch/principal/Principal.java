package br.com.screenmatch.principal;

import br.com.screenmatch.model.DadosEpisodio;
import br.com.screenmatch.model.DadosSerie;
import br.com.screenmatch.model.DadosTemporada;
import br.com.screenmatch.model.Episodio;
import br.com.screenmatch.service.ConsumoApi;
import br.com.screenmatch.service.ConverteDados;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private  ConverteDados conversor = new ConverteDados();

    // Constantes são declaradas tudo maiusculo
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=8c307fc3";

    public void exibeMenu(){
        System.out.println("Digite o nome da série para buscar");
        var nomeSerie = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dados);

        List<DadosTemporada> temporadas = new ArrayList<>();

		for (int i = 1; i <= dados.totalTemporadas(); i++){
			json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + "&season=" + i + API_KEY);
			DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}
		temporadas.forEach(System.out::println);

//        for (int i = 0; i < dados.totalTemporadas(); i++){
//            List<DadosEpisodio> episodiosTemporada = temporadas.get(i).episodios();
//            for (int j = 0; j < episodiosTemporada.size(); j++){
//                System.out.println(episodiosTemporada.get(j).titulo());
//            }
//        }

        // Versão resumida do código acima
        // Utilizando lambda "->"
        System.out.println("\n\nLista de episódios");
        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

        // stream permite iterar nas coleções de dados,
        // flatMap mapeia outro array dentro
        // Collectors.toList() pega os dados do map e joga na lista
        List<DadosEpisodio> dadosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .collect(Collectors.toList());

        // Peek serve para poder averiguar as etapas intermediarias do stream
        System.out.println("\n\nTop 10 episódios");
        dadosEpisodios.stream()
                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
                //.peek(e -> System.out.println("Primeiro filtro(N/A) " + e))
                // Passa o comparator como paramentro para especificar o que comparar do Json
                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
                //.peek(e -> System.out.println("Ordenação " + e))
                .limit(10)
                //.peek(e -> System.out.println("Limite " + e))
                .map(e -> e.titulo().toUpperCase() + " - " + e.avaliacao())
                //.peek(e -> System.out.println("Mapeamento " + e))
                .forEach(System.out::println);

        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.numero(), d))
                ).collect(Collectors.toList());

        System.out.println("\n\nObjetos episódio com temporada");
        episodios.forEach(System.out::println);

        // Pesquisa de episódio
        System.out.println("Digite um trecho do titulo do episódio");
        var trechoTitulo = leitura.nextLine();
        Optional<Episodio> episodioBuscado = episodios.stream()
                .filter(e -> e.getTitulo().toUpperCase().contains(trechoTitulo.toUpperCase()))
                .findFirst();
        if(episodioBuscado.isPresent()){
            System.out.println("Episódio encontrado!");
            System.out.println("Temporada: " + episodioBuscado.get().getTemporada() +
                                " - " + episodioBuscado.get().getTitulo());
        } else {
            System.out.println("Episódio não encontrado!");
        }

        System.out.println("A partir de que ano você deseha ver os episódios? ");
        var ano = leitura.nextInt();
        leitura.nextLine();

        LocalDate dataBusca = LocalDate.of(ano, 1, 1);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        episodios.stream()
                .filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBusca))
                .forEach(e -> System.out.println(
                        "Temporada: " + e.getTemporada() +
                                " Episódio: " + e.getTitulo() +
                                " Data Lançamento: " + e.getDataLancamento().format(formatter)
                ));

        // Usando map para coletar info especidica
        Map<Integer, Double> avaliacoesPorTemporada = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.groupingBy(Episodio::getTemporada,
                        Collectors.averagingDouble(Episodio::getAvaliacao)));
        System.out.println(avaliacoesPorTemporada);

        // Classe para calcular estatisticas
        DoubleSummaryStatistics est = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.summarizingDouble(Episodio::getAvaliacao));
        System.out.println("Media: " + est.getAverage());
        System.out.println("Melhor Episodio: " + est.getMax());
        System.out.println("Pior Episodio: " + est.getMin());
        System.out.println("Quantidade: " + est.getCount());
    }
}
