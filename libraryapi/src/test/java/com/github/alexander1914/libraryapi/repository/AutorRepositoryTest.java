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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository autorRepository;

    @Autowired
    LivroRepository livroRepository;

    @Test
    public void salvarAutorTest(){
        Autor autor = new Autor();
        autor.setNome("Alexander");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(1990, 7,24));

        var autorSalvo = autorRepository.save(autor);
        System.out.print("Autor Salvo: " + autorSalvo);
    }

    @Test
    public void atualizaAutorTest(){
    var id = UUID.fromString("17df7d69-6ef6-452e-b7ee-e26bcf73b568");

        Optional<Autor> possivelAutor = autorRepository.findById(id);

        if (possivelAutor.isPresent()){
            Autor autorEncontrado = possivelAutor.get();

            System.out.println("Dados do Autor:");
            System.out.println(possivelAutor.get());

            autorEncontrado.setDataNascimento(LocalDate.of(1980, 1,1));
            autorEncontrado.setNacionalidade("Australiano");
            autorEncontrado.setNome("Charles");

            autorRepository.save(autorEncontrado);
        }
    }

    @Test
    public void listarAutores(){
        List<Autor> listaAutores = autorRepository.findAll();
        listaAutores.forEach(System.out::println);
    }

    @Test
    public void contarAutores(){
        System.out.println("Contagem de autores: " + autorRepository.count());
    }

    @Test
    public void deletarAutorPorId(){
        var id = UUID.fromString("17df7d69-6ef6-452e-b7ee-e26bcf73b568");
        autorRepository.deleteById(id);
    }

    @Test
    public void deletarAutor(){
        var id = UUID.fromString("17df7d69-6ef6-452e-b7ee-e26bcf73b568");
        var alexander = autorRepository.findById(id).get();
        autorRepository.delete(alexander);
    }

    @Test
    void salvarAutorComLivrosTest(){
        Autor autor2 = new Autor();
        autor2.setNome("Antônio");
        autor2.setNacionalidade("Americana");
        autor2.setDataNascimento(LocalDate.of(1980, 2,12));

        Livro livro = new Livro();
        livro.setIsbn("80987-85878");
        livro.setPreco(BigDecimal.valueOf(204));
        livro.setGenero(GeneroLivro.ROMANCE);
        livro.setTitulo("Kama Sutra");
        livro.setDataPublicacao(LocalDate.of(1999,1,2));
        livro.setAutor(autor2);

        Livro livro2 = new Livro();
        livro2.setIsbn("80987-85878");
        livro2.setPreco(BigDecimal.valueOf(305));
        livro2.setGenero(GeneroLivro.BIOGRAFIA);
        livro2.setTitulo("Abel Ferreira");
        livro2.setDataPublicacao(LocalDate.of(2021,1,2));
        livro2.setAutor(autor2);

        autor2.setLivros(new ArrayList<>());
        autor2.getLivros().add(livro);
        autor2.getLivros().add(livro2);

        autorRepository.save(autor2);

        livroRepository.saveAll(autor2.getLivros());
    }

    @Test
    @Transactional
    void listarLivrosAutorTest(){
        UUID id = UUID.fromString("206d8415-5f4a-41bd-9f97-7b2c7688e39b");
        var autor = autorRepository.findById(id).get();

        /// to find books of autor
        List<Livro> livrosLista =  livroRepository.findByAutor(autor);
        autor.setLivros(livrosLista);

        autor.getLivros().forEach(System.out::println);
    }
}
