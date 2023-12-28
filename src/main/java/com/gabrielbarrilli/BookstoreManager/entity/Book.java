package com.gabrielbarrilli.BookstoreManager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_generator")
    private Long bookId;

    @Lob
    private String bookName;

    @Lob
    private Integer bookPages;

    @Lob
    private Integer bookChapters;

    @Lob
    private String bookIsbn;

    @Lob
    private String bookPublisherName;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tutorial_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Author author;

    public Book(String bookName, Integer bookPages, Integer bookChapters, String bookIsbn, String bookPublisherName) {
        this.bookName = bookName;
        this.bookPages = bookPages;
        this.bookChapters = bookChapters;
        this.bookIsbn = bookIsbn;
        this.bookPublisherName = bookPublisherName;
    }
}
