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
    public static final int NOF_WEEKDAYS = 7;

    private Integer weekendNumber;

    public void initializeWithWeekendNumber(int weekendNumber) {
        this.weekendNumber = weekendNumber;
    }

    /**
     * Checks whether a date is within the nth weekend (Saturday 00:00 to Sunday
     * 23:59) of the month. The number n has to be given to the instance beforehand
     * using the initializeWithWeekendNumber Method.
     *
     * @param now the point in time for which the decision should be made whether weekend discount is applied or not
     * @return
     * @throws IllegalWeekendNumberException
     *             if weekend number is not set
     *             or if the weekend number is higher than the number of weekends in this month
     */
    public boolean isAuthorizedForDiscount(LocalDateTime now) throws IllegalWeekendNumberException {
        // Check if the weekend number has been set
        if (this.weekendNumber == null) {
            throw new IllegalWeekendNumberException("WeekendDiscountTimeSlotValidator has not been initialized correctly!");
        }

        // Check if the day is a weekend day
        if (WEEKEND_DAYS.contains(now.getDayOfWeek())) {
            LocalDate startOfWeekend = getStartOfWeekend(now);
            LocalDate endOfWeekend = startOfWeekend.plusDays(NOF_WEEKDAYS - 1);

            // Check if the given date is within the weekend
            return now.toLocalDate().isAfter(startOfWeekend.minusDays(1)) && now.toLocalDate().isBefore(endOfWeekend.plusDays(1));
        }

        return false;
    }

    private LocalDate getStartOfWeekend(LocalDateTime now) {
        int year = now.getYear();
        int monthValue = now.getMonthValue();

        // Find the first Saturday in the month
        LocalDate lFirstSaturdayInMonth = LocalDate.of(year, monthValue, 1);
        while (lFirstSaturdayInMonth.getDayOfWeek() != DayOfWeek.SATURDAY) {
            lFirstSaturdayInMonth = lFirstSaturdayInMonth.plusDays(1);
        }

        // Calculate the start and end of the weekend for the given weekend number
        return lFirstSaturdayInMonth.plusWeeks(weekendNumber - 1);
    }
}
