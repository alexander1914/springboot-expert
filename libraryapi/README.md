# Docker via portgreSQL

    // PGADMIN
    docker run --name pgadmin4 -p 15432:80 -e PGADMIN_DEFAULT_EMAIL=admin@admin.com -e PGADMIN_DEFAULT_PASSWORD=admin dpage/pgadmin4

    // POSTGRESQL
    docker run --name librarydb -p 5432:5432 -e POSTGRES_PASSWORD=postgres -e POSTGRES_USER=postgres -e POSTGRES_DB=library postgres:18.6
    
    // NETWORK
    docker run --name librarydb -p 5432:5432 -e POSTGRES_PASSWORD=postgres -e POSTGRES_USER=postgres -e POSTGRES_DB=library --network library-network postgres:18.6

### Commands Dockers basics:

    docker run // start or create new container
    
    docker start name-container // start container
    
    docker ps // check all containers working

    docker stop name-container // stop the container

    docker container rm name-container // remove the container

    docker network create library-network // create a new network