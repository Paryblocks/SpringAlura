package br.com.screenmatch.model.serie;

// Enum é usado para criar uma lista de valores fixos
// Todos são constantes e limitam as opções, tornando mais seguro

public enum Categoria {
    ACAO("Action", "Ação"),
    ROMANCE("Romance", "Romance"),
    COMEDIA("Comedy", "Comédia"),
    DRAMA("Drama", "Drama"),
    CRIME("Crime", "Crime"),
    AVENTURA("Adventure", "Aventura"),
    FANTASIA("Fantasy", "Fantasia");

    private String categoriaOmdb;
    private String categoriaPesquisa;

    // Adicionar outros parametros no construtor mapeia a segunda
    // Coluna de opções acima, e assim por diante
    Categoria(String categoriaOmdb, String categoriaPesquisa){
        this.categoriaOmdb = categoriaOmdb;
        this.categoriaPesquisa = categoriaPesquisa;
    }

    // Metodos estaticos não requerem a criação de um objeto
    // Podendo ser acessados de qualquer lugar
    public static Categoria fromString(String text) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaOmdb.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para a string fornecida: " + text);
    }

    public static Categoria fromPesquisa(String text) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaPesquisa.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para a string fornecida: " + text);
    }
}
