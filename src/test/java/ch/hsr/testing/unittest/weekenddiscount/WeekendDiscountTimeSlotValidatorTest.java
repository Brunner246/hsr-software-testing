package ch.hsr.testing.unittest.weekenddiscount;

import ch.hsr.testing.unittest.tdd.romannumbers.RomanNumberConverter;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.stream.Stream;

import static org.junit.Assert.assertTrue;


class WeekendDiscountTimeSlotValidatorTest {

    @ParameterizedTest(name = "{index} => weekend number:{0} => date :{1}")
    @MethodSource("createTestInput")
    void testWeekendDiscount(int aWeekendNumber, LocalDateTime aNow) {
        var lDiscountValidator = WeekendDiscountTimeSlotValidator.createWeekendDiscountTimeSlotValidator(aWeekendNumber);
        if (lDiscountValidator != null) {;
            Assertions.assertTrue(lDiscountValidator.isAuthorizedForDiscount(aNow));
        }
    }

    private static Stream<Arguments> createTestInput() {
        return Stream.of(
                Arguments.of(4, LocalDateTime.of(2023, Month.SEPTEMBER, 23, 0, 0)),
                Arguments.of(2, LocalDateTime.of(2023, Month.SEPTEMBER, 9, 0, 0)),
                Arguments.of(1, LocalDateTime.of(2023, Month.OCTOBER, 1, 23, 59))
        );
    }
}