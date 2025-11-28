package com.mm.library.domain.borrowing.validations;

import com.mm.library.common.Validates;
import com.mm.library.domain.ValidationException;
import com.mm.library.domain.borrowing.Borrowing;
import com.mm.library.domain.borrowing.BorrowingStatus;
import org.springframework.stereotype.Component;

@Component
public class ValidateClosedBorrowings implements Validates<Borrowing> {

    public void validate(Borrowing borrowing) {
        if (borrowing.getStatus() == BorrowingStatus.CLOSED)
            throw new ValidationException("You can not update a close borrowing");
    }
}
