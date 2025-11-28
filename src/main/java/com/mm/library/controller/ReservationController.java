package com.mm.library.controller;

import com.mm.library.common.BaseCRUDController;
import com.mm.library.domain.reservation.ReservationBody;
import com.mm.library.domain.reservation.ReservationDTO;
import com.mm.library.domain.reservation.ReservationService;
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
@RequestMapping("reservations")
@SecurityRequirement(name = "bearer-key")
public class ReservationController implements BaseCRUDController<ReservationDTO, ReservationBody> {

    @Autowired
    ReservationService reservationService;

    @Override
    public ResponseEntity save(ReservationBody reservationBody, UriComponentsBuilder uriBuilder) {
        ReservationDTO reservationDTO = new ReservationDTO(this.reservationService.save(reservationBody));
        URI uri = uriBuilder.path("/reservations/{id}").buildAndExpand(reservationDTO.id()).toUri();
        return ResponseEntity.created(uri).body(reservationDTO);
    }

    @Override
    public ResponseEntity<Page<ReservationDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(this.reservationService.findAll(pageable).map(ReservationDTO::new));
    }

    @Override
    public ResponseEntity findById(Long id) {
        return ResponseEntity.ok(new ReservationDTO(this.reservationService.findById(id)));
    }

    @Override
    public ResponseEntity update(Long id, ReservationBody reservationBody) {
        return ResponseEntity.ok(new ReservationDTO(this.reservationService.update(id, reservationBody)));
    }

    @Override
    public ResponseEntity delete(Long id) {
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
