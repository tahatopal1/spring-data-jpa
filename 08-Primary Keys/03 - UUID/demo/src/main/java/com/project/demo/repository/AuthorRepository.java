package com.project.demo.repository;

import com.project.demo.model.AuthorUuid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuthorRepository extends JpaRepository<AuthorUuid, UUID> {
}
