package com.mm.library.domain.author;

import java.util.Date;

public record AuthorDTO(
        Long id,
        String name,
        Date birthDate,
        Date createdAt,
        Date updatedAt
) {
    public AuthorDTO(Author author) {
        this(
                author.getId(),
                author.getName(),
                author.getBirthDate(),
                author.getCreatedAt(),
                author.getUpdatedAt()
        );
    }
}
