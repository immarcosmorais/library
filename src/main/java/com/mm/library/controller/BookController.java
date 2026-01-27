package com.mm.library.controller;

import com.mm.library.domain.book.BookBody;
import com.mm.library.domain.book.BookDTO;
import com.mm.library.domain.book.BookService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("books")
@SecurityRequirement(name = "bearer-key")
public class BookController {

    @Autowired
    BookService bookService;

    @PostMapping
    public ResponseEntity save(@RequestBody @Valid BookBody bookBody, UriComponentsBuilder uriBuilder) {
        BookDTO bookDTO = new BookDTO(this.bookService.save(bookBody));
        URI uri = uriBuilder.path("/books/{id}").buildAndExpand(bookDTO.id()).toUri();
        return ResponseEntity.created(uri).body(bookDTO);
    }

    @GetMapping
    public ResponseEntity<Page<BookDTO>> findAll(@PageableDefault(size = 999, sort = "title") Pageable pageable) {
        return ResponseEntity.ok(this.bookService.findAll(pageable).map(BookDTO::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id) {
        return ResponseEntity.ok(new BookDTO(this.bookService.findById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody @Valid BookBody bookBody) {
        return ResponseEntity.ok(new BookDTO(this.bookService.update(id, bookBody)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        this.bookService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
