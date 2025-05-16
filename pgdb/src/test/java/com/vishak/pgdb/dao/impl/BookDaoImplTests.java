package com.vishak.pgdb.dao.impl;

import com.vishak.pgdb.TestDataUtil;
import com.vishak.pgdb.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BookDaoImplTests {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private BookDaoImpl underTest;


    @Test
    public void testCreate() {
        Book book = TestDataUtil.createTestBookA();

        underTest.create(book);

        verify(jdbcTemplate).update(
                eq("INSERT INTO books (isbn, title, author_id) VALUES (?,?,?)"),
                eq("123"),
                eq("The Lord of the Rings"),
                eq(1L)
        );
    }

    @Test
    public void testThatFindBookGeneratesCorrectSql() {
        underTest.findOne("0-380-97346-4");
        verify(jdbcTemplate).query(
                eq("SELECT isbn, title, author_id FROM books WHERE isbn = ? LIMIT 1"),
                ArgumentMatchers.<BookDaoImpl.BookRowMapper>any(),
                eq("0-380-97346-4")
        );
    }

    @Test
    public void testThatFindsMultipleBooks(){
        underTest.findMany();
        verify(jdbcTemplate).query(
                eq("SELECT isbn, title, author_id FROM books"),
                ArgumentMatchers.<BookDaoImpl.BookRowMapper>any()

        );
    }

    @Test
    public  void testThatUpdateBookGeneratesCorrectSql(){
        Book book = TestDataUtil.createTestBookA();

        underTest.update("123",book);
        verify(jdbcTemplate).update(
                "UPDATE books set isbn = ?, title = ?, author_id= ? WHERE isbn = ?  ",
                        book.getIsbn(),book.getTitle(),book.getAuthorId(),"123"
        );

    }

    @Test
    public void testThatDeleteBookGeneratesCorrectSql(){
        String isbn = "123";
        underTest.delete(isbn);

        verify(jdbcTemplate).update(
                "DELETE FROM books WHERE isbn = ?", isbn);


    }
}
