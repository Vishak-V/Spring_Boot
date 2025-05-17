package com.vishak.pgdb.services;

import com.vishak.pgdb.domain.Entities.BookEntity;

import java.util.List;
import java.util.Optional;


public interface BookService {

    BookEntity createUpdateBook(String isbn, BookEntity bookEntity);

    List<BookEntity> findAll();

    Optional<BookEntity> findOne(String isbn);

    boolean isExists(String isbn);

    void delete(String isbn);
}
