package com.mohamedgamal.springJpa.repos;

import com.mohamedgamal.springJpa.entites.BorrowRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BorrowRecordRepo extends JpaRepository<BorrowRecord,Long> {

    List<BorrowRecord> findByBookId(Long bookId);

}
