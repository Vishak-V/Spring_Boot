package com.vishak.pgdb;

import com.vishak.pgdb.domain.Entities.AuthorEntity;
import com.vishak.pgdb.domain.Entities.BookEntity;
import com.vishak.pgdb.domain.dto.AuthorDto;
import com.vishak.pgdb.domain.dto.BookDto;

public final class TestDataUtil {
    private TestDataUtil() {
    }

    public static AuthorEntity createTestAuthorA() {
        return AuthorEntity.builder()
                .name("Vishak")
                .age(25)
                .build();
    }

    public static AuthorEntity createTestAuthorB() {
        return AuthorEntity .builder()
                .name("VishakV")
                .age(26)
                .build();
    }

    public static AuthorEntity createTestAuthorC() {
        return AuthorEntity .builder()
                .name("VishakVik")
                .age(28)
                .build();
    }

    public static BookEntity createTestBookEntityA(final AuthorEntity author) {
        return  BookEntity.builder()
                .isbn("123")
                .title("The Lord of the Rings")
                .author(author)
                .build();
    }

    public static BookEntity createTestBookB(final AuthorEntity author) {
        return  BookEntity.builder()
                .isbn("1234")
                .title("The Lord of the Rings - 2")
                .author(author)
                .build();
    }

    public static BookEntity createTestBookC(final AuthorEntity author) {
        return  BookEntity.builder()
                .isbn("12345")
                .title("The Lord of the Rings - 3")
                .author(author)
                .build();
    }

    public static BookDto createTestBookDtoA(final AuthorDto author) {
        return  BookDto.builder()
                .isbn("123")
                .title("The Lord of the Rings")
                .author(author)
                .build();
    }

    public static AuthorDto createTestAuthorDtoA() {
        return AuthorDto.builder()
                .id(2L)
                .name("VishakVik")
                .age(28)
                .build();
    }
}