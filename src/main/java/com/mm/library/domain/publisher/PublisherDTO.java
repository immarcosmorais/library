package com.mm.library.domain.publisher;

import java.util.Date;

public record PublisherDTO(
        Long id,
        String name,
        String country,
        Date createdAt,
        Date updatedAt
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
