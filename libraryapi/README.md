# Docker via PostgreSQL

``` dockerfile
    // PGADMIN
    docker run --name pgadmin4 -p 15432:80 -e PGADMIN_DEFAULT_EMAIL=admin@admin.com -e PGADMIN_DEFAULT_PASSWORD=admin dpage/pgadmin4

    // POSTGRESQL
    docker run --name librarydb -p 5432:5432 -e POSTGRES_PASSWORD=postgres -e POSTGRES_USER=postgres -e POSTGRES_DB=library postgres:18.6
    
    // NETWORK
    docker run --name librarydb -p 5432:5432 -e POSTGRES_PASSWORD=postgres -e POSTGRES_USER=postgres -e POSTGRES_DB=library --network library-network postgres:18.6
    
    // BASICS COMMANDS
    docker run // start or create new container
    
    docker start name-container // start container
    
    docker ps // check all containers working

    docker stop name-container // stop the container

    docker container rm name-container // remove the container

    docker network create library-network // create a new network
```

### Scripts SQL

``` sql
    CREATE TABLE autor(
        id uuid not null primary key,
        nome varchar(100) not null,
        data_nascimento date not null,
        nacionalidade varchar(50) not null,
        data_cadastro timestamp,
        data_atualizacao timestamp,
        id_usuario uuid
    );
    
    CREATE TABLE livro(
        id uuid not null primary key,
        isbn varchar (20) not null,
        titulo varchar(150) not null,
        data_publicacao date not null,
        genero varchar(30) not null,
        preco numeric(18,2),
        data_cadastro timestamp,
        data_atualizacao timestamp,
        id_usuario uuid
        id_autor uuid not null references autor(id),
        CONSTRAINT chk_genero check (genero in ('FICCAO', 'FANTASIA', 'MISTERIO', 'ROMANCE', 'BIOGRAFIA', 'CIENCIA'))
    );
```

### JPA

**CASCADE**: Entity relationships often depend on the existence of another entity,
for example the Person–Address relationship. Without the Person, the Address entity doesn’t have any meaning of its own.
When we delete the Person entity, our Address entity should also get deleted.
All JPA-specific cascade operations are represented by the jakarta.persistence.CascadeType enum containing entries:

    ALL
    PERSIST
    MERGE
    REMOVE
    REFRESH
    DETACH

**@Transient**: is annotation when saving to a database. However,
the @Transient annotation does not affect Java object serialization.

**@ManyToOne**: is annotation lets us create bidirectional relationships too.
We’ll cover this in detail in the next few subsections.

**Query Method**: is Spring Data JPA supports find, read, query, count and get.
So, we could have done queryByName, and Spring Data would behave the same.

``` java
    List<Livro> findByAutor(Autor autor);
```

https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html

**JQPL:** is used to define searches against persistent entities independent of the mechanism
used to store those entities.
As such, JPQL is "portable", and not constrained to any particular data store

**@Query:** annotation rather than annotating them to the domain class.
This frees the domain class from persistence specific information and co-locates the query to the repository interface.

``` java
@Query("""
select l.genero
from Livro l
join l.autor a
where a.nacionalidade = 'Brasileira'
order by l.genero
""")
List<String> listarGenerosAutoresBrasileiros();
```

**@Param:** is a documentation comment tag used to describe a function or method parameter, its data type,
and its purpose.

````java
/// named parameters
@Query("select l from Livro l where l.genero = :genero order by :paramOrderby")
List<Livro> findByGenero(
        @Param("genero") GeneroLivro generoLivro,
        @Param("paramOrderby") String orderBy
);
````

```java
/// positional parameters
@Query("select l from Livro l where l.genero = ?1 order by ?2")
List<Livro> findByGeneroParametroPosicianal(
        @Param("genero") GeneroLivro generoLivro,
        @Param("paramOrderby") String orderBy
);
```

**@Modifying:** is annotation used for database queries that alter data or schema.

**@Transactional**  is an annotation that defines a transaction boundary around a method or class.

```java
/// delete with @Query
@Modifying
@Transactional
@Query("delete from Livro where genero = ?1")
void deletePorGenero(GeneroLivro generoLivro);

/// update with @Query
@Modifying
@Transactional
@Query("update Livro set dataPublicacao = ?1")
void updateDataPublicao(LocalDate novaData);
```

## Transactional on JPA

**@Transactional**: Let’s focus on a straightforward operation sequence such as retrieving a draft article,
updating it, and saving the changes — all performed within a single database transaction.

```java
/// insert on database
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

/// update on database
/// when you have a transaction open didn't necessary to save entity on JPA 
/// Status managed
@Transactional
public void atualizarSemAtualizar() {

    Livro livro = livroRepository
            .findById(UUID.fromString("8f10396d-06b7-48dd-9386-2b6bb3e17076"))
            .orElse(null);

    livro.setDataPublicacao(LocalDate.of(2026, 12, 24));
}
```

**@CreatedDate:** is an annotation (org.springframework.data.annotation.CreatedDate) that marks a field or
method to record the date and time when an entity is created.

**@LastModifiedDate:** is an annotation (org.springframework.data.annotation.LastModifiedDate), @LastModifiedDate is an
auditing annotation
that marks a field or method to store the timestamp of when an entity was most recently modified.

**@EntityListeners:** is an annotation that specifies external listener classes to run callback methods during entity
lifecycle events.

**@EnableJpaAuditing:** is an annotation that activates automatic tracking of entity creation and modification metadata
(timestamps and user principals).

### RESTFul

is an acronym for Representational State Transfer and an architectural style (a named set of constraints) for
distributed hypermedia systems (a system of linked documents spread over a network).
Roy Fielding first presented it in 2000 in his famous dissertation.
Since then, it has become one of the most widely used approaches for building web-based APIs (Application Programming
Interfaces).

**@RestController:** is annotation in order to simplify the creation of RESTful web services. It’s a convenient
annotation that combines **@Controller** and **@ResponseBody**, which eliminates the need to annotate every request
handling method of the controller
class with the **@ResponseBody** annotation.

**@RequestMapping:** is annotation to map requests to controllers methods. It has various attributes to match by URL,
HTTP method, request parameters, headers, and media types. You can use it at the class level to express shared mappings
or at the method level to narrow down to a specific endpoint mapping.

**@PostMapping:** are annotations used to handle HTTP requests in Spring MVC controllers.
They help map specific HTTP methods to controller methods when building REST APIs.
