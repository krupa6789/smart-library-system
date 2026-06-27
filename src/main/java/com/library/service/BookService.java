package com.library.service;

import com.library.entity.Book;
import com.library.exception.ResourceNotFoundException;
import com.library.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * BookService - Business logic for Book operations
 * Demonstrates CRUD operations and transaction management
 */
@Service
@RequiredArgsConstructor
@Transactional
public class BookService {
    private final BookRepository bookRepository;

    /**
     * Add a new book to the library
     */
    public Book addBook(Book book) {
        if (book.getAvailableCopies() == null) {
            book.setAvailableCopies(book.getTotalCopies());
        }
        return bookRepository.save(book);
    }

    /**
     * Get a book by ID
     */
    @Transactional(readOnly = true)
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + id));
    }

    /**
     * Get all books
     */
    @Transactional(readOnly = true)
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    /**
     * Update a book
     */
    public Book updateBook(Long id, Book bookDetails) {
        Book book = getBookById(id);
        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        book.setPublisher(bookDetails.getPublisher());
        book.setCategory(bookDetails.getCategory());
        book.setPrice(bookDetails.getPrice());
        book.setDescription(bookDetails.getDescription());
        return bookRepository.save(book);
    }

    /**
     * Delete a book
     */
    public void deleteBook(Long id) {
        Book book = getBookById(id);
        bookRepository.delete(book);
    }

    /**
     * Search books by title
     */
    @Transactional(readOnly = true)
    public List<Book> searchByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }

    /**
     * Search books by author
     */
    @Transactional(readOnly = true)
    public List<Book> searchByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author);
    }

    /**
     * Search books by category
     */
    @Transactional(readOnly = true)
    public List<Book> searchByCategory(String category) {
        return bookRepository.findByCategory(category);
    }

    /**
     * Get available books
     */
    @Transactional(readOnly = true)
    public List<Book> getAvailableBooks() {
        return bookRepository.findAvailableBooks();
    }
}