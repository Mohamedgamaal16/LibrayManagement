package com.mohamedgamal.springJpa.repos;

import com.mohamedgamal.springJpa.entites.Book;
import com.mohamedgamal.springJpa.entites.BorrowRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepo extends JpaRepository<Book,Long> {

}
