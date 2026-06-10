package br.com.screenmatch.controller;

import br.com.screenmatch.dto.EpisodioDTO;
import br.com.screenmatch.dto.SerieDTO;
import br.com.screenmatch.repository.SerieRepository;
import br.com.screenmatch.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

// Indicar para o Spring que é controller
@RestController
// Mapeia padrão todas
@RequestMapping("/series")
public class SerieController {

    @Autowired
    private SerieService service;

    // Mapeia na porta o endereço que GET responde
    @GetMapping
    public List<SerieDTO> obterSeries() {
        return service.obterTodasAsSeries();
    }

    @GetMapping("/top5")
    public List<SerieDTO> obterTop5(){
        return service.obterTop5Series();
    }

    @GetMapping("/lancamentos")
    public List<SerieDTO> obterLancamentos(){
        return service.obterLancamentos();
    }

    @GetMapping("/{id}")
    public SerieDTO obterPorId(@PathVariable Long id){
        return service.obterPorId(id);
    }

    @GetMapping("/{id}/temporadas/todas")
    public List<EpisodioDTO> obterTodasTemporadas(@PathVariable Long id){
        return service.obterTodasTemporadas(id);
    }

    @GetMapping("/{id}/temporadas/{numero}")
    public List<EpisodioDTO> obterEpisodiosPorTemporada(@PathVariable Long id, @PathVariable Long numero){
        return service.obterEpisodiosPorTemporada(id, numero);
    }

    @GetMapping("/categoria/{categoria}")
    public List<SerieDTO> obterSeriesPorCategoria(@PathVariable String categoria){
        return service.obterSeriesPorCategoria(categoria);
    }
}
