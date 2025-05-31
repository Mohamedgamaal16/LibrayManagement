package com.mohamedgamal.springJpa.service;

import com.mohamedgamal.springJpa.DTO.BookDto;
import com.mohamedgamal.springJpa.entites.Book;
import com.mohamedgamal.springJpa.entites.BorrowRecord;
import com.mohamedgamal.springJpa.repos.BookRepo;
import com.mohamedgamal.springJpa.repos.BorrowRecordRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BookService {

    @Autowired
    BookRepo bookRepo;

    @Autowired
    private BorrowRecordRepo borrowRecordRepo; // You'll need this repository

    public BookDto createBook(BookDto dto) {
        Book saved = bookRepo.save(BookDto.toEntity(dto));
        return BookDto.toDto(saved);

    }


    public List<BookDto> getAllBooks() {
        return bookRepo.findAll()
                .stream()
                .map(BookDto::toDto)
                .collect(Collectors.toList());
    }


    public BookDto getBookById(Long id) {
        Optional<Book> book = this.bookRepo.findById(id);
        return book.map(BookDto::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + id));
    }

//    public String removeBookById(Long id) {
//        if (!bookRepo.existsById(id)) {
//            return "Book with ID " + id + " does not exist.";
//        }
//
//        bookRepo.deleteById(id);
//        return "Book with ID " + id + " was successfully deleted.";
//    }


    public BookDto updateBook(Long id, BookDto dto) {
        Book existing = bookRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));

        existing.setTitle(dto.getTitle());
        existing.setIsbn(dto.getIsbn());
        existing.setPublicationDate(dto.getPublicationDate());

        Book updated = bookRepo.save(existing);
        return BookDto.toDto(updated);
    }

    public Set<BookDto> getAllBooksByIds(List<Long> ids) {
        Iterable<Book> books = bookRepo.findAllById(ids);
        Set<BookDto> result = new HashSet<>();

        for (Book book : books) {
            result.add(BookDto.toDto(book));
        }

        return result;
    }


    public String removeBookById(Long id) {
        if (!bookRepo.existsById(id)) {
            return "Book with ID " + id + " does not exist.";
        }

        try {

            List<BorrowRecord> borrowRecords = borrowRecordRepo.findByBookId(id);
            if (!borrowRecords.isEmpty()) {
                long activeBorrows = borrowRecords.stream()
                        .filter(record -> record.getReturnDate() == null)
                        .count();
                if (activeBorrows > 0) {
                    return "Cannot delete book with ID " + id +
                            ". It has " + activeBorrows + " active borrow(s). " +
                            "Please ensure all copies are returned first.";
                } else {
                    return "Cannot delete book with ID " + id +
                            ". It has historical borrow records (" + borrowRecords.size() + " records). " +
                            "Consider soft deletion instead.";
                }
            }

            bookRepo.deleteById(id);
            return "Book with ID " + id + " was successfully deleted.";

        } catch (DataIntegrityViolationException e) {
            return "Cannot delete book with ID " + id +
                    ". It is referenced by other records. Please remove all references first.";
        } catch (Exception e) {
            return "Error occurred while deleting book with ID " + id + ": " + e.getMessage();
        }
    }


    @Transactional
    public String forceRemoveBookByIdButKeepInDatabase(Long id) {
        if (!bookRepo.existsById(id)) {
            return "Book with ID " + id + " does not exist.";
        }

        try {
            Book book = bookRepo.findById(id).orElseThrow();

            List<BorrowRecord> borrowRecords = borrowRecordRepo.findByBookId(id);
            for (BorrowRecord record : borrowRecords) {
                record.setBook(null);
            }
            borrowRecordRepo.saveAll(borrowRecords);

            bookRepo.delete(book);

            return "Book with ID " + id + " was deleted after unlinking " +
                    borrowRecords.size() + " borrow records.";

        } catch (Exception e) {
            return "Error occurred while force deleting book with ID " + id + ": " + e.getMessage();
        }
    }

}
