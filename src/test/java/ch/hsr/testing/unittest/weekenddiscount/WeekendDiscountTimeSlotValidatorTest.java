package ch.hsr.testing.unittest.weekenddiscount;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.stream.Stream;


class WeekendDiscountTimeSlotValidatorTest {

    // TODO test static creator method

    @ParameterizedTest(name = "{index} => weekend number:{0} => date :{1}")
    @MethodSource("createTestInputValid")
    void testWeekendDiscountSuccess(int aWeekendNumber, LocalDateTime aNow) {
        var lDiscountValidator = WeekendDiscountTimeSlotValidator.createWeekendDiscountTimeSlotValidator(aWeekendNumber);
        if (lDiscountValidator != null) {;
            Assertions.assertTrue(lDiscountValidator.isAuthorizedForDiscount(aNow));
        }
    }

    private static Stream<Arguments> createTestInputValid() {
        return Stream.of(
                Arguments.of(4, LocalDateTime.of(2023, Month.SEPTEMBER, 23, 0, 0)),
                Arguments.of(2, LocalDateTime.of(2023, Month.SEPTEMBER, 9, 0, 0)),
                Arguments.of(1, LocalDateTime.of(2023, Month.OCTOBER, 1, 23, 59))
        );
    }

    @ParameterizedTest(name = "{index} => weekend number:{0} => date :{1}")
    @MethodSource("createTestInputInValid")
    void testWeekendDiscountFail(int aWeekendNumber, LocalDateTime aNow) {
        var lDiscountValidator = WeekendDiscountTimeSlotValidator.createWeekendDiscountTimeSlotValidator(aWeekendNumber);
        if (lDiscountValidator != null) {;
            Assertions.assertFalse(lDiscountValidator.isAuthorizedForDiscount(aNow));
        }
    }

    private static Stream<Arguments> createTestInputInValid() {
        return Stream.of(
                Arguments.of(1, LocalDateTime.of(2023, Month.OCTOBER, 2, 0, 1))
        );
    }
}