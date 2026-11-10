package com.ger.biblioteca.biblioteca.Repository;

import com.ger.biblioteca.biblioteca.Model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivrosRepository 
    extends JpaRepository<Livro, Long> {
}