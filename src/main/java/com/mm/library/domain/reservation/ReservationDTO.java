package com.mm.library.domain.reservation;

import java.time.LocalDateTime;
import java.util.Date;

public record ReservationDTO(
        Long id,
        String bookTitle,
        String readerName,
        Date deadline,
        ReservationStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public ReservationDTO(Reservation reservation) {
        this(
                reservation.getId(),
                reservation.getBook().getTitle(),
                reservation.getReader().getName(),
                reservation.getDeadline(),
                reservation.getStatus(),
                reservation.getCreatedAt(),
                reservation.getUpdatedAt()
        );
    }
}
