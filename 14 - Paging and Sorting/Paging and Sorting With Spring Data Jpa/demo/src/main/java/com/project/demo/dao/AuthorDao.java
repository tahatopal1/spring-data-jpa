package com.project.demo.dao;

import com.project.demo.model.Author;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AuthorDao {

    List<Author> findAllAuthorsByLastName(String lastname, Pageable pageable);
    Author getById(Long id);

    Author findAuthorByName(String firstName, String lastName);

    Author saveNewAuthor(Author author);

    Author updateAuthor(Author author);

    void deleteAuthorById(Long id);
}
