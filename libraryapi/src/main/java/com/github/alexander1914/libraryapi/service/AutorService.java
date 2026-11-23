package com.github.alexander1914.libraryapi.service;

import com.github.alexander1914.libraryapi.model.Autor;
import com.github.alexander1914.libraryapi.repository.AutorRepository;
import org.springframework.stereotype.Service;

@Service
public class AutorService {

    private final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public Autor salvarAutor(Autor autor) {
        return autorRepository.save(autor);
    }
}
