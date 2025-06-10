package com.mohamedgamal.springJpa.DTO;

import com.mohamedgamal.springJpa.entites.Author;
import com.mohamedgamal.springJpa.entites.Book;
import com.mohamedgamal.springJpa.entites.Publisher;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private Long id;
    private String title;
    private String isbn;
    private LocalDate publicationDate;
//    private Set<Long> authorIds = new HashSet<>();
    private Long publisherId;
//    private Set<String> authorNames = new HashSet<>();
    private String publisherName;

    public static BookDto toDto(Book book) {
        if (book == null) {
            return null;
        }

        BookDto.BookDtoBuilder builder = BookDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .isbn(book.getIsbn())
                .publicationDate(book.getPublicationDate());

        // Handle publisher information
        try {
            if (book.getPublisher() != null) {
                builder.publisherId(book.getPublisher().getId());
                builder.publisherName(book.getPublisher().getName());
            }
        } catch (Exception e) {
            // Handle lazy loading issues
            System.err.println("Could not load publisher for book " + book.getId() + ": " + e.getMessage());
        }


        return builder.build();
    }

    public static Book toEntity(BookDto dto) {
        if (dto == null) {
            return null;
        }
        return Book.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .isbn(dto.getIsbn())
                .publicationDate(dto.getPublicationDate())
                .authors(new HashSet<>())
                .publisher(null)
                .build();
    }
}