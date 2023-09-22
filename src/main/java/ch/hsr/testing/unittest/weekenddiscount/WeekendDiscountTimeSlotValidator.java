package ch.hsr.testing.unittest.weekenddiscount;

/*
 * HSR Hochschule für Technik Rapperswil
 * Master of Advanced Studies in Software Engineering
 * Module Software Testing
 *
 * Thomas Briner, thomas.briner@gmail.com
 */

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class WeekendDiscountTimeSlotValidator {

    public static final List<DayOfWeek> WEEKEND_DAYS = Arrays.asList(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);

    private final Integer weekendNumber;

    /**
     * static creator method that validates the input param before constructing the object.
     * with that we don't have to throw an exception
     *
     * @param aWeekendNumber
     * @return
     */
    public static WeekendDiscountTimeSlotValidator createWeekendDiscountTimeSlotValidator(int aWeekendNumber) {
        if (aWeekendNumber <= 0) {
            return null;
        }
        return new WeekendDiscountTimeSlotValidator(aWeekendNumber);
    }

    private WeekendDiscountTimeSlotValidator(int aWeekendNumber) {
        this.weekendNumber = aWeekendNumber;
    }

    /**
     * * Checks whether a date is within the nth weekend (Saturday 00:00 to Sunday
     * 23:59) of the month. The number n has to be given to the instance beforehand
     * using the initializeWithWeekendNumber Method.
     * <p>
     * The weekend has always to start on a saturday according to ISO-Standard 8601.
     * Defined by the Product Owner Hans Muster on 18.09.2023.
     *
     * @param now
     * @return
     */
    public boolean isAuthorizedForDiscount(LocalDateTime now) {

        // refactored class according to clean code rules
        // IllegalWeekendNumberException I validate the weekend nr in the static creator method
        if (WEEKEND_DAYS.contains(now.getDayOfWeek())) {
            LocalDate startOfWeekend = getStartOfWeekend(now);
            LocalDate endOfWeekend = startOfWeekend.plusDays(1);

            return now.toLocalDate().isAfter(startOfWeekend.minusDays(1)) && now.toLocalDate().isBefore(endOfWeekend.plusDays(1));
        }
        return false;
    }

    /**
     * get start of weekend
     * the weekend has to start on a saturday according to ISO-Standard 8601.
     *
     * @param now
     * @return
     */
    private LocalDate getStartOfWeekend(LocalDateTime now) {
        int year = now.getYear();
        int monthValue = now.getMonthValue();

        LocalDate lFirstWeekendDay = LocalDate.of(year, monthValue, 1);
        while (lFirstWeekendDay.getDayOfWeek() != DayOfWeek.SATURDAY) {
            lFirstWeekendDay = lFirstWeekendDay.plusDays(1);
        }
        return lFirstWeekendDay.plusWeeks(weekendNumber - 1);
    }
}
