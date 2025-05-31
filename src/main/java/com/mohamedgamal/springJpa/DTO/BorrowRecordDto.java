package com.mohamedgamal.springJpa.DTO;

import com.mohamedgamal.springJpa.entites.Book;
import com.mohamedgamal.springJpa.entites.BorrowRecord;
import com.mohamedgamal.springJpa.entites.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BorrowRecordDto {

    private Long id;
    private Long userId;
    private Long bookId;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private UserDto user;
    private BookDto book;



    public static BorrowRecordDto toDto(BorrowRecord borrowRecord) {
        if (borrowRecord == null) {
            return null;
        }

        return BorrowRecordDto.builder()
                .id(borrowRecord.getId())
                .userId(borrowRecord.getUser().getId())
                .bookId(borrowRecord.getBook().getId())
                .borrowDate(borrowRecord.getBorrowDate())
                .returnDate(borrowRecord.getReturnDate())
                .user(UserDto.toDto(borrowRecord.getUser()))
                .book(BookDto.toDto(borrowRecord.getBook()))
                .build();
    }

    public static BorrowRecord toEntity(BorrowRecordDto dto) {

        if (dto == null) {
            return null;
        }

return BorrowRecord.builder()
        .id(dto.getId())
        .user(null)
        .book(null)
        .borrowDate(dto.getBorrowDate())
        .returnDate(dto.getReturnDate()).build();
    }
}
