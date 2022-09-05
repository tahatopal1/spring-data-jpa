package com.project.demo.repository;

import com.project.demo.model.composite.AuthorEmbedded;
import com.project.demo.model.composite.NameId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorEmbeddedRepository extends JpaRepository<AuthorEmbedded, NameId> {
}
