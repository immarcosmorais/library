package com.mm.library.controller;

import com.mm.library.domain.reservation.ReservationBody;
import com.mm.library.domain.reservation.ReservationDTO;
import com.mm.library.domain.reservation.ReservationService;
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
@RequestMapping("reservations")
@SecurityRequirement(name = "bearer-key")
public class ReservationController {

    @Autowired
    ReservationService reservationService;

    @PostMapping
    public ResponseEntity save(@RequestBody @Valid ReservationBody reservationBody, UriComponentsBuilder uriBuilder) {
        ReservationDTO reservationDTO = new ReservationDTO(this.reservationService.save(reservationBody));
        URI uri = uriBuilder.path("/reservations/{id}").buildAndExpand(reservationDTO.id()).toUri();
        return ResponseEntity.created(uri).body(reservationDTO);
    }

    @GetMapping
    public ResponseEntity<Page<ReservationDTO>> findAll(Pageable pageable, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(this.reservationService.findAll(pageable, user).map(ReservationDTO::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id) {
        return ResponseEntity.ok(new ReservationDTO(this.reservationService.findById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody @Valid ReservationBody reservationBody) {
        return ResponseEntity.ok(new ReservationDTO(this.reservationService.update(id, reservationBody)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        this.reservationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("close/{id}")
    public ResponseEntity close(@PathVariable Long id) {
        return ResponseEntity.ok(new ReservationDTO(this.reservationService.close(id)));
    }

    @GetMapping("open/{id}")
    public ResponseEntity open(@PathVariable Long id) {
        return ResponseEntity.ok(new ReservationDTO(this.reservationService.open(id)));
    }


}
