package com.mm.library.domain.book;

import com.mm.library.common.AbstractEntity;
import com.mm.library.domain.author.Author;
import com.mm.library.domain.publisher.Publisher;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "books")
@Entity(name = "Book")
public class Book extends AbstractEntity {
    private String title;
    private String isbn;
    @Column(name = "publication_date")
    private Date publicationDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private BookStatus status;

    public Book(BookBody bookBody, Publisher publisher, Author author) {
        this.setDeleted(false);
        this.title = bookBody.title();
        this.isbn = bookBody.isbn();
        this.publicationDate = bookBody.publicationDate();
        this.publisher = publisher;
        this.author = author;
//        this.status = bookBody.status();
    }

    public void update(BookBody bookBody, Publisher publisher, Author author) {
        this.title = bookBody.title();
        this.isbn = bookBody.isbn();
        this.publicationDate = bookBody.publicationDate();
        this.publisher = publisher;
        this.author = author;
//        this.status = bookBody.status();
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        this.status = BookStatus.AVAILABLE;
    }
}
