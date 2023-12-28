package com.gabrielbarrilli.BookstoreManager.controller;

import com.gabrielbarrilli.BookstoreManager.entity.Book;
import com.gabrielbarrilli.BookstoreManager.exceptions.ResourceNotFoundException;
import com.gabrielbarrilli.BookstoreManager.repository.AuthorRepository;
import com.gabrielbarrilli.BookstoreManager.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class BookController {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/authors/{authorId}/books")
    public ResponseEntity<List<Book>> getAllBooksByAuthorId(@PathVariable(value = "authorId") Long authorId) {
        if (!authorRepository.existsById(authorId)) {
            throw new ResourceNotFoundException("Not found Author with id = " + authorId);
        }

        List<Book> books = bookRepository.findByAuthorId(authorId);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getBooksByAuthorId(@PathVariable(value = "id") Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Book with id = " + id));

        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @PostMapping("/authors/{authorId}/books")
    public ResponseEntity<Book> createBook(@PathVariable(value = "authorId") Long authorId,
                                                 @RequestBody Book bookRequest) {
        Book book = authorRepository.findById(authorId).map(author -> {
            bookRequest.setAuthor(author);
            return bookRepository.save(bookRequest);
        }).orElseThrow(() -> new ResourceNotFoundException("Not found Author with id = " + authorId));

        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable("id") Long id, @RequestBody Book bookRequest) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("BookId " + id + " not found"));

        book.setBookName(bookRequest.getBookName());
        book.setBookPages(bookRequest.getBookPages());
        book.setBookChapters(bookRequest.getBookChapters());
        book.setBookIsbn(bookRequest.getBookIsbn());
        book.setBookPublisherName(bookRequest.getBookPublisherName());

        return new ResponseEntity<>(bookRepository.save(book), HttpStatus.OK);
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<HttpStatus> deleteBook(@PathVariable("id") Long id) {
        bookRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/authors/{authorId}/books")
    public ResponseEntity<List<Book>> deleteAllBooksOfAuthor(@PathVariable(value = "authorId") Long authorId) {
        if (!authorRepository.existsById(authorId)) {
            throw new ResourceNotFoundException("Not found Author with id = " + authorId);
        }

        bookRepository.deleteByAuthorId(authorId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

