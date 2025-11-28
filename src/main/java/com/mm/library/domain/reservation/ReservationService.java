package com.mm.library.domain.reservation;

import com.mm.library.common.BaseCRUDService;
import com.mm.library.common.Validates;
import com.mm.library.domain.book.Book;
import com.mm.library.domain.book.BookService;
import com.mm.library.domain.book.BookStatus;
import com.mm.library.domain.book.validations.ValidateAvailableBooks;
import com.mm.library.domain.reader.Reader;
import com.mm.library.domain.reader.ReaderService;
import com.mm.library.domain.reservation.validations.ValidateClosedReservations;
import com.mm.library.domain.reservation.validations.ValidateDeadline;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class ReservationService implements BaseCRUDService<Reservation, ReservationBody> {

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    BookService bookService;

    @Autowired
    ReaderService readerService;

    @Autowired
    List<Validates<Reservation>> validateReservations;

    @Autowired
    List<Validates<Book>> validateBooks;


    @Override
    @Transactional
    public Reservation save(ReservationBody body) {
        Book book = this.bookService.findById(body.bookId());
        Reader reader = this.readerService.findById(body.readerId());
        validateBooks.forEach(v -> v.validate(book));
        if (body.deadline() == null) {
            Date deadLine = Date.from(Instant.now().plus(3, ChronoUnit.DAYS));
            body = new ReservationBody(body.bookId(), body.readerId(), body.status(), deadLine);
        }
        Reservation reservationToBeSaved = new Reservation(body, book, reader);
        validateReservations.forEach(v -> v.validate(reservationToBeSaved));
        this.bookService.updateBookStatus(body.bookId(), BookStatus.RESERVED);
        return this.reservationRepository.save(reservationToBeSaved);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Reservation> findAll(Pageable pageable) {
        return this.reservationRepository.findAllByDeletedFalse(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Reservation findById(Long id) {
        return this.findByIdOrThrowException(id);
    }

    @Override
    @Transactional
    public Reservation update(Long id, ReservationBody body) {
        Reservation reservationToBeUpdated = this.findById(id);
        new ValidateClosedReservations().validate(reservationToBeUpdated);
        Book book = this.bookService.findById(body.bookId());
        Reader reader = this.readerService.findById(body.readerId());
        if (!Objects.equals(reservationToBeUpdated.getBook().getId(), book.getId())) {
            new ValidateAvailableBooks().validate(book);
            this.bookService.updateBookStatus(reservationToBeUpdated.getBook().getId(), BookStatus.AVAILABLE);
            this.bookService.updateBookStatus(body.bookId(), BookStatus.RESERVED);
        }
        if (body.deadline() == null) {
            body = new ReservationBody(body.bookId(), body.readerId(), reservationToBeUpdated.getStatus(), reservationToBeUpdated.getDeadline());
        }
        reservationToBeUpdated.update(body, book, reader);
        new ValidateDeadline().validate(reservationToBeUpdated);
        return this.reservationRepository.save(reservationToBeUpdated);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Reservation reservationToBeDeleted = this.reservationRepository.getReferenceById(id);
        this.bookService.updateBookStatus(reservationToBeDeleted.getBook().getId(), BookStatus.AVAILABLE);
        reservationToBeDeleted.setDeleted(true);
        reservationToBeDeleted.setStatus(ReservationStatus.CLOSED);
        this.reservationRepository.save(reservationToBeDeleted);
    }

    @Override
    @Transactional
    public void destroy(Long id) {
        this.reservationRepository.delete(this.reservationRepository.getReferenceById(id));
    }

    @Transactional
    private Reservation findByIdOrThrowException(Long id) {
        return this.reservationRepository.findByIdAndDeletedFalse(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Reservation with id %d not found", id))
        );
    }

    @Transactional
    public Reservation findByReaderIdAndBookId(Long readerId, Long bookId) {
        return this.reservationRepository.findByReaderIdAndBookIdAndDeletedFalseAndStatus(
                readerId,
                bookId,
                ReservationStatus.OPENED).orElse(null);
    }

    @Transactional
    public void updateReservationStatus(Long id, ReservationStatus reservationStatus) {
        Reservation reservation = this.reservationRepository.getReferenceById(id);
        reservation.setStatus(reservationStatus);
        this.reservationRepository.save(reservation);
    }

    @Transactional
    public Reservation close(Long id) {
        Reservation reservation = this.findById(id);
        if (reservation.getStatus() == ReservationStatus.OPENED) {
            reservation.setStatus(ReservationStatus.CLOSED);
            this.bookService.updateBookStatus(reservation.getBook().getId(), BookStatus.AVAILABLE);
            return this.reservationRepository.save(reservation);
        }
        return reservation;
    }

    @Transactional
    public Reservation open(Long id) {
        Reservation reservation = this.findById(id);
        if (reservation.getStatus() == ReservationStatus.CLOSED) {
            Book book = this.bookService.findById(reservation.getBook().getId());
            new ValidateAvailableBooks().validate(book);
            this.bookService.updateBookStatus(book.getId(), BookStatus.RESERVED);
            reservation.setStatus(ReservationStatus.OPENED);
            this.bookService.updateBookStatus(reservation.getBook().getId(), BookStatus.AVAILABLE);
            return this.reservationRepository.save(reservation);
        }
        return reservation;
    }
}
