package com.szymanowski.marcin.reservation;

import io.vavr.control.Either;
import lombok.extern.log4j.Log4j2;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

@Component
@Log4j2
class BidsManager {
    private static final String JSON_URL = "https://gist.githubusercontent.com/fjahr/b164a446db285e393d8e4b36d17f4143/raw/75108c09a72a001a985d27b968a0ac5a867e830b/smarthost_hotel_guests.json";

    Either<String, List<Integer>> getBids() {
        try {
            URL url = new URI(JSON_URL).toURL();
            JSONParser parser = new JSONParser(url.openStream());

            return Either.right(parser.parseArray()
                    .stream()
                    .map(o -> Integer.valueOf(o.toString()))
                    .toList());
        } catch (URISyntaxException | IOException | ParseException e) {
            log.warn("Parse json bids exception", e);
            return Either.left("Parse json bids exception");
        }
    }
}
