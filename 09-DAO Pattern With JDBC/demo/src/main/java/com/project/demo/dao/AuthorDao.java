package com.project.demo.dao;

import com.project.demo.model.Author;

public interface AuthorDao {
    Author getById(Long id);

    Author findAuthorByName(String name, String surname);

    Author saveNewAuthor(Author author);

    Author updateAuthor(Author saved);

    void deleteAuthorById(Long id);
}
