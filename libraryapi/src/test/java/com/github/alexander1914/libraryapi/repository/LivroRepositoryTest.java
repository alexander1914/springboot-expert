package com.github.alexander1914.libraryapi.repository;

import com.github.alexander1914.libraryapi.model.Autor;
import com.github.alexander1914.libraryapi.model.GeneroLivro;
import com.github.alexander1914.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@SpringBootTest
class LivroRepositoryTest {

    @Autowired
    LivroRepository livroRepository;

    @Autowired
    AutorRepository autorRepository;

    @Test
    void salvarLivroTest(){
        Livro livro = new Livro();
        livro.setIsbn("90887-84874");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("UFO");
        livro.setDataPublicacao(LocalDate.of(1980,1,2));

        Autor autor = autorRepository
                .findById(UUID.fromString("17df7d69-6ef6-452e-b7ee-e26bcf73b568"))
                .orElse(null);
        livro.setAutor(autor);

        livroRepository.save(livro);
    }
}