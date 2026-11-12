package com.github.alexander1914.libraryapi.repository;

import com.github.alexander1914.libraryapi.model.Autor;
import com.github.alexander1914.libraryapi.model.GeneroLivro;
import com.github.alexander1914.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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

    @Test
    void salvarCascadeLivroTest(){
        Livro livro = new Livro();
        livro.setIsbn("90887-84874");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.BIOGRAFIA);
        livro.setTitulo("Outro Livro");
        livro.setDataPublicacao(LocalDate.of(1980,1,2));

        Autor autor = new Autor();
        autor.setNome("Nathalia");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1997, 12,22));

        /// Implemented a way cascade
        livro.setAutor(autor);

        livroRepository.save(livro);
    }

    @Test
    void atualizarAutorDoLivro(){
        UUID id = UUID.fromString("d18277bc-d6b1-4cdb-a988-d28bfd662de0");
        var livroParaAtualizar = livroRepository.findById(id).orElse(null);

        UUID idAutor = UUID.fromString("a5df7ee4-f248-4c65-8580-6c813a3d65e8");
        Autor autor = autorRepository.findById(idAutor).orElse(null);

        livroParaAtualizar.setAutor(autor);

        livroRepository.save(livroParaAtualizar);
    }

    @Test
    void deletarLivroPorId(){
        UUID id = UUID.fromString("d18277bc-d6b1-4cdb-a988-d28bfd662de0");
        livroRepository.deleteById(id);
    }

    @Test
    void deletarLivroPorCascade(){
        UUID id = UUID.fromString("d18277bc-d6b1-4cdb-a988-d28bfd662de0");
        livroRepository.deleteById(id);
    }

    @Test
    @Transactional
    void buscarLivroTest(){
        UUID id = UUID.fromString("d18277bc-d6b1-4cdb-a988-d28bfd662de0");
        Livro livro = livroRepository.findById(id).orElse(null);
        System.out.println("Livro: ");
        System.out.println(livro.getTitulo());

        System.out.println("Autor: ");
        System.out.println(livro.getAutor().getNome());
    }
}