package com.szymanowski.marcin.reservation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/reservations")
@RequiredArgsConstructor
class ReservationController {

    private final ReservationService reservationService;

    @GetMapping("/{premiumRoomsCount}/premium-rooms/{economyRoomsCount}/economy-rooms")
    ResponseEntity<?> get(@PathVariable Integer premiumRoomsCount, @PathVariable Integer economyRoomsCount) {
        return reservationService.calculate(premiumRoomsCount, economyRoomsCount)
                .fold(
                        __ -> ResponseEntity.internalServerError().build(),
                        ResponseEntity::ok
                );
    }
}
