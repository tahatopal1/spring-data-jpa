package com.project.demo;

import com.project.demo.dao.BookDao;
import com.project.demo.model.Author;
import com.project.demo.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import static org.assertj.core.api.Assertions.assertThat;

@ComponentScan(basePackages = {"com.project.demo"})
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookDaoIntegrationTest {

    @Autowired
    private BookDao bookDao;

    @Test
    void testGetBook() {
        Book book = bookDao.getById(1L);
        assertThat(book).isNotNull();
    }

    @Test
    void testGetBookByTitle() {
        Book book = bookDao.findBookByTitle("Clean Code, Modern Edition");
        assertThat(book).isNotNull();
    }

    @Test
    void testSaveBook() {
        Book book = new Book("Clean Code, New Edition", "978-0134494198", "Addison Wesley");

        Author author = new Author();
        author.setId(3L);

        book.setAuthor(author);
        Book saved = bookDao.saveNewBook(book);

        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
    }

    @Test
    void testUpdateBook() {
        Book book = new Book("Clean Code, New Edition", "978-0134494198", "Addison Wesley");

        Author author = new Author();
        author.setId(3L);

        book.setAuthor(author);
        Book saved = bookDao.saveNewBook(book);

        saved.setTitle("Clean Code, Modern Edition");
        Book updated = bookDao.updateAuthor(saved);

        assertThat(updated.getTitle()).isEqualTo("Clean Code, Modern Edition");
    }

    @Test
    void testDeleteBook() {
        Book book = new Book("Clean Code, Critical Edition", "978-0134494500", "Addison Wesley");
        Book saved = bookDao.saveNewBook(book);

        bookDao.deleteAuthorById(saved.getId());
        Book deleted = bookDao.getById(saved.getId());
        assertThat(deleted).isNull();
    }
}
