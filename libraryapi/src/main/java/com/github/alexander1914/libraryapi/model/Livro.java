package com.github.alexander1914.libraryapi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "livro")
@Data
public class Livro {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "isbn", length = 20, nullable = false)
    private String isbn;

    @Column(name = "titulo", length = 150, nullable = false)
    private String titulo;

    @Column(name = "data_publicaco")
    private LocalDate dataPublicacao;

    @Enumerated(EnumType.STRING) /// to map an Enum
    @Column(name = "genero", length = 150, nullable = false)
    private GeneroLivro genero;

    @Column(name = "preco", precision = 18, scale = 2)/// a scale 2 with 18 precision
    private BigDecimal preco;

    @ManyToOne
    @JoinColumn(name = "id_autor")
    private Autor autor;
}
