package com.mohamedgamal.springJpa.controller;

import com.mohamedgamal.springJpa.DTO.PublisherDto;
import com.mohamedgamal.springJpa.service.PuplisherService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publisher")
@CrossOrigin(origins = "*")
public class PublisherController {

    @Autowired
    private PuplisherService publisherService;

    @GetMapping
    public ResponseEntity<?> getAllPublishers() {
        try {
            List<PublisherDto> publishers = publisherService.getAllPublisher();
            return new ResponseEntity<>(publishers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to retrieve publishers: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPublisherById(@PathVariable Long id) {
        try {
            PublisherDto publisher = publisherService.getPublisherById(id);
            return new ResponseEntity<>(publisher, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Publisher not found with ID: " + id, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error retrieving publisher: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> createPublisher(@RequestBody PublisherDto dto) {
        try {
            PublisherDto created = publisherService.createPuplisher(dto);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error creating publisher: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<?> updatePublisher(@RequestBody PublisherDto dto) {
        try {
            PublisherDto updated = publisherService.updatePublisher(dto);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Publisher not found for update", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating publisher: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePublisher(@PathVariable Long id) {
        try {
            String result = publisherService.removePublisher(id);
            if (result.contains("cant find")) {
                return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>("Publisher has references in other entities", HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting publisher: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
