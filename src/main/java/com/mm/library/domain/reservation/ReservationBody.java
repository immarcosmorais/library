package com.mm.library.domain.reservation;

import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record ReservationBody(
        @NotNull
        Long bookId,
        @NotNull
        Long readerId,
        ReservationStatus status,
        Date deadline
) {
    public ReservationBody(Reservation reservation) {
        this(
                reservation.getBook().getId(),
                reservation.getReader().getId(),
                reservation.getStatus(),
                reservation.getDeadline()
        );
    }
}
