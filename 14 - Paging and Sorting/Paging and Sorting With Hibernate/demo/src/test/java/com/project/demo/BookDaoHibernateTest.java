package com.project.demo;

import com.project.demo.dao.BookDao;
import com.project.demo.dao.BookDaoHibernate;
import com.project.demo.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityManagerFactory;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookDaoHibernateTest {

    @Autowired
    EntityManagerFactory emf;

    BookDao bookDao;

    @BeforeEach
    void setUp(){
        bookDao = new BookDaoHibernate(emf);
    }

    @Test
    void testFindAllBooks() {
        List<Book> books = bookDao.findAllBooks(PageRequest.of(0, 10));
        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(10);
    }

    @Test
    void findAllBooksSortByTitle() {
        List<Book> books
                = bookDao.findAllBooksSortByTitle(PageRequest.of(0, 10, Sort.by(Sort.Order.desc("title"))));
        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(10);
    }
}
