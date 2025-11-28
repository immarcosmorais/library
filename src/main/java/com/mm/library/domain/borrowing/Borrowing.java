package com.mm.library.domain.borrowing;

import com.mm.library.common.AbstractEntity;
import com.mm.library.domain.book.Book;
import com.mm.library.domain.reader.Reader;
import com.mm.library.domain.reservation.Reservation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "borrowings")
@Entity(name = "Borrowing")
public class Borrowing extends AbstractEntity {
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", unique = true)
    private Book book;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reader_id")
    private Reader reader;
    @Column(name = "expected_return_date")
    private Date expectedReturnDate;
    @Column(name = "return_date")
    private Date returnDate;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private BorrowingStatus status;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", unique = true)
    private Reservation reservation;

    public Borrowing(BorrowingBody borrowingBody, Book book, Reader reader) {
        this.book = book;
        this.reader = reader;
        this.expectedReturnDate = borrowingBody.expectedReturnDate();
    }

    public void update(BorrowingBody borrowingBody, Book book, Reader reader) {
        this.book = book;
        this.reader = reader;
        this.expectedReturnDate = borrowingBody.expectedReturnDate();
    }

    public Reservation getReservation() {
        if (this.reservation == null)
            return new Reservation();
        return this.reservation;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        this.status = BorrowingStatus.OPENED;
    }


}
