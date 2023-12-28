package com.gabrielbarrilli.BookstoreManager.repository;

import com.gabrielbarrilli.BookstoreManager.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByAuthorId(Long postId);

    @Transactional
    void deleteByAuthorId(Long authorId);
}
