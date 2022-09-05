package com.project.demo.repository;

import com.project.demo.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {

//    Author findAuthorByFirstNameAndAndLastName(String firstName, String lastName);
    Optional<Author> findAuthorByFirstNameAndAndLastName(String firstName, String lastName);

}
