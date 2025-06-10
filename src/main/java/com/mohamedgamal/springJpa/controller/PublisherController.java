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

            List<PublisherDto> publishers = publisherService.getAllPublisher();
            return new ResponseEntity<>(publishers, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPublisherById(@PathVariable Long id) {
            PublisherDto publisher = publisherService.getPublisherById(id);
            return new ResponseEntity<>(publisher, HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<?> createPublisher(@RequestBody PublisherDto dto) {
            PublisherDto created = publisherService.createPuplisher(dto);
            return new ResponseEntity<>(created, HttpStatus.CREATED);

    }

    @PutMapping
    public ResponseEntity<?> updatePublisher(@RequestBody PublisherDto dto) {
            PublisherDto updated = publisherService.updatePublisher(dto);
            return new ResponseEntity<>(updated, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePublisher(@PathVariable Long id) {
        publisherService.removePublisher(id);
        return ResponseEntity.ok("Publisher successfully deleted");
    }

}
