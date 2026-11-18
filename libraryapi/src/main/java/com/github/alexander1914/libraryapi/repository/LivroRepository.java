package com.github.alexander1914.libraryapi.repository;

import com.github.alexander1914.libraryapi.model.Autor;
import com.github.alexander1914.libraryapi.model.GeneroLivro;
import com.github.alexander1914.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * @see LivroRepositoryTest
 */

@Repository
public interface LivroRepository extends JpaRepository<Livro, UUID> {

    /// Query Methods

    // select * from livro where id_autor = id
    List<Livro> findByAutor(Autor autor);

    // select * from livro where titulo = titulo
    List<Livro> findByTitulo(String titulo);

    // select * from livro where isbn = isbn
    List<Livro> findByIsbn(String isbn);

    // select * from livro where titulo = titulo and preco = preco
    List<Livro> findByTituloAndPreco(String titulo, BigDecimal preco);

    // select * from livro where titulo = titulo or preco = preco
    List<Livro> findByTituloOrIsbn(String titulo, String isbn);

    // select * from livro where data_publicacao between ? and ?
    List<Livro> findByDataPublicacaoBetween(LocalDate inicio, LocalDate fim);

    // select * from livro where titulo like 'Abel'
    List<Livro> findByTituloLike(String titulo);

    // select * from livro where titulo = ? order by isbn desc
    List<Livro> findByTituloOrderByIsbnDesc(String titulo);

    // select l.* from livro as l order by l.titulo, l.preco
    @Query("select l from Livro as l order by l.titulo, l.preco")
    List<Livro> listarTodosOrdenadosPorTituloAndPreco();

    /**
     * select a.*
     * from livro l
     * join autor a on a.id = l.id_autor
     */
    @Query("select a from Livro l join l.autor a")
    List<Autor> listarAutoresDosLivros();

    // select distinct l.* from livro l
    @Query("select distinct l.titulo from Livro l")
    List<String> listarNomesDiferentesLivros();

    @Query("""
                select l.genero
                    from Livro l
                        join l.autor a 
                            where a.nacionalidade = 'Brasileira'
                                        order by l.genero
            """)
    List<String> listarGenerosAutoresBrasileiros();

    /// named parameters
    @Query("select l from Livro l where l.genero = :genero order by :paramOrderby")
    List<Livro> findByGenero(
            @Param("genero") GeneroLivro generoLivro,
            @Param("paramOrderby") String orderBy
    );

    /// positional parameters
    @Query("select l from Livro l where l.genero = ?1 order by ?2")
    List<Livro> findByGeneroParametroPosicianal(
            @Param("genero") GeneroLivro generoLivro,
            @Param("paramOrderby") String orderBy
    );
}
