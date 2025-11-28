package com.mm.library.domain.borrowing;

import com.mm.library.common.BaseCRUDService;
import com.mm.library.common.Validates;
import com.mm.library.domain.book.Book;
import com.mm.library.domain.book.BookService;
import com.mm.library.domain.book.BookStatus;
import com.mm.library.domain.borrowing.validations.ValidateClosedBorrowings;
import com.mm.library.domain.reader.Reader;
import com.mm.library.domain.reader.ReaderService;
import com.mm.library.domain.reservation.Reservation;
import com.mm.library.domain.reservation.ReservationService;
import com.mm.library.domain.reservation.ReservationStatus;
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
public class BorrowingService implements BaseCRUDService<Borrowing, BorrowingBody> {

    @Autowired
    BorrowingRepository borrowingRepository;

    @Autowired
    ReservationService reservationService;

    @Autowired
    BookService bookService;

    @Autowired
    ReaderService readerService;

    @Autowired
    List<Validates<Borrowing>> validateBorrowings;

    @Autowired
    List<Validates<Reservation>> validateReservations;

    @Autowired
    List<Validates<Book>> validateBooks;


    @Override
    public Borrowing save(BorrowingBody body) {
        Book book = this.bookService.findById(body.bookId());
        validateBooks.forEach(v -> v.validate(book));
        if (body.expectedReturnDate() == null) {
            Date expectedReturnDate = Date.from(Instant.now().plus(7, ChronoUnit.DAYS));
            body = new BorrowingBody(body.bookId(), body.readerId(), body.status(), expectedReturnDate);
        }
        Reader reader = this.readerService.findById(body.readerId());
        this.bookService.updateBookStatus(book.getId(), BookStatus.BORROWED);
        Borrowing borrowingToBeSaved = new Borrowing(body, book, reader);
        return this.borrowingRepository.save(borrowingToBeSaved);
    }

    @Override
    public Page<Borrowing> findAll(Pageable pageable) {
        return this.borrowingRepository.findAllByDeletedFalse(pageable);
    }

    @Override
    public Borrowing findById(Long id) {
        return this.findByIdOrThrowException(id);
    }

    @Override
    public Borrowing update(Long id, BorrowingBody body) {
        Borrowing borrowingToBeUpdated = this.findById(id);
        new ValidateClosedBorrowings().validate(borrowingToBeUpdated);
        Book book = this.bookService.findById(body.bookId());
        if (!Objects.equals(borrowingToBeUpdated.getBook().getId(), book.getId())) {
            validateBooks.forEach(v -> v.validate(book));
            this.bookService.updateBookStatus(borrowingToBeUpdated.getBook().getId(), BookStatus.AVAILABLE);
            this.bookService.updateBookStatus(book.getId(), BookStatus.BORROWED);
        }
        if (body.expectedReturnDate() == null) {
            body = new BorrowingBody(body.bookId(), body.readerId(), body.status(), borrowingToBeUpdated.getExpectedReturnDate());
        }
        Reader reader = this.readerService.findById(body.readerId());
        Borrowing borrowingToBeSaved = new Borrowing(body, book, reader);
        return this.borrowingRepository.save(borrowingToBeSaved);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Borrowing borrowingToBeDeleted = this.borrowingRepository.getReferenceById(id);
        borrowingToBeDeleted.setDeleted(true);
        this.bookService.updateBookStatus(borrowingToBeDeleted.getId(), BookStatus.AVAILABLE);
        this.borrowingRepository.save(borrowingToBeDeleted);
    }

    @Override
    @Transactional
    public void destroy(Long id) {
        this.borrowingRepository.delete(this.borrowingRepository.getReferenceById(id));
    }

    private Borrowing findByIdOrThrowException(Long id) {
        return this.borrowingRepository.findByIdAndDeletedFalse(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Borrowing with id %d not found", id))
        );
    }

    public Borrowing byReservation(Long reservationId) {
        Reservation reservation = this.reservationService.findById(reservationId);
        validateReservations.forEach(v -> v.validate(reservation));
        Book book = reservation.getBook();
        Reader reader = reservation.getReader();
        validateBooks.forEach(v -> v.validate(book));
        this.reservationService.updateReservationStatus(reservation.getId(), ReservationStatus.CLOSED);
        Date expectedReturnDate = Date.from(Instant.now().plus(7, ChronoUnit.DAYS));
        BorrowingBody body = new BorrowingBody(book.getId(), reader.getId(), BorrowingStatus.OPENED, expectedReturnDate);
        this.bookService.updateBookStatus(book.getId(), BookStatus.BORROWED);
        Borrowing borrowingToBeSaved = new Borrowing(body, book, reader);
        borrowingToBeSaved.setReservation(reservation);
        return this.borrowingRepository.save(borrowingToBeSaved);
    }

    public Borrowing close(Long id) {
        Borrowing borrowing = this.findById(id);
        if (borrowing.getStatus() == BorrowingStatus.OPENED) {
            this.bookService.updateBookStatus(borrowing.getBook().getId(), BookStatus.AVAILABLE);
            borrowing.setStatus(BorrowingStatus.CLOSED);
            borrowing.setReturnDate(new Date());
            return this.borrowingRepository.save(borrowing);
        }
        return borrowing;
    }

    public Borrowing returnBook(Long bookId) {
        Borrowing borrowing = this.borrowingRepository.findByBookIdAndDeletedFalse(bookId).orElseThrow(
                () -> new EntityNotFoundException(String.format("Didn't found any borrowing for book with id $d", bookId))
        );
        if (borrowing.getStatus() == BorrowingStatus.OPENED) {
            this.bookService.updateBookStatus(borrowing.getBook().getId(), BookStatus.AVAILABLE);
            borrowing.setStatus(BorrowingStatus.CLOSED);
            borrowing.setReturnDate(new Date());
            return this.borrowingRepository.save(borrowing);
        }
        return borrowing;
    }
}
