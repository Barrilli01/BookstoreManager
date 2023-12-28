package com.gabrielbarrilli.BookstoreManager.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_generator")
    private Long id;

    @Column(name = "author_name", nullable = false, unique = true, length = 150)
    private String authorName;

    @Column(name = "author_age", nullable = false)
    private Integer authorAge;

    @Column(name = "author_published")
    private boolean published;

    public Author(String authorName, Integer authorAge, boolean published) {
        this.authorName = authorName;
        this.authorAge = authorAge;
        this.published = published;
    }
}
