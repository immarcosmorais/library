package com.mm.library.controller;

import com.mm.library.common.BaseCRUDController;
import com.mm.library.domain.borrowing.BorrowingBody;
import com.mm.library.domain.borrowing.BorrowingDTO;
import com.mm.library.domain.borrowing.BorrowingService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("borrowings")
@SecurityRequirement(name = "bearer-key")
public class BorrowingController implements BaseCRUDController<BorrowingDTO, BorrowingBody> {

    @Autowired
    BorrowingService borrowingService;

    @Override
    public ResponseEntity save(BorrowingBody borrowingBody, UriComponentsBuilder uriBuilder) {
        BorrowingDTO borrowingDTO = new BorrowingDTO(this.borrowingService.save(borrowingBody));
        URI uri = uriBuilder.path("/borrowings/{id}").buildAndExpand(borrowingDTO.id()).toUri();
        return ResponseEntity.created(uri).body(borrowingDTO);
    }

    @Override
    public ResponseEntity<Page<BorrowingDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(this.borrowingService.findAll(pageable).map(BorrowingDTO::new));
    }

    @Override
    public ResponseEntity findById(Long id) {
        return ResponseEntity.ok(new BorrowingDTO(this.borrowingService.findById(id)));
    }

    @Override
    public ResponseEntity update(Long id, BorrowingBody borrowingBody) {
        return ResponseEntity.ok(new BorrowingDTO(this.borrowingService.update(id, borrowingBody)));
    }

    @Override
    public ResponseEntity delete(Long id) {
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
