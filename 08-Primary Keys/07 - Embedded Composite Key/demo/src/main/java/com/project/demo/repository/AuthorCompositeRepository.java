package com.project.demo.repository;

import com.project.demo.model.composite.AuthorComposite;
import com.project.demo.model.composite.NameId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorCompositeRepository extends JpaRepository<AuthorComposite, NameId> {
}
