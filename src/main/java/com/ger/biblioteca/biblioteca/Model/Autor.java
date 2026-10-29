package com.ger.biblioteca.biblioteca.Model;

public class Autor {
    private String nome;
    private String nacionalidade;
    private String obrasPublicadas;

    public Autor() {
    }

    public Autor(String nome, String nacionalidade, String obrasPublicadas) {
        this.nome = nome;
        this.nacionalidade = nacionalidade;
        this.obrasPublicadas = obrasPublicadas;
    }

    public String getObrasPublicadas() {
        return obrasPublicadas;
    }

    public String getNome() {
        return nome;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setObrasPublicadas(String obrasPublicadas) {
        this.obrasPublicadas = obrasPublicadas;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }
}