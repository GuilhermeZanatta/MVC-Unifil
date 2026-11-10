package com.ger.biblioteca.biblioteca.Repository;

import com.ger.biblioteca.biblioteca.Model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutoresRepository 
    extends JpaRepository<Autor, Long> {

}
