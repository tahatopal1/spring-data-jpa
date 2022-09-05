package com.project.demo;

import com.project.demo.model.AuthorUuid;
import com.project.demo.model.BookNatural;
import com.project.demo.model.BookUuid;
import com.project.demo.repository.AuthorRepository;
import com.project.demo.repository.BookNaturalRepository;
import com.project.demo.repository.BookRepository;
import com.project.demo.repository.BookUuidRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("default")
@DataJpaTest
@ComponentScan(basePackages = {"com.project.demo"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MySQLIntegrationTest {
    @Autowired
    BookRepository bookRepository;

    @Autowired
    BookUuidRepository bookUuidRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    BookNaturalRepository bookNaturalRepository;

    @Test
    void bookNaturalTest() {
        BookNatural bookNatural = new BookNatural();
        bookNatural.setTitle("My Book");
        BookNatural saved = bookNaturalRepository.save(bookNatural);

        BookNatural fetched = bookNaturalRepository.getReferenceById(saved.getTitle());
        assertThat(fetched).isNotNull();
    }

    @Test
    void testBookId(){
        BookUuid bookUuid = bookUuidRepository.save(new BookUuid());
        assertThat(bookUuid).isNotNull();
        assertThat(bookUuid.getId());

        BookUuid fetched = bookUuidRepository.getReferenceById(bookUuid.getId());
        assertThat(fetched).isNotNull();
    }

    @Test
    void testAuthorId(){
        AuthorUuid authorUuid = authorRepository.save(new AuthorUuid());
        assertThat(authorUuid).isNotNull();
        assertThat(authorUuid.getId());

        AuthorUuid fetched = authorRepository.getReferenceById(authorUuid.getId());
        assertThat(fetched).isNotNull();
    }

}
