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

            List<AuthorDto> authors = authorService.getAllAuthors();
            return new ResponseEntity<>(authors, HttpStatus.OK);

    }


    @GetMapping("/{id}")
    public ResponseEntity<AuthorDto> getAuthorById(@PathVariable("id") Long id) {

            AuthorDto author = authorService.getAuthorById(id);
            return new ResponseEntity<>(author, HttpStatus.OK);

    }


    @PostMapping
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto authorDto) {

            AuthorDto createdAuthor = authorService.createAuthor(authorDto);
            return new ResponseEntity<>(createdAuthor, HttpStatus.CREATED);

    }


    @PutMapping("/{id}")
    public ResponseEntity<AuthorDto> updateAuthor(@PathVariable("id") Long id, @RequestBody AuthorDto authorDto) {

            // Set the ID in the DTO to ensure we're updating the correct author
            authorDto.setId(id);
            AuthorDto updatedAuthor = authorService.updateAuthor(authorDto);
            return new ResponseEntity<>(updatedAuthor, HttpStatus.OK);

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAuthor(@PathVariable("id") Long id) {
        String result = authorService.deleteAuthor(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
