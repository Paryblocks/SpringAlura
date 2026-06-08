package br.com.screenmatch.model.serie;

// Enum é usado para criar uma lista de valores fixos
// Todos são constantes e limitam as opções, tornando mais seguro

public enum Categoria {
    ACAO("Action"),
    ROMANCE("Romance"),
    COMEDIA("Comedy"),
    DRAMA("Drama"),
    CRIME("Crime"),
    AVENTURA("Adventure"),
    FANTASIA("Fantasy");

    private String categoriaOmdb;

    Categoria(String categoriaOmdb){
        this.categoriaOmdb = categoriaOmdb;
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
}
