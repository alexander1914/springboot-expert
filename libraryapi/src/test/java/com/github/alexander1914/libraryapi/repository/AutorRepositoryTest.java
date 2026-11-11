package com.github.alexander1914.libraryapi.repository;

import com.github.alexander1914.libraryapi.model.Autor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository autorRepository;

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
}
