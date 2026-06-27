package com.library.controller;

import com.library.entity.Member;
import com.library.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * MemberController - REST endpoints for Member operations
 * Demonstrates RESTful Web Services
 */
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    /**
     * POST - Register a new member
     */
    @PostMapping
    public ResponseEntity<Member> registerMember(@Valid @RequestBody Member member) {
        return new ResponseEntity<>(memberService.registerMember(member), HttpStatus.CREATED);
    }

    /**
     * GET - Get a member by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Member> getMember(@PathVariable Long id) {
        return ResponseEntity.ok(memberService.getMemberById(id));
    }

    /**
     * GET - Get all members
     */
    @GetMapping
    public ResponseEntity<List<Member>> getAllMembers() {
        return ResponseEntity.ok(memberService.getAllMembers());
    }

    /**
     * GET - Get member by email
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<Member> getMemberByEmail(@PathVariable String email) {
        return ResponseEntity.ok(memberService.getMemberByEmail(email));
    }

    /**
     * GET - Get members with pending fines
     */
    @GetMapping("/fine/pending")
    public ResponseEntity<List<Member>> getMembersWithPendingFine() {
        return ResponseEntity.ok(memberService.getMembersWithPendingFine());
    }

    /**
     * PUT - Update member information
     */
    @PutMapping("/{id}")
    public ResponseEntity<Member> updateMember(@PathVariable Long id, @Valid @RequestBody Member memberDetails) {
        return ResponseEntity.ok(memberService.updateMember(id, memberDetails));
    }

    /**
     * DELETE - Delete a member
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }
}