package com.szymanowski.marcin.reservation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ReservationServiceImplTest {

    @Autowired
    private MockMvc mockMvc;

    private static Stream<TestData> prepareTestData() {
        return Stream.of(new TestData(3, 3, 3, 738, 3, 167),
                new TestData(7, 5, 6, 1054, 4, 189),
                new TestData(2, 7, 2, 583, 4, 189),
                new TestData(10, 1, 9, 1221, 1, 22),
                new TestData(0, 0, 0, 0, 0, 0),
                new TestData(1, 0, 1, 374, 0, 0),
                new TestData(0, 1, 0, 0, 1, 99)
        );
    }

    @ParameterizedTest
    @MethodSource("prepareTestData")
    void shouldReturnCalculatedUsage(TestData testData) throws Exception {
        this.mockMvc.perform(
                        get("/api/v1/reservations/{premiumAvailable}/premium-rooms/{economyAvailable}/economy-rooms",
                                testData.premiumAvailable, testData.economyAvailable))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.premiumUsage.roomsCount").value(testData.expectedPremiumRoomsCount))
                .andExpect(jsonPath("$.premiumUsage.price").value(testData.expectedPremiumPrice))
                .andExpect(jsonPath("$.economyUsage.roomsCount").value(testData.expectedEconomyRoomsCount))
                .andExpect(jsonPath("$.economyUsage.price").value(testData.expectedEconomyPrice));
    }

    @Test
    void shouldReturnBadRequest() throws Exception {
        this.mockMvc.perform(
                        get("/api/v1/reservations/{premiumAvailable}/premium-rooms/{economyAvailable}/economy-rooms",
                                "aaaa", 1))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    private record TestData(int premiumAvailable, int economyAvailable, int expectedPremiumRoomsCount,
                            int expectedPremiumPrice, int expectedEconomyRoomsCount, int expectedEconomyPrice) {
    }

}