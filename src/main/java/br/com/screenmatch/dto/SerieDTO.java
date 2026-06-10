package br.com.screenmatch.dto;

import br.com.screenmatch.model.serie.Categoria;

public record SerieDTO(
        Long id,
        String titulo,
        Integer totalTemporadas,
        double avaliacao,
        Categoria genero,
        String atores,
        String poster,
        String sinopse) {
}
