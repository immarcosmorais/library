package com.mm.library.domain.book.validations;

import com.mm.library.common.Validates;
import com.mm.library.domain.ValidationException;
import com.mm.library.domain.book.Book;
import com.mm.library.domain.book.BookStatus;
import org.springframework.stereotype.Component;

@Component
public class ValidateReservedBooks implements Validates<Book> {

    public void validate(Book book) {
        if (book.getStatus() != BookStatus.RESERVED) {
            throw new ValidationException(String.format("Book '%d' can not be reserved or borrowed", book.getId()));
        }

    }

}
