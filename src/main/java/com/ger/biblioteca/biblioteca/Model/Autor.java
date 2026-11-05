package com.ger.biblioteca.biblioteca.Model;

import java.util.ArrayList;
import java.util.List;

public class Autor {
    private String nome;
    private String nacionalidade;
    private List<Long> livrosPublicados = new ArrayList<>();
    private Long id;

    public Autor() {
    }

    public Autor(String nome, String nacionalidade, List<Long> livrosPublicados) {
        this.nome = nome;
        this.nacionalidade = nacionalidade;
        this.livrosPublicados = livrosPublicados;
        this.id = null;
    }

    public List<Long> getLivrosPublicados() {
        return livrosPublicados;
    }

    public String getNome() {
        return nome;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setLivrosPublicados(List<Long> livrosPublicados) {
        this.livrosPublicados = livrosPublicados;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}