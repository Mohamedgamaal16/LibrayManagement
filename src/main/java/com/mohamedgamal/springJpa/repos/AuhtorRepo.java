package com.mohamedgamal.springJpa.repos;

import com.mohamedgamal.springJpa.entites.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuhtorRepo extends JpaRepository<Author,Long> {
}
