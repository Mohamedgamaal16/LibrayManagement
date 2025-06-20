package com.mohamedgamal.springJpa.service;

import com.mohamedgamal.springJpa.DTO.AuthorDto;
import com.mohamedgamal.springJpa.DTO.BookDto;
import com.mohamedgamal.springJpa.DTO.PublisherDto;
import com.mohamedgamal.springJpa.entites.Author;
import com.mohamedgamal.springJpa.entites.Book;
import com.mohamedgamal.springJpa.entites.Publisher;
import com.mohamedgamal.springJpa.exception.LibraryManagmentAPIException;
import com.mohamedgamal.springJpa.repos.BookRepo;
import com.mohamedgamal.springJpa.repos.PublisherRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PuplisherService {

    @Autowired
    PublisherRepo publisherRepo;
    @Autowired
    private BookRepo bookRepo;

    public PublisherDto createPuplisher(PublisherDto dto) {

        Publisher publisher = PublisherDto.toEntity(dto);
        Publisher saved = publisherRepo.save(publisher);
        publisherRepo.save(saved);
        return PublisherDto.toDto(saved);
    }

    public void removePublisher(Long id) {
        if (!publisherRepo.existsById(id)) {
            throw new LibraryManagmentAPIException("Cannot find the publisher with ID: " + id);
        }

        try {
            publisherRepo.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new LibraryManagmentAPIException("Cannot delete publisher with ID " + id +
                    ". It is referenced by other records. Please remove all references first.");
        } catch (Exception e) {
            throw new LibraryManagmentAPIException("Error occurred while deleting publisher with ID " + id + ": " + e.getMessage());
        }
    }

    public List<PublisherDto> getAllPublisher() {
        return publisherRepo.findAll().stream().map(PublisherDto::toDto).collect(Collectors.toList());
    }

    public PublisherDto getPublisherById(Long id) {

        Optional<Publisher> publisher = publisherRepo.findById(id);
        return publisher.map(PublisherDto::toDto).orElseThrow(() -> new LibraryManagmentAPIException("Publisher not found with id: " + id));

    }




    public PublisherDto updatePublisher(PublisherDto dto) {
        Publisher existing = publisherRepo.findById(dto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Publisher not found"));

        existing.setName(dto.getName());
        existing.setAddress(dto.getAddress());

        if (dto.getBookIds() != null && !dto.getBookIds().isEmpty()) {
            List<Book> books = bookRepo.findAllById(dto.getBookIds());
            existing.setBooks(books);
        } else {
            existing.setBooks(null);
        }

        Publisher saved = publisherRepo.save(existing);
        return PublisherDto.toDto(saved);
    }

}
