package com.mohamedgamal.springJpa.service;

import com.mohamedgamal.springJpa.DTO.BorrowRecordDto;
import com.mohamedgamal.springJpa.entites.Book;
import com.mohamedgamal.springJpa.entites.BorrowRecord;
import com.mohamedgamal.springJpa.entites.User;
import com.mohamedgamal.springJpa.repos.BookRepo;
import com.mohamedgamal.springJpa.repos.BorrowRecordRepo;
import com.mohamedgamal.springJpa.repos.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BorrowBookService {
    @Autowired
    BorrowRecordRepo borrowRecordRepo;
    @Autowired

    UserRepo userRepo;
    @Autowired

    BookRepo bookRepo;

    public BorrowRecordDto borrowBook(BorrowRecordDto dto) {
        if (dto.getUserId() == null || dto.getBookId() == null) {
            throw new IllegalArgumentException("User and Book must be provided.");
        }

        User user = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Book book = bookRepo.findById(dto.getBookId())
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));

        BorrowRecord borrowRecord = BorrowRecord.builder()
                .user(user)
                .book(book)
                .borrowDate(dto.getBorrowDate())
                .returnDate(dto.getReturnDate())
                .build();

        BorrowRecord saved = borrowRecordRepo.save(borrowRecord);
        return BorrowRecordDto.toDto(saved);
    }

    public List<BorrowRecordDto> getAllBorrowRecords() {
        return borrowRecordRepo.findAll()
                .stream()
                .map(BorrowRecordDto::toDto)
                .collect(Collectors.toList());
    }

    public BorrowRecordDto getBorrowRecordById(Long id) {
        BorrowRecord record = borrowRecordRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Borrow record not found"));
        return BorrowRecordDto.toDto(record);
    }

    public String deleteBorrowRecord(Long id) {
        if (!borrowRecordRepo.existsById(id)) {
            return "Borrow record not found.";
        }
        try {
            borrowRecordRepo.deleteById(id);
            return "Borrow record deleted successfully.";
        } catch (DataIntegrityViolationException e) {
            return "Cannot delete record with ID " + id +
                    ". It is referenced by other records. Please remove all references first.";
        } catch (Exception e) {
            return "Error occurred while deleting record with ID " + id + ": " + e.getMessage();
        }
    }

    public List<BorrowRecordDto> getAllBorrowsById(List<Long> ids) {
        return borrowRecordRepo.findAllById(ids)
                .stream()
                .map(BorrowRecordDto::toDto)
                .collect(Collectors.toList());
    }

}
