package com.library.service;

import com.library.entity.*;
import com.library.exception.InvalidOperationException;
import com.library.exception.ResourceNotFoundException;
import com.library.repository.BorrowingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * BorrowingService - Business logic for borrowing operations
 * Demonstrates transaction management and business rules
 */
@Service
@RequiredArgsConstructor
@Transactional
public class BorrowingService {
    private final BorrowingRepository borrowingRepository;
    private final BookService bookService;
    private final MemberService memberService;
    
    private static final int BORROWING_PERIOD_DAYS = 14;
    private static final double FINE_PER_DAY = 10.0;

    /**
     * Borrow a book - reduces available copies and creates borrowing record
     */
    public Borrowing borrowBook(Long memberId, Long bookId) {
        Member member = memberService.getMemberById(memberId);
        Book book = bookService.getBookById(bookId);

        // Check if book is available
        if (book.getAvailableCopies() <= 0) {
            throw new InvalidOperationException("Book is not available for borrowing");
        }

        // Check if member is active
        if (member.getStatus() != MembershipStatus.ACTIVE) {
            throw new InvalidOperationException("Member status is not active");
        }

        // Create borrowing record
        Borrowing borrowing = Borrowing.builder()
                .member(member)
                .book(book)
                .borrowDate(LocalDate.now())
                .dueDate(LocalDate.now().plusDays(BORROWING_PERIOD_DAYS))
                .status(BorrowingStatus.ACTIVE)
                .fineAmount(0.0)
                .build();

        // Update available copies
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookService.addBook(book);

        return borrowingRepository.save(borrowing);
    }

    /**
     * Return a book - increases available copies and calculates fine if overdue
     */
    public Borrowing returnBook(Long borrowingId) {
        Borrowing borrowing = borrowingRepository.findById(borrowingId)
                .orElseThrow(() -> new ResourceNotFoundException("Borrowing record not found"));

        if (borrowing.getStatus() != BorrowingStatus.ACTIVE) {
            throw new InvalidOperationException("Book has already been returned");
        }

        borrowing.setReturnDate(LocalDate.now());
        borrowing.setStatus(BorrowingStatus.RETURNED);

        // Calculate fine if overdue
        if (LocalDate.now().isAfter(borrowing.getDueDate())) {
            long daysOverdue = ChronoUnit.DAYS.between(borrowing.getDueDate(), LocalDate.now());
            double fine = daysOverdue * FINE_PER_DAY;
            borrowing.setFineAmount(fine);
            
            // Update member's outstanding fine
            Member member = borrowing.getMember();
            member.setOutstandingFine(member.getOutstandingFine() + fine);
        } else {
            borrowing.setFineAmount(0.0);
        }

        // Update available copies
        Book book = borrowing.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookService.addBook(book);

        return borrowingRepository.save(borrowing);
    }

    /**
     * Get borrowings by member
     */
    @Transactional(readOnly = true)
    public List<Borrowing> getBorrowingsByMember(Long memberId) {
        return borrowingRepository.findByMemberId(memberId);
    }

    /**
     * Get overdue books
     */
    @Transactional(readOnly = true)
    public List<Borrowing> getOverdueBooks() {
        return borrowingRepository.findOverdueBooks(LocalDate.now());
    }

    /**
     * Get active borrowings by member
     */
    @Transactional(readOnly = true)
    public List<Borrowing> getActiveBorrowingsByMember(Long memberId) {
        return borrowingRepository.findActiveBorrowingsByMember(memberId);
    }
}