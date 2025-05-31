package com.mohamedgamal.springJpa.DTO;


import com.mohamedgamal.springJpa.entites.BorrowRecord;
import com.mohamedgamal.springJpa.entites.Publisher;
import com.mohamedgamal.springJpa.entites.User;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String username;
    private String email;
    private String password;
    private List<Long> borrowRecordIds;

    public static UserDto toDto(User user) {
        if (user == null) {
            return null;
        }

        return UserDto.builder().id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .borrowRecordIds(
                        user.getBorrowRecords() != null
                                ? user.getBorrowRecords().stream().map(br -> br.getId()).toList()
                                : List.of()
                ).build();

    }


    public static User toEntity(UserDto dto) {

        if (dto == null) {
            return null;
        }
        return User.builder().id(dto.getId())
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .borrowRecords(new ArrayList<>())
                .build();

    }
}
