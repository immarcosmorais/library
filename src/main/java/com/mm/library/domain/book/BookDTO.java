package com.mm.library.domain.book;

import java.util.Date;

public record BookDTO(
        Long id,
        String title,
        String isbn,
        Date publicationDate,
        String publisherName,
        String authorName,
        BookStatus status,
        Date createdAt,
        Date updatedAt
) {
    public BookDTO(Book book) {
        this(
                book.getId(),
                book.getTitle(),
                book.getIsbn(),
                book.getPublicationDate(),
                book.getPublisher().getName(),
                book.getAuthor().getName(),
                book.getStatus(),
                book.getCreatedAt(),
                book.getUpdatedAt()
        );
    }
}