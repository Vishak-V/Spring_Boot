package com.vishak.pgdb.repositories;

import com.vishak.pgdb.TestDataUtil;
import com.vishak.pgdb.domain.Entities.AuthorEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorRepositoryIntegrationTests {

    private AuthorRepository underTest;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public AuthorRepositoryIntegrationTests(AuthorRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatAuthorCanBeCreatedAndRecalled() {

        AuthorEntity author = TestDataUtil.createTestAuthorA();

        underTest.save(author);
        Optional<AuthorEntity> result = underTest.findById(author.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(author);


    }

    @Test
    public void testThatMultipleAuthorsCanBeCreatedAndRecalled() {

        AuthorEntity authorA = TestDataUtil.createTestAuthorA();
        underTest.save(authorA);
        AuthorEntity authorB = TestDataUtil.createTestAuthorB();
        underTest.save(authorB);
        AuthorEntity authorC = TestDataUtil.createTestAuthorC();
        underTest.save(authorC);

        Iterable<AuthorEntity> result = underTest.findAll();
        assertThat(result)
                .hasSize(3)
                .containsExactly(authorA, authorB, authorC);
    }

    @Test
    public void testThatAuthorCanBeUpdated(){
        AuthorEntity author = TestDataUtil.createTestAuthorA();

        underTest.save(author);
        author.setName("UPDATED");
        author.setAge(42);
        underTest.save(author);

        Optional<AuthorEntity> result = underTest.findById(author.getId());

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("UPDATED");
        assertThat(result.get().getAge()).isEqualTo(42);

    }

    @Test
    public void testThatAuthorCanBeDeleted(){
        AuthorEntity authorA = TestDataUtil.createTestAuthorA();

        underTest.save(authorA);
        underTest.deleteById(authorA.getId());

        Optional<AuthorEntity> result = underTest.findById(authorA.getId());
        assertThat(result).isEmpty();
    }

    @Test
    public void testThatGetAuthorsWithAgeLessThan(){
        AuthorEntity authorA = TestDataUtil.createTestAuthorA();
        underTest.save(authorA);
        AuthorEntity authorB = TestDataUtil.createTestAuthorB();
        underTest.save(authorB);
        AuthorEntity authorC = TestDataUtil.createTestAuthorC();
        underTest.save(authorC);
        Iterable<AuthorEntity> result = underTest.ageLessThan(27);

        assertThat(result).hasSize(2);

    }

    @Test
    public void getAuthorsWithAgeGreaterThan(){

        AuthorEntity authorA = TestDataUtil.createTestAuthorA();
        underTest.save(authorA);
        AuthorEntity authorB = TestDataUtil.createTestAuthorB();
        underTest.save(authorB);
        AuthorEntity authorC = TestDataUtil.createTestAuthorC();
        underTest.save(authorC);
        Iterable<AuthorEntity> result = underTest.findAuthorWithAgeGreaterThan(27);

        assertThat(result).hasSize(1);

    }


}
