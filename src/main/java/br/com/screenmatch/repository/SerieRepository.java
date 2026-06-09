package br.com.screenmatch.repository;

import br.com.screenmatch.model.serie.Categoria;
import br.com.screenmatch.model.serie.Episodio;
import br.com.screenmatch.model.serie.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

// Tem que dizer a classe que ele implementa e o tipo do ID (Nesse caso Long)
public interface SerieRepository extends JpaRepository<Serie, Long> {
    // derived queries permitem criar queries personalizadas usando
    // palavras do Java, como nesse caso, contains e ignoreCase
    Optional<Serie> findByTituloContainingIgnoreCase(String nomeSerie);

    List<Serie> findByAtoresContainingIgnoreCase(String nomeAtor);

    List<Serie> findTop5ByOrderByAvaliacaoDesc();

    List<Serie> findByGenero(Categoria genero);

    // AndAvaliacaoGreaterThanEqual -> concatenar maior ou igual
    // a um valor dentro da query, tem q passar o valor no metodo
    List<Serie> findByTotalTemporadasLessThanEqualAndAvaliacaoGreaterThanEqual(String maxTemporadas, String minAvaliacao);

    // Permite atribuir uma query a um metodo
    //Query(value = "SELECT * FROM series WHERE series.total_temporadas <= 5 AND series.avaliacao >= 7.5", nativeQuery = true)

    //JPQL -> similar a query porem substitui pelas entidades da classe
    @Query("SELECT s FROM Serie s WHERE s.totalTemporadas <= :maxTemporadas AND s.avaliacao >= :minAvaliacao")
    List<Serie> seriesPorTemporadaEAvaliacao(String maxTemporadas, String minAvaliacao);

    // ILIKE é LIKE com IGNORECASE
    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE e.titulo ILIKE %:trechoEpisodio%")
    List<Episodio> episodiosPorTecho(String trechoEpisodio);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s = :serie ORDER BY e.avaliacao DESC LIMIT 5")
    List<Episodio> topEpisodiosPorSerie(Serie serie);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s = :serie AND YEAR(e.dataLancamento) >= :anoLancamento")
    List<Episodio> episodiosPorSerieEAno(Serie serie, int anoLancamento);
}
