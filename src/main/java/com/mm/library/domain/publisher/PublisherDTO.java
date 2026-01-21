package com.mm.library.domain.publisher;

import java.time.LocalDateTime;

public record PublisherDTO(
        Long id,
        String name,
        String country,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public PublisherDTO(Publisher publisher) {
        this(
                publisher.getId(),
                publisher.getName(),
                publisher.getCountry(),
                publisher.getCreatedAt(),
                publisher.getUpdatedAt()
        );
    }
}
