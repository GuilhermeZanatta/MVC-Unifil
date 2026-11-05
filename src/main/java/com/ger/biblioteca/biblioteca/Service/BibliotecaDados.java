package com.ger.biblioteca.biblioteca.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ger.biblioteca.biblioteca.Model.Autor;
import com.ger.biblioteca.biblioteca.Model.Livro;

@Component
public class BibliotecaDados {

    private final List<Autor> autores = new ArrayList<>();
    private final List<Livro> livros = new ArrayList<>();
    private long proximoIdAutor = 0;
    private long proximoIdLivro = 0;

    public List<Autor> getAutores() {
        return autores;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public long proximoIdAutor() {
        return proximoIdAutor++;
    }

    public long proximoIdLivro() {
        return proximoIdLivro++;
    }
}
