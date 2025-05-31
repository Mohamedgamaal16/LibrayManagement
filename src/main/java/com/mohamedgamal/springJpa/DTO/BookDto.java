package com.mohamedgamal.springJpa.DTO;

import com.mohamedgamal.springJpa.entites.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {

    private Long id;
    private String title;
    private String isbn;
    private LocalDate publicationDate;

    public static BookDto toDto(Book book) {
        if (book == null) {
            return null;
        }

        return BookDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .isbn(book.getIsbn())
                .publicationDate(book.getPublicationDate())
                .build();

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
                .build();

    }

}
