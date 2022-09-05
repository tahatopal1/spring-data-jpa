package com.project.demo;

import com.project.demo.dao.AuthorDao;
import com.project.demo.model.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@ComponentScan(basePackages = {"com.project.demo"})
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback
public class AuthorDaoIntegrationTest {

    @Autowired
    private AuthorDao authorDao;

    @Test
    void testGetAuthor(){
        Author author = authorDao.getById(1L);
        assertThat(author).isNotNull();
    }

    @Test
    void testGetAuthorByName() {
        Author author = authorDao.findAuthorByName("Craig", "Walls");
        assertThat(author).isNotNull();
    }

    @Test
    @Transactional
    void testSaveAuthor() {
        Author author = new Author();
        author.setFirstName("Taha");
        author.setLastName("Topal");
        Author saved = authorDao.saveNewAuthor(author);

        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
    }

    @Test
    void testUpdateAuthor() {
        Author author = new Author();
        author.setFirstName("Taha");
        author.setLastName("t");

        Author saved = authorDao.saveNewAuthor(author);
        saved.setLastName("Topal");

        Author updated = authorDao.updateAuthor(saved);
        assertThat(saved.getLastName()).isEqualTo("Topal");
    }

    @Test
    void testDeleteAuthor() {
        Author author = new Author();
        author.setFirstName("Taha");
        author.setLastName("t");

        Author saved = authorDao.saveNewAuthor(author);
        authorDao.deleteAuthorById(saved.getId());

        Author deleted = authorDao.getById(saved.getId());
        assertThat(deleted);
    }
}
