package com.mohamedgamal.springJpa.service;

import com.mohamedgamal.springJpa.DTO.BorrowRecordDto;
import com.mohamedgamal.springJpa.DTO.UserDto;
import com.mohamedgamal.springJpa.entites.BorrowRecord;
import com.mohamedgamal.springJpa.entites.User;
import com.mohamedgamal.springJpa.repos.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    BorrowBookService borrowBookService;

    public UserDto createUser(UserDto dto) {
        User user = UserDto.toEntity(dto);
        User saved = userRepo.save(user);
        return UserDto.toDto(saved);
    }

    public String deleteUser(Long id) {
        if (!userRepo.existsById(id)) {
            return "User not found.";
        }
        try {
            userRepo.deleteById(id);
            return "User deleted successfully.";
        } catch (DataIntegrityViolationException e) {
            return "Cannot delete User with ID " + id +
                    ". It is referenced by other records. Please remove all references first.";
        } catch (Exception e) {
            return "Error occurred while deleting User with ID " + id + ": " + e.getMessage();
        }
    }

    public UserDto getUserById(Long id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + id));
        return UserDto.toDto(user);
    }

    public List<UserDto> getAllUsers() {
        return userRepo.findAll().stream()
                .map(UserDto::toDto)
                .collect(Collectors.toList());
    }

    public UserDto updateUser(UserDto dto) {
        User existing = userRepo.findById(dto.getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        existing.setUsername(dto.getUsername());
        existing.setEmail(dto.getEmail());
        existing.setPassword(dto.getPassword());


        if (dto.getBorrowRecordIds() != null) {
            List<Long> recordIds = dto.getBorrowRecordIds();

            List<BorrowRecordDto> borrowRecordDtos = borrowBookService.getAllBorrowsById(recordIds);

            // Convert BorrowRecordDto to BorrowRecord entity using stream
            List<BorrowRecord> borrowRecords = borrowRecordDtos.stream()
                    .map(BorrowRecordDto::toEntity)  // Assuming you have this method
                    .collect(Collectors.toList());

            existing.setBorrowRecords(borrowRecords);
        }

        User updated = userRepo.save(existing);
        return UserDto.toDto(updated);
    }

}
