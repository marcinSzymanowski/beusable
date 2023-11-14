package com.szymanowski.marcin.reservation;

import io.vavr.control.Either;

interface ReservationService {
    Either<String, ReservationResponse> calculate(int premiumAvailability, int regularAvailability);
}
