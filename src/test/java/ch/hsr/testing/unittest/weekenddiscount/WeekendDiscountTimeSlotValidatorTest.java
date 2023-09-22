package ch.hsr.testing.unittest.weekenddiscount;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.stream.Stream;


class WeekendDiscountTimeSlotValidatorTest {

    @Test
    @DisplayName("Test static object creator method")
    void testWeekendDiscountCreator() {
        Assertions.assertThat(WeekendDiscountTimeSlotValidator.createWeekendDiscountTimeSlotValidator(-3)).isNull();
    }

    /**
     * test method with method source
     * test cases from development team
     *
     * @param aWeekendNumber
     * @param aNow
     * @param aExpected
     */
    @ParameterizedTest(name = "{index} => weekend number: {0}  => date :{1} => expected :{2}")
    @MethodSource("createTestInputValid")
    void testWeekendDiscountSuccess(int aWeekendNumber, LocalDateTime aNow, boolean aExpected) {
        var lDiscountValidator = WeekendDiscountTimeSlotValidator.createWeekendDiscountTimeSlotValidator(aWeekendNumber);
        if (lDiscountValidator != null) {
            ;
            Assertions.assertThat(lDiscountValidator.isAuthorizedForDiscount(aNow)).isEqualTo(aExpected);
        }
    }

    private static Stream<Arguments> createTestInputValid() {
        return Stream.of(
                Arguments.of(4, LocalDateTime.of(2023, Month.SEPTEMBER, 23, 0, 0), true),
                Arguments.of(2, LocalDateTime.of(2023, Month.SEPTEMBER, 9, 0, 0), true),
                Arguments.of(1, LocalDateTime.of(2023, Month.OCTOBER, 1, 23, 59), false),
                Arguments.of(1, LocalDateTime.of(2023, Month.OCTOBER, 7, 23, 59), true),
                Arguments.of(1, LocalDateTime.of(2023, Month.OCTOBER, 8, 23, 59), true),
                Arguments.of(5, LocalDateTime.of(2023, Month.DECEMBER, 31, 23, 59), true),
                Arguments.of(1, LocalDateTime.of(2023, Month.OCTOBER, 2, 0, 1), false)
        );
    }

    /**
     * test method with csv import required by PO Hans Muster so that they can provide custom
     * data defined by the client
     *
     * @param aWeekendNumber
     * @param aNow
     * @param aExpected
     */
    @ParameterizedTest(name = "{index} => weekend number:{0} => date :{1} => expected :{2}")
    @CsvFileSource(resources = "/files/DiscountTimes.csv", numLinesToSkip = 1)
    void testWeekendDiscountSuccessCsv(int aWeekendNumber, LocalDateTime aNow, boolean aExpected) {
        var lDiscountValidator = WeekendDiscountTimeSlotValidator.createWeekendDiscountTimeSlotValidator(aWeekendNumber);
        if (lDiscountValidator != null) {
            ;
            Assertions.assertThat(lDiscountValidator.isAuthorizedForDiscount(aNow)).isEqualTo(aExpected);
        }
    }

}