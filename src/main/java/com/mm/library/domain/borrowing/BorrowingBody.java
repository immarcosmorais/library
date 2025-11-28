package com.mm.library.domain.borrowing;

import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record BorrowingBody(
        @NotNull
        Long bookId,
        @NotNull
        Long readerId,
        BorrowingStatus status,
        Date expectedReturnDate
) {
}
