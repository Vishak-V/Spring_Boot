package com.vishak.pgdb.repositories;

import com.vishak.pgdb.domain.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<Book,String> {
}
