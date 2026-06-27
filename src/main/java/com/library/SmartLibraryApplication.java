package com.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Smart Library System - Main Application Entry Point
 * This is an enterprise library management system built with Spring Boot
 * covering RESTful Web Services, Spring Data JPA, Hibernate ORM, and CRUD operations
 */
@SpringBootApplication
public class SmartLibraryApplication {
    public static void main(String[] args) {
        SpringApplication.run(SmartLibraryApplication.class, args);
        System.out.println("Smart Library System Started Successfully!");
    }
}