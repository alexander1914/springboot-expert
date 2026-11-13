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
    nacionalidade varchar(50) not null
    );
    
    CREATE TABLE livro(
    id uuid not null primary key,
    isbn varchar (20) not null,
    titulo varchar(150) not null,
    data_publicacao date not null,
    genero varchar(30) not null,
    preco numeric(18,2),
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

**@Transactional**: Let’s focus on a straightforward operation sequence such as retrieving a draft article, 
updating it, and saving the changes — all performed within a single database transaction.

**@ManyToOne**: is annotation lets us create bidirectional relationships too. 
We’ll cover this in detail in the next few subsections.

**Query Method**: is Spring Data JPA supports find, read, query, count and get. 
So, we could have done queryByName, and Spring Data would behave the same.

``` java
    List<Livro> findByAutor(Autor autor);
```