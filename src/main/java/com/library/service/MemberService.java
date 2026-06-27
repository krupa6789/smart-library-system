package com.library.service;

import com.library.entity.Member;
import com.library.entity.MembershipStatus;
import com.library.exception.ResourceNotFoundException;
import com.library.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * MemberService - Business logic for Member operations
 * Demonstrates CRUD operations and business rules
 */
@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    /**
     * Register a new member
     */
    public Member registerMember(Member member) {
        member.setDateOfJoining(LocalDate.now());
        member.setStatus(MembershipStatus.ACTIVE);
        member.setOutstandingFine(0.0);
        return memberRepository.save(member);
    }

    /**
     * Get a member by ID
     */
    @Transactional(readOnly = true)
    public Member getMemberById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with ID: " + id));
    }

    /**
     * Get all members
     */
    @Transactional(readOnly = true)
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    /**
     * Update member information
     */
    public Member updateMember(Long id, Member memberDetails) {
        Member member = getMemberById(id);
        member.setName(memberDetails.getName());
        member.setEmail(memberDetails.getEmail());
        member.setPhone(memberDetails.getPhone());
        member.setAddress(memberDetails.getAddress());
        return memberRepository.save(member);
    }

    /**
     * Delete a member
     */
    public void deleteMember(Long id) {
        Member member = getMemberById(id);
        memberRepository.delete(member);
    }

    /**
     * Get member by email
     */
    @Transactional(readOnly = true)
    public Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with email: " + email));
    }

    /**
     * Get members with pending fines
     */
    @Transactional(readOnly = true)
    public List<Member> getMembersWithPendingFine() {
        return memberRepository.findMembersWithPendingFine();
    }
}