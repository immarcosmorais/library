package com.mm.library.domain.reservation;

import com.mm.library.common.AbstractEntity;
import com.mm.library.domain.book.Book;
import com.mm.library.domain.reader.Reader;
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
@Table(name = "Reservations")
@Entity(name = "Reservation")
public class Reservation extends AbstractEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", unique = true)
    private Book book;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reader_id")
    private Reader reader;
    @Column(name = "deadline")
    private Date deadline;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private ReservationStatus status;

    public Reservation(ReservationBody reservationBody, Book book, Reader reader) {
        this.book = book;
        this.reader = reader;
        this.deadline = reservationBody.deadline();
    }

    public void update(ReservationBody reservationBody, Book book, Reader reader) {
        this.deadline = reservationBody.deadline();
        this.book = book;
        this.reader = reader;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        this.status = ReservationStatus.OPENED;
    }
}
