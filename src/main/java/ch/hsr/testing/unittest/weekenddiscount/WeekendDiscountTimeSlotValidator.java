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
     *
     * @param now
     * @return
     */
    public boolean isAuthorizedForDiscount(LocalDateTime now) {

        // removed IllegalWeekendNumberException i validate the weekendnumber in the static creator method
        // Check if the day is a weekend day
        // discussion with PO resulted that Sunday counts as start of weekend
        if (WEEKEND_DAYS.contains(now.getDayOfWeek())) {
            LocalDate startOfWeekend = getStartOfWeekend(now);
            LocalDate endOfWeekend = startOfWeekend.plusDays(1);

            // Check if the given date is within the weekend
            return now.toLocalDate().isAfter(startOfWeekend.minusDays(1)) && now.toLocalDate().isBefore(endOfWeekend.plusDays(1));
        }
        return false;
    }

    private LocalDate getStartOfWeekend(LocalDateTime now) {
        int year = now.getYear();
        int monthValue = now.getMonthValue();

        // find first saturday or sunday of the month
        // discussion with PO resulted that Sunday counts as start of weekend
        LocalDate lFirstWeekendDay = LocalDate.of(year, monthValue, 1);
        while (lFirstWeekendDay.getDayOfWeek() != DayOfWeek.SATURDAY
                && lFirstWeekendDay.getDayOfWeek() != DayOfWeek.SUNDAY) {
           lFirstWeekendDay = lFirstWeekendDay.plusDays(1);
        }
        return lFirstWeekendDay; //.plusWeeks(weekendNumber - 1);
    }
}
