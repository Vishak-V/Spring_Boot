package com.vishak.pgdb.controllers;


import com.vishak.pgdb.domain.Entities.BookEntity;
import com.vishak.pgdb.domain.dto.BookDto;
import com.vishak.pgdb.mappers.Mapper;
import com.vishak.pgdb.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class BookController {

    private Mapper<BookEntity,BookDto> bookMapper;
    private BookService bookService;

    public BookController(Mapper<BookEntity, BookDto> bookMapper, BookService bookService) {
        this.bookMapper = bookMapper;
        this.bookService = bookService;
    }

    @PutMapping("/books/{isbn}")
    public ResponseEntity<BookDto> createUpdateBook(@PathVariable("isbn") String isbn, @RequestBody BookDto bookDto) {
        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        boolean bookExists = bookService.isExists(isbn);
        BookEntity savedBookEntity = bookService.createUpdateBook(isbn, bookEntity);
        BookDto savebookDto = bookMapper.mapTo(savedBookEntity);
        if (bookExists) {
            return new ResponseEntity<>(savebookDto,HttpStatus.OK);
        } else {


            return new ResponseEntity<>(savebookDto, HttpStatus.CREATED);
        }
    }

    @GetMapping(path = "/books")
    public List<BookDto> listBooks(){
       List<BookEntity> bookEntities = bookService.findAll();

       return bookEntities.stream().map(bookMapper::mapTo).collect(Collectors.toList());
    }

    @GetMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> listBook(@PathVariable String isbn){
        Optional<BookEntity> foundBook = bookService.findOne(isbn);

        return foundBook.map(bookEntity -> {
            BookDto bookDto = bookMapper.mapTo(bookEntity);
            return new ResponseEntity<>(bookDto,HttpStatus.OK);
        }).orElse(new ResponseEntity<>(null,HttpStatus.NOT_FOUND));

    }

    @DeleteMapping(path = "/books/{isbn}")
    public ResponseEntity deleteBook(@PathVariable("isbn") String isbn){
        bookService.delete(isbn);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


}
