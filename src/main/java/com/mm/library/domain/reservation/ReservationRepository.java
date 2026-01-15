package com.mm.library.domain.reservation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Page<Reservation> findAllByDeletedFalse(Pageable pageable);

    Optional<Reservation> findByIdAndDeletedFalse(Long Id);

    Optional<Reservation> findByReaderIdAndBookIdAndDeletedFalseAndStatus(Long readerId, Long bookId, ReservationStatus status);
}