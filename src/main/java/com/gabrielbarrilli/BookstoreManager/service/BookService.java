package com.gabrielbarrilli.BookstoreManager.service;

import com.gabrielbarrilli.BookstoreManager.dto.MessageResponseDTO;
import com.gabrielbarrilli.BookstoreManager.entity.Book;
import com.gabrielbarrilli.BookstoreManager.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class BookService {

    private BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @PostMapping
    public MessageResponseDTO create(Book book) {
        Book savedBook = bookRepository.save(book);

        /*var resposta = MessageResponseDTO.builder().message("Book created with ID " + savedBook.getId()).build();
        return resposta;*/

        return MessageResponseDTO.builder().message("Book created with ID " + savedBook.getId()).build();
    }
}
