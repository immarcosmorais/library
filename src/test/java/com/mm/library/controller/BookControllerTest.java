package com.mm.library.controller;

import com.mm.library.domain.book.BookService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests for BookController")
class BookControllerTest {

    @InjectMocks
    private BookController controller;

    @Mock
    private BookService service;

    @BeforeEach
    void setUp() {
//        Book validBook = BookCreator.createBookToBeSaved();


    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void save() {
    }

    @Test
    void findAll() {
    }

    @Test
    void findById() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}