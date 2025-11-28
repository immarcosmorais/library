package com.mm.library.domain.borrowing;

import java.util.Date;

public record BorrowingDTO(
        Long id,
        String bookTitle,
        String readerName,
        Long reservationId,
        Date expectedReturnDate,
        BorrowingStatus status,
        Date returnDate,
        Date createdAt,
        Date updatedAt
) {
    public BorrowingDTO(Borrowing borrowing) {
        this(
                borrowing.getId(),
                borrowing.getBook().getTitle(),
                borrowing.getReader().getName(),
                borrowing.getReservation().getId(),
                borrowing.getExpectedReturnDate(),
                borrowing.getStatus(),
                borrowing.getReturnDate(),
                borrowing.getCreatedAt(),
                borrowing.getUpdatedAt()
        );
    }
}
