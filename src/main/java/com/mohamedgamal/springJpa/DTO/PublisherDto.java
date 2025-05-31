package com.mohamedgamal.springJpa.DTO;

import com.mohamedgamal.springJpa.entites.Book;
import com.mohamedgamal.springJpa.entites.Publisher;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PublisherDto {

    private Long id;
    private String name;
    private String address;
    private List<Long> bookIds;
    private List<BookDto> books;

    public static PublisherDto toDto(Publisher publisher) {
        if (publisher == null) return null;

        return PublisherDto.builder()
                .id(publisher.getId())
                .name(publisher.getName())
                .address(publisher.getAddress())
                .bookIds(publisher.getBooks() != null
                        ? publisher.getBooks().stream().map(Book::getId).collect(Collectors.toList())
                        : null)
                .books(publisher.getBooks() != null
                        ? publisher.getBooks().stream().map(BookDto::toDto).collect(Collectors.toList())
                        : null)
                .build();
    }

    /**
     * Convert DTO to Entity. You must pass resolved Book entities (fetched using bookIds).
     */
    public static Publisher toEntity(PublisherDto dto) {
        if (dto == null) return null;

        return Publisher.builder()
                .id(dto.getId())
                .name(dto.getName())
                .address(dto.getAddress())
                .books(null)
                .build();
    }
}
