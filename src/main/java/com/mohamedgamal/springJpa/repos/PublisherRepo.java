package com.mohamedgamal.springJpa.repos;

import com.mohamedgamal.springJpa.entites.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepo extends JpaRepository<Publisher,Long> {
}
