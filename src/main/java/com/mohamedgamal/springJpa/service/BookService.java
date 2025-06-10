package com.mohamedgamal.springJpa.service;

import com.mohamedgamal.springJpa.DTO.BookDto;
import com.mohamedgamal.springJpa.entites.Author;
import com.mohamedgamal.springJpa.entites.Book;
import com.mohamedgamal.springJpa.entites.BorrowRecord;
import com.mohamedgamal.springJpa.entites.Publisher;
import com.mohamedgamal.springJpa.exception.LibraryManagmentAPIException;
import com.mohamedgamal.springJpa.repos.AuhtorRepo;
import com.mohamedgamal.springJpa.repos.BookRepo;
import com.mohamedgamal.springJpa.repos.BorrowRecordRepo;
import com.mohamedgamal.springJpa.repos.PublisherRepo;
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

@Service
public class BookService {

    @Autowired
    BookRepo bookRepo;

    @Autowired
    private BorrowRecordRepo borrowRecordRepo;

    @Autowired
    private AuhtorRepo authorRepo;

    @Autowired
    private PublisherRepo publisherRepo;

    @Transactional
    public BookDto createBook(BookDto dto) {
        Book book = BookDto.toEntity(dto);

        // Set authors if provided
//        if (dto.getAuthorIds() != null && !dto.getAuthorIds().isEmpty()) {
//            Set<Author> authors = new HashSet<>();
//            for (Long authorId : dto.getAuthorIds()) {
//                Author author = authorRepo.findById(authorId)
//                        .orElseThrow(() -> new LibraryManagmentAPIException("Author not found with id: " + authorId));
//                authors.add(author);
//            }
//            book.setAuthors(authors);
//        }

        // Set publisher if provided
        if (dto.getPublisherId() != null) {
            Publisher publisher = publisherRepo.findById(dto.getPublisherId())
                    .orElseThrow(() -> new LibraryManagmentAPIException("Publisher not found with id: " + dto.getPublisherId()));
            book.setPublisher(publisher);
        }

        Book saved = bookRepo.save(book);
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
                .orElseThrow(() -> new LibraryManagmentAPIException("Book not found with id: " + id));
    }

    @Transactional
    public BookDto updateBook(Long id, BookDto dto) {
        Book existing = bookRepo.findById(id)
                .orElseThrow(() -> new LibraryManagmentAPIException("Book not found"));

        existing.setTitle(dto.getTitle());
        existing.setIsbn(dto.getIsbn());
        existing.setPublicationDate(dto.getPublicationDate());

//        if (dto.getAuthorIds() != null) {
//            Set<Author> authors = new HashSet<>();
//            for (Long authorId : dto.getAuthorIds()) {
//                Author author = authorRepo.findById(authorId)
//                        .orElseThrow(() -> new LibraryManagmentAPIException("Author not found with id: " + authorId));
//                authors.add(author);
//            }
//            existing.setAuthors(authors);
//        }

        // Update publisher if provided
        if (dto.getPublisherId() != null) {
            Publisher publisher = publisherRepo.findById(dto.getPublisherId())
                    .orElseThrow(() -> new LibraryManagmentAPIException("Publisher not found with id: " + dto.getPublisherId()));
            existing.setPublisher(publisher);
        } else {
            existing.setPublisher(null);
        }

        Book updated = bookRepo.save(existing);
        return BookDto.toDto(updated);
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

    // Additional utility methods for managing book-author relationships

    @Transactional
    public BookDto addAuthorToBook(Long bookId, Long authorId) {
        Book book = bookRepo.findById(bookId)
                .orElseThrow(() -> new LibraryManagmentAPIException("Book not found with id: " + bookId));
        Author author = authorRepo.findById(authorId)
                .orElseThrow(() -> new LibraryManagmentAPIException("Author not found with id: " + authorId));

        book.getAuthors().add(author);
        Book updated = bookRepo.save(book);
        return BookDto.toDto(updated);
    }


}