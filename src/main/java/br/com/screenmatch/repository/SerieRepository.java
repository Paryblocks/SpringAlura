package br.com.screenmatch.repository;

import br.com.screenmatch.model.serie.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

// Tem que dizer a classe que ele implementa e o tipo do ID (Nesse caso Long)
public interface SerieRepository extends JpaRepository<Serie, Long> {
}
