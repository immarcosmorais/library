package com.mm.library.domain.reader;

import java.time.LocalDateTime;

public record ReaderDTO(
        Long id,
        String name,
        String email,
        String phone,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public ReaderDTO(Reader reader) {
        this(
                reader.getId(),
                reader.getName(),
                reader.getEmail(),
                reader.getPhone(),
                reader.getCreatedAt(),
                reader.getUpdatedAt()
        );
    }
}
