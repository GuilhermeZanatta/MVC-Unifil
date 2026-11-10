package com.ger.biblioteca.biblioteca.Repository;

import com.ger.biblioteca.biblioteca.Model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriasRepository 
    extends JpaRepository<Categoria, Long> {

}
