package com.vishak.pgdb.repositories;

import com.vishak.pgdb.domain.Entities.AuthorEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends CrudRepository<AuthorEntity,Long> {

    Iterable<AuthorEntity> ageLessThan(int i);


    @Query("SELECT a from AuthorEntity a where a.age > ?1")
    Iterable<AuthorEntity> findAuthorWithAgeGreaterThan(int i);
}
