package ch.hsr.testing.unittest.weekenddiscount;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;

class WeekendDiscountTimeSlotValidatorTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void initializeWithWeekendNumber() {
    }

    @Test
    void isAuthorizedForDiscount() {
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/files/discountTimes.csv", numLinesToSkip = 1)
    public void testIsAuthorizedForDiscount(LocalDateTime dateTime, int weekendNumber) throws IllegalWeekendNumberException {
        WeekendDiscountTimeSlotValidator validator = new WeekendDiscountTimeSlotValidator();
        validator.initializeWithWeekendNumber(weekendNumber);

        boolean result = validator.isAuthorizedForDiscount(dateTime);

        assertTrue(result);
    }

}