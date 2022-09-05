package com.project.demo;

import com.project.demo.dao.BookDao;
import com.project.demo.dao.BookDaoJDBCTemplate;
import com.project.demo.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookDaoJDBCTemplateTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    BookDao bookDao;

    @BeforeEach
    void setUp(){
        bookDao = new BookDaoJDBCTemplate(jdbcTemplate);
    }

    @Test
    void findAllBooksPage1_SortByTitle() {
        List<Book> books
                = bookDao.findAllBooksSortByTitle(PageRequest.of(0, 10, Sort.by(Sort.Order.desc("title").getDirection().name())));
        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(10);

    }

    @Test
    void findAllBooksPage1_pageable() {
        List<Book> books = bookDao.findAllBooks(PageRequest.of(0, 5));
        assertThat(books).isNotNull();
    }

    @Test
    void findAllBooksPage2_pageable() {
        List<Book> books = bookDao.findAllBooks(PageRequest.of(1, 5));
        assertThat(books).isNotNull();
    }

    @Test
    void findAllBooksPage3_pageable() {
        List<Book> books = bookDao.findAllBooks(PageRequest.of(2, 5));
        assertThat(books).isNotNull();
    }

    @Test
    void findAllBooksPage1() {
        List<Book> books = bookDao.findAllBooks(5, 3);
        assertThat(books).isNotNull();
    }

    @Test
    void findAllBooksPage2() {
        List<Book> books = bookDao.findAllBooks(5, 5);
        assertThat(books).isNotNull();
    }

    @Test
    void findAllBooksPage3() {
        List<Book> books = bookDao.findAllBooks(27, 55);
        assertThat(books).isNotNull();
    }

    @Test
    void testGetById(){
        Book book = bookDao.getById(3L);
        assertThat(book).isNotNull();
    }

    @Test
    void testFindAllBooks() {
        List<Book> books = bookDao.findAllBooks();
        assertThat(books).isNotNull();
        assertThat(books.size()).isGreaterThan(0);
    }

    @Test
    void testFindBookByTitle() {
        Book book = bookDao.findBookByTitle("Clean Code");
        assertThat(book).isNotNull();
    }

    @Test
    void testSaveNewBook() {
        Book book = new Book();
        book.setIsbn("1234");
        book.setPublisher("Self");
        book.setTitle("My Book");
        book.setAuthorId(1L);

        Book saved = bookDao.saveNewBook(book);
        assertThat(saved).isNotNull();
    }

    @Test
    void testUpdateBook() {
        Book book = new Book();
        book.setIsbn("2243");
        book.setPublisher("Self");
        book.setTitle("My Book 2");
        book.setAuthorId(1L);
        Book saved = bookDao.saveNewBook(book);

        saved.setTitle("New Book");
        bookDao.updateBook(saved);

        Book fetched = bookDao.getById(saved.getId());
        assertThat(fetched.getTitle()).isEqualTo("New Book");

    }

    @Test
    void testDeleteBookById() {
        Book book = new Book();
        book.setIsbn("2243");
        book.setPublisher("Self");
        book.setTitle("My Book 2");
        book.setAuthorId(1L);
        Book saved = bookDao.saveNewBook(book);

        bookDao.deleteBookById(saved.getId());

        assertThrows(EmptyResultDataAccessException.class, () -> {
            bookDao.getById(saved.getId());
        });

    }
}
