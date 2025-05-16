package com.vishak.pgdb.dao.impl;

import com.vishak.pgdb.TestDataUtil;
import com.vishak.pgdb.dao.AuthorDao;
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
public class BookDaoImplIntegrationTests {

    private BookDaoImpl underTest;
    private AuthorDao authorDao;


    @Autowired
    public BookDaoImplIntegrationTests(BookDaoImpl underTest,AuthorDao  authorDao) {
        this.underTest = underTest;
        this.authorDao = authorDao;
    }

    @Test
    public void testThatBookCanBeRetrieved(){
            Book book = TestDataUtil.createTestBookA();

            Author author = TestDataUtil.createTestAuthorA();
            authorDao.create(author);
            book.setAuthorId(author.getId());
            underTest.create(book);

            Optional<Book> result = underTest.findOne(book.getIsbn());

            assertThat(result).isPresent();
            assertThat(result.get()).isEqualTo(book);

    }

    @Test
    public void testThatMultipleBooksCanBeRetrieved(){
        Book bookA = TestDataUtil.createTestBookA();
        Book bookB = TestDataUtil.createTestBookB();
        Book bookC = TestDataUtil.createTestBookC();

        Author author = TestDataUtil.createTestAuthorA();

        authorDao.create(author);

        bookA.setAuthorId(author.getId());
        bookB.setAuthorId(author.getId());
        bookC.setAuthorId(author.getId());

        underTest.create(bookA);
        underTest.create(bookB);
        underTest.create(bookC);

        List<Book> result = underTest.findMany();

        assertThat(result)
                .hasSize(3)
                .containsExactly(bookA,bookB,bookC);


    }

    @Test
    public void testThatBookCanBeUpdated(){
        Author authorA = TestDataUtil.createTestAuthorA();
        Author authorB = TestDataUtil.createTestAuthorB();

        authorDao.create(authorA);
        authorDao.create(authorB);


        Book bookA = TestDataUtil.createTestBookA();
        bookA.setAuthorId(authorA.getId());
        underTest.create(bookA);
        Book newBook = TestDataUtil.createTestBookB();
        newBook.setIsbn("Test-ISBN");
        newBook.setAuthorId(authorB.getId());

        underTest.update(bookA.getIsbn(),newBook);

        Optional<Book> result = underTest.findOne(newBook.getIsbn());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(newBook);


    }

    @Test
    public void testThatBookCanBeDeleted(){
        Author author = TestDataUtil.createTestAuthorA();
        authorDao.create(author);
        Book book = TestDataUtil.createTestBookA();
        book.setAuthorId(author.getId());
        underTest.create(book);
        underTest.delete(book.getIsbn());
        Optional<Book> result = underTest.findOne(book.getIsbn());
        assertThat(result).isEmpty();
    }


}
