package com.vishak.pgdb.dao;

import com.vishak.pgdb.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {
    void create(Book book);

    Optional<Book> findOne(String isbn);

    List<Book> findMany();
}
