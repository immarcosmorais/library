package com.mm.library.domain.reservation.validations;

import com.mm.library.common.Validates;
import com.mm.library.domain.ValidationException;
import com.mm.library.domain.reservation.Reservation;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ValidateDeadline implements Validates<Reservation> {

    public void validate(Reservation Reservation) {
        Date now = new Date();
        if (Reservation.getDeadline().before(now)) {
            throw new ValidationException("Deadline must be a future date");
        }
    }
}
