package com.github.alexander1914.libraryapi.repository;

import com.github.alexander1914.libraryapi.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository /// its' optional extends JpaRepository
public interface AutorRepository extends JpaRepository<Autor, UUID> {
}
