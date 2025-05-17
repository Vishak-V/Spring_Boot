package com.vishak.pgdb.repositories;

import com.vishak.pgdb.TestDataUtil;
import com.vishak.pgdb.domain.Entities.AuthorEntity;
import com.vishak.pgdb.domain.Entities.BookEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookRepositoryIntegrationTests {

    private BookRepository underTest;
    private AuthorRepository authorRepository;

    @Autowired
    public BookRepositoryIntegrationTests(BookRepository underTest,AuthorRepository  authorRepository) {
        this.underTest = underTest;
        this.authorRepository = authorRepository;
    }

    @Test
    public void testThatBookCanBeRetrieved(){
        AuthorEntity author = TestDataUtil.createTestAuthorA();

        BookEntity book= TestDataUtil.createTestBookEntityA(author);

        underTest.save(book);

        Optional<BookEntity> result = underTest.findById(book.getIsbn());

        assertThat(result).isPresent();
        assertThat(result.get().getIsbn()).isEqualTo(book.getIsbn());
        assertThat(result.get().getTitle()).isEqualTo(book.getTitle());

        assertThat(result.get().getAuthor().getName()).isEqualTo(book.getAuthor().getName());
        assertThat(result.get().getAuthor().getAge()).isEqualTo(book.getAuthor().getAge());


    }

    @Test
    public void testThatMultipleBooksCanBeRetrieved(){

        AuthorEntity author = TestDataUtil.createTestAuthorA();
        authorRepository.save(author);
        BookEntity bookA = TestDataUtil.createTestBookEntityA(author);
        BookEntity bookB = TestDataUtil.createTestBookB(author);
        BookEntity bookC = TestDataUtil.createTestBookC(author);

        underTest.save(bookA);
        underTest.save(bookB);
        underTest.save(bookC);

        Iterable<BookEntity> result = underTest.findAll();

        assertThat(result)
                .hasSize(3)
                .containsExactly(bookA,bookB,bookC);


    }

    @Test
    public void testThatBookCanBeUpdated(){
        AuthorEntity authorA = TestDataUtil.createTestAuthorA();
        AuthorEntity authorB = TestDataUtil.createTestAuthorB();

        authorRepository.save(authorA);
        authorRepository.save(authorB);


        BookEntity bookA = TestDataUtil.createTestBookEntityA(authorA);

        underTest.save(bookA);
        BookEntity newBook = TestDataUtil.createTestBookB(authorB);
        newBook.setIsbn("Test-ISBN");

        underTest.save(newBook);

        Optional<BookEntity> result = underTest.findById(newBook.getIsbn());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(newBook);


    }

    @Test
    public void testThatBookCanBeDeleted(){
        AuthorEntity author= TestDataUtil.createTestAuthorA();
        authorRepository.save(author);
        BookEntity book = TestDataUtil.createTestBookEntityA(author);
        underTest.save(book);
        underTest.deleteById(book.getIsbn());
        Optional<BookEntity> result = underTest.findById(book.getIsbn());
        assertThat(result).isEmpty();
    }




}
