package com.project.demo.dao;

import com.project.demo.model.Author;

import java.util.List;

public interface AuthorDao {

    List<Author> findAll();

    List<Author> listAuthorByLastNameLike(String lastName);

    Author getById(Long id);

    Author findAuthorByName(String firstName, String lastName);

    Author saveNewAuthor(Author author);

    Author updateAuthor(Author author);

    void deleteAuthorById(Long id);

    Author findAuthorByNameCriteria(String name, String surname);

    Author findAuthorByNameNative(String name, String surname);
}
