package com.mohamedgamal.springJpa.service;


import com.mohamedgamal.springJpa.DTO.AuthorDto;
import com.mohamedgamal.springJpa.DTO.BookDto;
import com.mohamedgamal.springJpa.entites.Author;
import com.mohamedgamal.springJpa.entites.Book;
import com.mohamedgamal.springJpa.entites.BorrowRecord;
import com.mohamedgamal.springJpa.repos.AuhtorRepo;
import com.mohamedgamal.springJpa.repos.BookRepo;
import com.mohamedgamal.springJpa.repos.BorrowRecordRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthorService {
    @Autowired
    AuhtorRepo authorRepo;

    @Autowired
    BookRepo bookRepo;
    @Autowired
    private BorrowRecordRepo borrowRecordRepo; // You'll need this repository


    public AuthorDto createAuthor(AuthorDto authorDto) {
        Author author = AuthorDto.toEntity(authorDto);
        Author saved = authorRepo.save(author);
        authorRepo.save(saved);
        return AuthorDto.toDto(saved);
    }


    public List<AuthorDto> getAllAuthors() {
        return authorRepo.findAll()
                .stream()
                .map(AuthorDto::toDto)
                .collect(Collectors.toList());
    }


    public AuthorDto getAuthorById(Long id) {
        Author author = authorRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author with ID " + id + " not found"));
        return AuthorDto.toDto(author);
    }


    public AuthorDto updateAuthor(AuthorDto dto) {
        Author existing = authorRepo.findById(dto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Author with ID " + dto.getId() + " not found"));

        existing.setFirstName(dto.getFirstName());
        existing.setLastName(dto.getLastName());


        if (dto.getBooksId() != null) {
            Set<Book> books = bookRepo.findAllById(dto.getBooksId()).stream().collect(Collectors.toSet());
            existing.setBooks(books);
        }

        Author updated = authorRepo.save(existing);
        return AuthorDto.toDto(updated);
    }


    public String deleteAuthor(Long id) {

        if (!authorRepo.existsById(id)) {
            return "Author with ID " + id + " does not exist.";
        }

        try {
            authorRepo.deleteById(id);
            return "author deleted successfully";
        } catch (DataIntegrityViolationException e) {
            return "Cannot delete Author with ID " + id +
                    ". It is referenced by other records. Please remove all references first.";
        } catch (Exception e) {
            return "Error occurred while deleting Author with ID " + id + ": " + e.getMessage();
        }

    }


}
