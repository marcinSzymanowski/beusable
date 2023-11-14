package com.szymanowski.marcin.reservation;

import java.util.List;

record RoomUsageResponse(Integer roomsCount, Integer price) {

    static RoomUsageResponse prepare(List<Integer> bids) {
        return new RoomUsageResponse(bids.size(), bids.stream().reduce(0, Integer::sum));
    }

}
