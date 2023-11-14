package com.szymanowski.marcin.reservation;

import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
class ReservationServiceImpl implements ReservationService {

    private final BidsManager bidsManager;

    private static final int PREMIUM_ECONOMY_THRESHOLD = 100;
    private static final int ZERO = 0;

    public Either<String, ReservationResponse> calculate(int premiumAvailability, int economyAvailability) {
        return bidsManager.getBids()
                .map(bids -> getReservationResponse(premiumAvailability, economyAvailability, bids));
    }

    private ReservationResponse getReservationResponse(int premiumAvailability, int economyAvailability,
            List<Integer> bids) {
        var sortedBids = bids.stream().sorted(Comparator.reverseOrder()).toList();

        var premiumRoomBids = sortedBids.stream()
                .filter(bid -> bid >= PREMIUM_ECONOMY_THRESHOLD)
                .limit(premiumAvailability).toList();
        var economyRoomBids = sortedBids.stream()
                .filter(bid -> bid < PREMIUM_ECONOMY_THRESHOLD)
                .toList();

        var premiumRoomsAvailableForEconomyCustomers = Math.max(ZERO, premiumAvailability - premiumRoomBids.size());
        var economyRoomsGap = economyRoomBids.size() - economyAvailability;
        var someEconomyBidsShouldGetPremiumRooms = economyRoomsGap > ZERO
                && premiumRoomsAvailableForEconomyCustomers > ZERO;

        if (someEconomyBidsShouldGetPremiumRooms) {
            return getReservationResponseWithUpgradedEconomyBids(economyAvailability,
                    premiumRoomsAvailableForEconomyCustomers,
                    economyRoomsGap,
                    premiumRoomBids, economyRoomBids);
        }

        var economyBidsResult = economyRoomBids.subList(ZERO, Math.min(economyAvailability, economyRoomBids.size()));

        return new ReservationResponse(
                RoomUsageResponse.prepare(premiumRoomBids),
                RoomUsageResponse.prepare(economyBidsResult)
        );
    }

    private ReservationResponse getReservationResponseWithUpgradedEconomyBids(
            int economyAvailability,
            int premiumRoomsAvailableForEconomyCustomers,
            int economyRoomsGap,
            List<Integer> premiumRoomBids,
            List<Integer> economyRoomBids
    ) {
        int upgradedEconomyBids = Math.min(premiumRoomsAvailableForEconomyCustomers, economyRoomsGap);
        var premiumResult = Stream.concat(premiumRoomBids.stream(),
                        economyRoomBids.subList(ZERO, upgradedEconomyBids).stream())
                .toList();

        var economyResult = economyRoomBids.subList(upgradedEconomyBids,
                Math.min(economyRoomBids.size(), upgradedEconomyBids + economyAvailability));

        return new ReservationResponse(
                RoomUsageResponse.prepare(premiumResult),
                RoomUsageResponse.prepare(economyResult)
        );
    }
}
