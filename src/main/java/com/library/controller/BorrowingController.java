package com.library.controller;

import com.library.entity.Borrowing;
import com.library.service.BorrowingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * BorrowingController - REST endpoints for Borrowing operations
 * Demonstrates RESTful Web Services and transaction management
 */
@RestController
@RequestMapping("/api/borrowings")
@RequiredArgsConstructor
public class BorrowingController {
    private final BorrowingService borrowingService;

    /**
     * POST - Borrow a book
     */
    @PostMapping("/borrow/{memberId}/{bookId}")
    public ResponseEntity<Borrowing> borrowBook(@PathVariable Long memberId, @PathVariable Long bookId) {
        return new ResponseEntity<>(borrowingService.borrowBook(memberId, bookId), HttpStatus.CREATED);
    }

    /**
     * PUT - Return a book
     */
    @PutMapping("/return/{borrowingId}")
    public ResponseEntity<Borrowing> returnBook(@PathVariable Long borrowingId) {
        return ResponseEntity.ok(borrowingService.returnBook(borrowingId));
    }

    /**
     * GET - Get borrowings by member
     */
    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<Borrowing>> getBorrowingsByMember(@PathVariable Long memberId) {
        return ResponseEntity.ok(borrowingService.getBorrowingsByMember(memberId));
    }

    /**
     * GET - Get active borrowings by member
     */
    @GetMapping("/member/{memberId}/active")
    public ResponseEntity<List<Borrowing>> getActiveBorrowingsByMember(@PathVariable Long memberId) {
        return ResponseEntity.ok(borrowingService.getActiveBorrowingsByMember(memberId));
    }

    /**
     * GET - Get overdue books
     */
    @GetMapping("/overdue")
    public ResponseEntity<List<Borrowing>> getOverdueBooks() {
        return ResponseEntity.ok(borrowingService.getOverdueBooks());
    }
}