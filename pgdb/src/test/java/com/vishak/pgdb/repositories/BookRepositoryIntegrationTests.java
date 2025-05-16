package com.vishak.pgdb.repositories;

import com.vishak.pgdb.TestDataUtil;
import com.vishak.pgdb.domain.Author;
import com.vishak.pgdb.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
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
        Author author = TestDataUtil.createTestAuthorA();

        Book book = TestDataUtil.createTestBookA(author);

        underTest.save(book);

        Optional<Book> result = underTest.findById(book.getIsbn());

        assertThat(result).isPresent();
        assertThat(result.get().getIsbn()).isEqualTo(book.getIsbn());
        assertThat(result.get().getTitle()).isEqualTo(book.getTitle());

        assertThat(result.get().getAuthor().getName()).isEqualTo(book.getAuthor().getName());
        assertThat(result.get().getAuthor().getAge()).isEqualTo(book.getAuthor().getAge());


    }

    @Test
    public void testThatMultipleBooksCanBeRetrieved(){

        Author author = TestDataUtil.createTestAuthorA();
        authorRepository.save(author);
        Book bookA = TestDataUtil.createTestBookA(author);
        Book bookB = TestDataUtil.createTestBookB(author);
        Book bookC = TestDataUtil.createTestBookC(author);

        underTest.save(bookA);
        underTest.save(bookB);
        underTest.save(bookC);

        Iterable<Book> result = underTest.findAll();

        assertThat(result)
                .hasSize(3)
                .containsExactly(bookA,bookB,bookC);


    }

    @Test
    public void testThatBookCanBeUpdated(){
        Author authorA = TestDataUtil.createTestAuthorA();
        Author authorB = TestDataUtil.createTestAuthorB();

        authorRepository.save(authorA);
        authorRepository.save(authorB);


        Book bookA = TestDataUtil.createTestBookA(authorA);

        underTest.save(bookA);
        Book newBook = TestDataUtil.createTestBookB(authorB);
        newBook.setIsbn("Test-ISBN");

        underTest.save(newBook);

        Optional<Book> result = underTest.findById(newBook.getIsbn());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(newBook);


    }

    @Test
    public void testThatBookCanBeDeleted(){
        Author author = TestDataUtil.createTestAuthorA();
        authorRepository.save(author);
        Book book = TestDataUtil.createTestBookA(author);
        underTest.save(book);
        underTest.deleteById(book.getIsbn());
        Optional<Book> result = underTest.findById(book.getIsbn());
        assertThat(result).isEmpty();
    }


}
