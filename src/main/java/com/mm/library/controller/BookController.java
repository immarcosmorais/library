package com.mm.library.controller;

import com.mm.library.common.BaseCRUDController;
import com.mm.library.domain.book.BookBody;
import com.mm.library.domain.book.BookDTO;
import com.mm.library.domain.book.BookService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("books")
@SecurityRequirement(name = "bearer-key")
public class BookController implements BaseCRUDController<BookDTO, BookBody> {

    @Autowired
    BookService bookService;

    @Override
    public ResponseEntity save(BookBody bookBody, UriComponentsBuilder uriBuilder) {
        BookDTO bookDTO = new BookDTO(this.bookService.save(bookBody));
        URI uri = uriBuilder.path("/books/{id}").buildAndExpand(bookDTO.id()).toUri();
        return ResponseEntity.created(uri).body(bookDTO);
    }

    @Override
    public ResponseEntity<Page<BookDTO>> findAll(@PageableDefault(size = 999, sort = "title") Pageable pageable) {
        return ResponseEntity.ok(this.bookService.findAll(pageable).map(BookDTO::new));
    }

    @Override
    public ResponseEntity findById(Long id) {
        return ResponseEntity.ok(new BookDTO(this.bookService.findById(id)));
    }

    @Override
    public ResponseEntity update(Long id, BookBody bookBody) {
        return ResponseEntity.ok(new BookDTO(this.bookService.update(id, bookBody)));
    }

    @Override
    public ResponseEntity delete(Long id) {
        this.bookService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
