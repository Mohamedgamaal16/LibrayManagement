package com.mohamedgamal.springJpa.controller;


import com.mohamedgamal.springJpa.DTO.BorrowRecordDto;
import com.mohamedgamal.springJpa.service.BorrowBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ConcurrentModificationException;
import java.util.List;

@RestController
@RequestMapping("/api/borrow-record-controller")
@CrossOrigin(origins = "*")
public class BorrowRecordController {

    @Autowired
    private BorrowBookService borrowBookService;

    @GetMapping
    public ResponseEntity<?> getAllBorrowRecords() {
        try {
            List<BorrowRecordDto> records = borrowBookService.getAllBorrowRecords();
            return new ResponseEntity<>(records, HttpStatus.OK);
        } catch (ConcurrentModificationException e) {
            return new ResponseEntity<>("Concurrent modification detected while processing borrow records.", HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>("Unexpected error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public BorrowRecordDto getBorrowRecordsById(@PathVariable Long id){
        return borrowBookService.getBorrowRecordById(id);
    }


    @PostMapping
    public String borrowBook(@RequestBody BorrowRecordDto borrowRecordDto){
        borrowBookService.borrowBook(borrowRecordDto);
        return "success";
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBorrowRecord(@PathVariable Long id) {
        try {
            borrowBookService.deleteBorrowRecord(id);
            return new ResponseEntity<>("Borrow record deleted successfully", HttpStatus.OK);
        } catch (ConcurrentModificationException e) {
            return new ResponseEntity<>("Conflict during deletion: Concurrent modification occurred.", HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting borrow record: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
