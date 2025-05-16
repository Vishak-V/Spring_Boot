package com.vishak.pgdb.dao.impl;

import com.vishak.pgdb.TestDataUtil;
import com.vishak.pgdb.domain.Author;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AuthorDaoImplTests {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private AuthorDaoImpl underTest;

    @Test
    public void testThatCreatesAuthorGeneratesCorrectSql() {
        Author author = TestDataUtil.createTestAuthorA();
        underTest.create(author);

        verify(jdbcTemplate).update(
                eq("INSERT INTO authors (id, name, age) VALUES (?, ?, ?)"),
                eq(1L), eq("Vishak"), eq(25)
        );
    }

    @Test
    public void testThatFindOneGeneratesCorrectSql() {
        underTest.findOne(1L);
        verify(jdbcTemplate).query(
                eq("SELECT id, name, age FROM authors WHERE id = ? LIMIT 1"),
                ArgumentMatchers.<AuthorDaoImpl.AuthorRowMapper>any(),
                eq(1L)
        );
    }

    @Test
    public void testThatFindManyGeneratesCorrectSql() {
        underTest.find();
        verify(jdbcTemplate).query(
                eq("SELECT id, name, age FROM authors"),
                ArgumentMatchers.<AuthorDaoImpl.AuthorRowMapper>any()
        );
    }

    @Test
    public void testThatUpdateAuthorGeneratesCorrectSql(){
        Author author = TestDataUtil.createTestAuthorA();
        Long id = 1L;
        author.setId(2L);
        underTest.update(id,author);

        verify(jdbcTemplate).update("UPDATE authors SET id = ?, name = ?, age = ? WHERE id = ?",
                2L,"Vishak",25,1L);
    }

    @Test
    public void testThatDeleteGeneratesTheCorrectSql(){
        underTest.delete(1L);

        verify(jdbcTemplate).update(
                "DELETE FROM authors WHERE id=?", 1L);

    }
}
