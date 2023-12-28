package com.gabrielbarrilli.BookstoreManager.controller;

import com.gabrielbarrilli.BookstoreManager.entity.Author;
import com.gabrielbarrilli.BookstoreManager.exceptions.ResourceNotFoundException;
import com.gabrielbarrilli.BookstoreManager.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class AuthorController {

    @Autowired
    AuthorRepository authorRepository;

    // Endpoint para obter todos os autores
    @GetMapping("/authors")
    public ResponseEntity<List<Author>> getAllAuthors(@RequestParam(required = false) String authorName) {
        try {
            List<Author> authors = new ArrayList<Author>();

            if (authorName == null)
                authorRepository.findAll().forEach(authors::add);
            else
                authorRepository.findByAuthorNameContaining(authorName).forEach(authors::add);

            if (authors.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(authors, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/authors/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable("id") Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + id));

        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    @PostMapping("/authors")
    public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
        Author _author = authorRepository.save(new Author(author.getAuthorName(), author.getAuthorAge(), true));
        return new ResponseEntity<>(_author, HttpStatus.CREATED);
    }

    @PutMapping("/authors/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable("id") Long id, @RequestBody Author author) {
        Author _author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Author with id = " + id));

            _author.setAuthorName(author.getAuthorName());
            _author.setAuthorAge(author.getAuthorAge());
            _author.setPublished(author.isPublished());

        return new ResponseEntity<>(authorRepository.save(_author), HttpStatus.OK);
    }

    @DeleteMapping("/authors/{id}")
    public ResponseEntity<HttpStatus> deleteAuthor(@PathVariable("id") Long id) {
        try {
            authorRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/authors")
    public ResponseEntity<HttpStatus> deleteAllAuthors() {
        authorRepository.deleteAll();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/authors/alive")
    public ResponseEntity<List<Author>> findByPublished() {
        List<Author> authors = authorRepository.findByPublished(true);

        if (authors.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(authors, HttpStatus.OK);
    }
}

