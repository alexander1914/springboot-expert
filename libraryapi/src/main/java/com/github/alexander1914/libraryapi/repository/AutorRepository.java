package com.github.alexander1914.libraryapi.repository;

import com.github.alexander1914.libraryapi.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/// its' optional extends JpaRepository
@Repository
public interface AutorRepository extends JpaRepository<Autor, UUID> {
}
