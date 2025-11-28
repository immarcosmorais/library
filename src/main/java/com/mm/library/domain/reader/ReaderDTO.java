package com.mm.library.domain.reader;

import java.util.Date;

public record ReaderDTO(
        Long id,
        String name,
        String email,
        String phone,
        Date createdAt,
        Date updatedAt
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
