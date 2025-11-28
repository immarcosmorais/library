package com.mm.library.domain.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record BookBody(
        @NotBlank
        String title,
        @NotBlank
        String isbn,
        @NotNull
        Date publicationDate,
        @NotNull
        Long publisherId,
        @NotNull
        Long authorId,
        BookStatus status
) {
    public BookBody(Book book) {
        this(
                book.getTitle(),
                book.getIsbn(),
                book.getPublicationDate(),
                book.getPublisher().getId(),
                book.getAuthor().getId(),
                book.getStatus()
        );
    }
}
