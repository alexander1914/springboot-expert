package com.github.alexander1914.libraryapi.service;

import com.github.alexander1914.libraryapi.model.Autor;
import com.github.alexander1914.libraryapi.model.GeneroLivro;
import com.github.alexander1914.libraryapi.model.Livro;
import com.github.alexander1914.libraryapi.repository.AutorRepository;
import com.github.alexander1914.libraryapi.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class TransacaoService {

    @Autowired
    private AutorRepository autorRepository;
    @Autowired
    private LivroRepository livroRepository;

    @Transactional
    public void executar() {
        /// First step to salve autor
        Autor autor = new Autor();
        autor.setNome("Nathalia");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1997, 12, 22));

        autorRepository.save(autor);

        /// Second step to salve livro
        Livro livro = new Livro();
        livro.setIsbn("01010-11111");
        livro.setPreco(BigDecimal.valueOf(500));
        livro.setGenero(GeneroLivro.FANTASIA);
        livro.setTitulo("Mágico de Oz");
        livro.setDataPublicacao(LocalDate.of(1980, 1, 2));

        livro.setAutor(autor);

        livroRepository.save(livro);

        if (autor.getNome().equals("Nathalia")) {
            throw new RuntimeException("Rollback");
        }
    }

    @Transactional
    public void atualizarSemAtualizar() {

        Livro livro = livroRepository
                .findById(UUID.fromString("8f10396d-06b7-48dd-9386-2b6bb3e17076"))
                .orElse(null);

        livro.setDataPublicacao(LocalDate.of(2026,12,24));
    }

    @Transactional
    public void rollback() {
        /// First step to salve autor
        Autor autor = new Autor();
        autor.setNome("Eliete");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1997, 12, 22));

        autorRepository.save(autor);

        /// Second step to salve livro
        Livro livro = new Livro();
        livro.setIsbn("01010-11111");
        livro.setPreco(BigDecimal.valueOf(500));
        livro.setGenero(GeneroLivro.FANTASIA);
        livro.setTitulo("Mágico de Oz");
        livro.setDataPublicacao(LocalDate.of(1980, 1, 2));

        livro.setAutor(autor);

        livroRepository.save(livro);

        if (autor.getNome().equals("Eliete")) {
            throw new RuntimeException("Rollback");
        }
    }
}
