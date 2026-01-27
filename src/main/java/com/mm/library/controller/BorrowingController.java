package com.mm.library.controller;

import com.mm.library.domain.borrowing.BorrowingBody;
import com.mm.library.domain.borrowing.BorrowingDTO;
import com.mm.library.domain.borrowing.BorrowingService;
import com.mm.library.domain.user.User;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("borrowings")
@SecurityRequirement(name = "bearer-key")
public class BorrowingController {

    @Autowired
    BorrowingService borrowingService;

    @PostMapping
    public ResponseEntity save(@RequestBody @Valid BorrowingBody borrowingBody, UriComponentsBuilder uriBuilder) {
        BorrowingDTO borrowingDTO = new BorrowingDTO(this.borrowingService.save(borrowingBody));
        URI uri = uriBuilder.path("/borrowings/{id}").buildAndExpand(borrowingDTO.id()).toUri();
        return ResponseEntity.created(uri).body(borrowingDTO);
    }

    @GetMapping
    public ResponseEntity<Page<BorrowingDTO>> findAll(Pageable pageable, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(this.borrowingService.findAll(pageable, user).map(BorrowingDTO::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id) {
        return ResponseEntity.ok(new BorrowingDTO(this.borrowingService.findById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody @Valid BorrowingBody borrowingBody) {
        return ResponseEntity.ok(new BorrowingDTO(this.borrowingService.update(id, borrowingBody)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        this.borrowingService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-reservation/{reservationId}")
    public ResponseEntity byReservation(@PathVariable Long reservationId) {
        return ResponseEntity.ok(new BorrowingDTO(this.borrowingService.byReservation(reservationId)));
    }

    @GetMapping("/close/{id}")
    public ResponseEntity close(@PathVariable Long id) {
        return ResponseEntity.ok(new BorrowingDTO(this.borrowingService.close(id)));
    }

    @GetMapping("/return-book/{bookId}")
    public ResponseEntity returnBook(@PathVariable Long bookId) {
        return ResponseEntity.ok(new BorrowingDTO(this.borrowingService.returnBook(bookId)));
    }

}
