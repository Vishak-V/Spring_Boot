package com.vishak.pgdb;

import com.vishak.pgdb.domain.Author;
import com.vishak.pgdb.domain.Book;

public final class TestDataUtil {
    private TestDataUtil() {
    }

    public static Author createTestAuthorA() {
        return Author.builder()
                .name("Vishak")
                .age(25)
                .build();
    }

    public static Author createTestAuthorB() {
        return Author.builder()
                .name("VishakV")
                .age(26)
                .build();
    }

    public static Author createTestAuthorC() {
        return Author.builder()
                .name("VishakVik")
                .age(28)
                .build();
    }

    public static Book createTestBookA(final Author author) {
        return  Book.builder()
                .isbn("123")
                .title("The Lord of the Rings")
                .author(author)
                .build();
    }

    public static Book createTestBookB(final Author author) {
        return  Book.builder()
                .isbn("1234")
                .title("The Lord of the Rings - 2")
                .author(author)
                .build();
    }

    public static Book createTestBookC(final Author author) {
        return  Book.builder()
                .isbn("12345")
                .title("The Lord of the Rings - 3")
                .author(author)
                .build();
    }
}