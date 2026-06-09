package br.com.screenmatch.repository;

import br.com.screenmatch.model.serie.Categoria;
import br.com.screenmatch.model.serie.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

// Tem que dizer a classe que ele implementa e o tipo do ID (Nesse caso Long)
public interface SerieRepository extends JpaRepository<Serie, Long> {
    // derived queries permitem criar queries personalizadas usando
    // palavras do Java, como nesse caso, contains e ignoreCase
    Optional<Serie> findByTituloContainingIgnoreCase(String nomeSerie);

    // AndAvaliacaoGreaterThanEqual -> concatenar maior ou igual
    // a um valor dentro da query, tem q passar o valor no metodo
    List<Serie> findByAtoresContainingIgnoreCase(String nomeAtor);

    List<Serie> findTop5ByOrderByAvaliacaoDesc();

    List<Serie> findByGenero(Categoria genero);

    List<Serie> findByTotalTemporadasLessThanEqualAndAvaliacaoGreaterThanEqual(String maxTemporadas, String minAvaliacao);
}
