package com.mm.library.domain.reservation.validations;

import com.mm.library.common.Validates;
import com.mm.library.domain.ValidationException;
import com.mm.library.domain.reservation.Reservation;
import com.mm.library.domain.reservation.ReservationStatus;
import org.springframework.stereotype.Component;

@Component
public class ValidateClosedReservations implements Validates<Reservation> {

    public void validate(Reservation reservation) {
        if (reservation.getStatus() == ReservationStatus.CLOSED)
            throw new ValidationException("You can not update a close reservation");
    }

}
