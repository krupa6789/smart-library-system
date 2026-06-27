package com.library.repository;

import com.library.entity.Borrowing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * BorrowingRepository - Spring Data JPA Repository for Borrowing entity
 * Demonstrates CRUD operations and custom queries
 */
@Repository
public interface BorrowingRepository extends JpaRepository<Borrowing, Long> {
    List<Borrowing> findByMemberId(Long memberId);
    
    List<Borrowing> findByBookId(Long bookId);
    
    @Query("SELECT b FROM Borrowing b WHERE b.status = 'ACTIVE' AND b.dueDate < ?1")
    List<Borrowing> findOverdueBooks(LocalDate date);
    
    @Query("SELECT b FROM Borrowing b WHERE b.member.id = ?1 AND b.status = 'ACTIVE'")
    List<Borrowing> findActiveBorrowingsByMember(Long memberId);
}