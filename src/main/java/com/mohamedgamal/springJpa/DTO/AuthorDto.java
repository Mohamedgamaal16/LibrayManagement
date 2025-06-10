package com.mohamedgamal.springJpa.DTO;

import com.mohamedgamal.springJpa.entites.Author;
import com.mohamedgamal.springJpa.entites.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDto {
    private Long id;
    private String firstName;
    private String lastName;
    private Set<Long> booksId = new HashSet<>();

    // Safe conversion method that handles lazy loading
    public static AuthorDto toDto(Author author) {
        if (author == null) return null;

        AuthorDto.AuthorDtoBuilder builder = AuthorDto.builder()
                .id(author.getId())
                .firstName(author.getFirstName())
                .lastName(author.getLastName());

        try {
            if (author.getBooks() != null) {
                Set<Long> bookIds = author.getBooks().stream()
                        .map(Book::getId)
                        .collect(Collectors.toSet());
                builder.booksId(bookIds);
            } else {
                builder.booksId(new HashSet<>());
            }
        } catch (Exception e) {
            // If lazy loading fails, just set empty set
            System.err.println("Could not load books for author " + author.getId() + ": " + e.getMessage());
            builder.booksId(new HashSet<>());
        }

        return builder.build();
    }



    public static Author toEntity(AuthorDto dto) {
        if (dto == null) return null;

        return Author.builder()
                .id(dto.getId())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .books(new HashSet<>())
                .build();
    }
}