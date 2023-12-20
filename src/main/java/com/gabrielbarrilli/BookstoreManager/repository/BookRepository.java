package com.gabrielbarrilli.BookstoreManager.repository;

import com.gabrielbarrilli.BookstoreManager.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

}
