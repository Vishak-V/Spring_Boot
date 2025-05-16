package com.vishak.pgdb;

import com.vishak.pgdb.domain.Author;
import com.vishak.pgdb.domain.Book;

public final class TestDataUtil {
    private TestDataUtil() {
    }

    public static Author createTestAuthorA() {
        return Author.builder()
                .id(1L)
                .name("Vishak")
                .age(25)
                .build();
    }

    public static Author createTestAuthorB() {
        return Author.builder()
                .id(2L)
                .name("VishakV")
                .age(26)
                .build();
    }

    public static Author createTestAuthorC() {
        return Author.builder()
                .id(3L)
                .name("VishakVik")
                .age(28)
                .build();
    }

    public static Book createTestBookA() {
        return  Book.builder()
                .isbn("123")
                .title("The Lord of the Rings")
                .authorId(1L)
                .build();
    }

    public static Book createTestBookB() {
        return  Book.builder()
                .isbn("1234")
                .title("The Lord of the Rings - 2")
                .authorId(1L)
                .build();
    }

    public static Book createTestBookC() {
        return  Book.builder()
                .isbn("12345")
                .title("The Lord of the Rings - 3")
                .authorId(1L)
                .build();
    }
}