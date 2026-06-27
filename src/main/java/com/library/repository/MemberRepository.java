package com.library.repository;

import com.library.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * MemberRepository - Spring Data JPA Repository for Member entity
 * Demonstrates CRUD operations and custom queries
 */
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    
    Optional<Member> findByPhone(String phone);
    
    @Query("SELECT m FROM Member m WHERE m.outstandingFine > 0")
    List<Member> findMembersWithPendingFine();
}