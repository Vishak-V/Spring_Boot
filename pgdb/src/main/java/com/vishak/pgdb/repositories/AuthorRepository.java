package com.vishak.pgdb.repositories;

import com.vishak.pgdb.domain.Author;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends CrudRepository<Author,Long> {

    Iterable<Author> ageLessThan(int i);


    @Query("SELECT a from Author a where a.age > ?1")
    Iterable<Author> findAuthorWithAgeGreaterThan(int i);
}
