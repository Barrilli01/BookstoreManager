package com.gabrielbarrilli.BookstoreManager.repository;

import com.gabrielbarrilli.BookstoreManager.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    List<Author> findByPublished(boolean published);
    List<Author> findByAuthorNameContaining(String authorName);
}
