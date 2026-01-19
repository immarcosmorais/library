package com.mm.library.util;

import com.mm.library.domain.book.Book;

public class BookCreator {
    public static Book createBookToBeSaved() {
        return new Book();
    }
}
