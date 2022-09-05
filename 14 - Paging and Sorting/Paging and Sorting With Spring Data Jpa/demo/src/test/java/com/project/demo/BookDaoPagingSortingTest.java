package com.project.demo;

import com.project.demo.dao.BookDao;
import com.project.demo.dao.BookDaoImpl;
import com.project.demo.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({BookDaoImpl.class})
public class BookDaoPagingSortingTest {

    @Autowired
    BookDao bookDao;

    @Test
    void findAllBooksPage1_SortByTitle() {
        List<Book> books = bookDao.findAllBooksSortByTitle(PageRequest.of(0, 10,
                Sort.by(Sort.Order.desc("authorId"))));

        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(10);
    }

    @Test
    void findAllBooksPage1_pageable() {
        List<Book> books = bookDao.findAllBooks(PageRequest.of(0, 5));

        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(5);
    }

    @Test
    void findAllBooksPage2_pageable() {
        List<Book> books = bookDao.findAllBooks(PageRequest.of(1, 5));

        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(5);
    }

    @Test
    void findAllBooksPage3_pageable() {
        List<Book> books = bookDao.findAllBooks(PageRequest.of(4, 5));

        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(5);
    }

    @Test
    void findAllBooksPage1() {
        List<Book> books = bookDao.findAllBooks(5, 3);

        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(5);
    }

    @Test
    void findAllBooksPage2() {
        List<Book> books = bookDao.findAllBooks(10, 10);

        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(10);
    }

    @Test
    void findAllBooksPage10() {
        List<Book> books = bookDao.findAllBooks(10, 100);

        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(0);
    }

    @Test
    void testFindAllBooks() {
        List<Book> books = bookDao.findAllBooks();

        assertThat(books).isNotNull();
        assertThat(books.size()).isGreaterThan(5);
    }
}
