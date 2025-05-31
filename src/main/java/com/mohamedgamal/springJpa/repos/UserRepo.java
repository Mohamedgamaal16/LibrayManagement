package com.mohamedgamal.springJpa.repos;

import com.mohamedgamal.springJpa.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Long> {
}
