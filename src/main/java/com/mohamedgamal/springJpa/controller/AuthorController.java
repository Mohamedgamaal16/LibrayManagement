package com.mohamedgamal.springJpa.controller;

import com.mohamedgamal.springJpa.DTO.AuthorDto;
import com.mohamedgamal.springJpa.service.AuthorService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/author")
@CrossOrigin(origins = "*")

public class AuthorController {
    @Autowired
    private AuthorService authorService;


    @GetMapping
    public ResponseEntity<List<AuthorDto>> getAllAuthors() {
        try {
            List<AuthorDto> authors = authorService.getAllAuthors();
            if (authors.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(authors, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<AuthorDto> getAuthorById(@PathVariable("id") Long id) {
        try {
            AuthorDto author = authorService.getAuthorById(id);
            return new ResponseEntity<>(author, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto authorDto) {
        try {
            AuthorDto createdAuthor = authorService.createAuthor(authorDto);
            return new ResponseEntity<>(createdAuthor, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<AuthorDto> updateAuthor(@PathVariable("id") Long id, @RequestBody AuthorDto authorDto) {
        try {
            // Set the ID in the DTO to ensure we're updating the correct author
            authorDto.setId(id);
            AuthorDto updatedAuthor = authorService.updateAuthor(authorDto);
            return new ResponseEntity<>(updatedAuthor, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAuthor(@PathVariable("id") Long id) {
        try {
            String result = authorService.deleteAuthor(id);
            if (result.contains("does not exist") || result.contains("not found")) {
                return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting author", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
