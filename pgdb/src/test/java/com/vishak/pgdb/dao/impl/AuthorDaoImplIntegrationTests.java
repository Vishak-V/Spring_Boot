package com.vishak.pgdb.dao.impl;

import com.vishak.pgdb.TestDataUtil;
import com.vishak.pgdb.dao.AuthorDao;
import com.vishak.pgdb.domain.Author;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorDaoImplIntegrationTests {

    private AuthorDaoImpl underTest;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public AuthorDaoImplIntegrationTests(AuthorDaoImpl underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatAuthorCanBeCreatedAndRecalled() {

        Author author = TestDataUtil.createTestAuthorA();

        underTest.create(author);
        Optional<Author> result = underTest.findOne(author.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(author);


    }

    @Test
    public void testThatMultipleAuthorsCanBeCreatedAndRecalled() {

        Author authorA = TestDataUtil.createTestAuthorA();
        underTest.create(authorA);
        Author authorB = TestDataUtil.createTestAuthorB();
        underTest.create(authorB);
        Author authorC = TestDataUtil.createTestAuthorC();
        underTest.create(authorC);

        Iterable<Author> result = underTest.find();
        assertThat(result)
                .hasSize(3)
                .containsExactlyInAnyOrder(authorA, authorB, authorC);
    }

    @Test
    public void testThatAuthorCanBeUpdated(){
        Author author = TestDataUtil.createTestAuthorA();

        Long oldId = author.getId();
        Long newId = 7L;

        underTest.create(author);
        author.setName("UPDATED");
        author.setAge(42);
        author.setId(newId);
        underTest.update(oldId, author);

        Optional<Author> result = underTest.findOne(newId);

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("UPDATED");
        assertThat(result.get().getAge()).isEqualTo(42);
        assertThat(result.get().getId()).isEqualTo(7L);

    }

    @Test
    public void testThatAuthorCanBeDeleted(){
        Author authorA = TestDataUtil.createTestAuthorA();

        underTest.create(authorA);
        underTest.delete(authorA.getId());

        Optional<Author> result = underTest.findOne(authorA.getId());
        assertThat(result).isEmpty();
    }
}
