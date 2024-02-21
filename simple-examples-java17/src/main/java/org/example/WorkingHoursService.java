    package org.example;

    import java.math.BigDecimal;
    import java.math.RoundingMode;
    import java.time.*;
    import java.util.Collection;
    import java.util.Collections;

    /**
     * StackOverflow Question:
     * <a href="https://stackoverflow.com/q/78033477/11289119">How to adjust the hours in working hours calendar</a>
     *
     * @author stefano-riffaldi
     */
    public class WorkingHoursService {
        private final Collection<LocalDate> holidays;
        private final LocalTime workdayStart;
        private final LocalTime workdayEnd;

        public WorkingHoursService() {
            this(Collections.emptySet(), LocalTime.of(8, 0), LocalTime.of(16, 0));
        }

        public WorkingHoursService(Collection<LocalDate> holidays, LocalTime workdayStart, LocalTime workdayEnd) {
            this.holidays = holidays;
            this.workdayStart = workdayStart;
            this.workdayEnd = workdayEnd;
        }

        /**
         * Given a LocalDateTime, add the number of working days as decimal number, make sure it will not exceed the working hour's conditions that is:
         * employee's working hours are 8:00 am to 16:00 pm
         * employee's do not work in the weekends
         * employee's do not work on holidays
         *
         * @param startDateTime
         * @param workDays
         * @return
         */
        public LocalDateTime addWorkingDays(LocalDateTime startDateTime, BigDecimal workDays) {
            // get integer part of workDays
            BigDecimal days = workDays.setScale(0, RoundingMode.FLOOR);
            // get decimal part to get hours extra, using minutes to be precise
            BigDecimal minutes = workDays.subtract(days).multiply(BigDecimal.valueOf(8 * 60));
            LocalDateTime plussed = startDateTime.plusMinutes(minutes.intValue());
            // Check if working time exceeds 8:00 am to 16:00 pm
            long remainingMinutes = Duration.between(workdayEnd, plussed.toLocalTime()).toMinutes();
            // if that is the case, add 1 day and remaining minutes
            if (remainingMinutes > 0) {
                plussed = LocalDateTime.of(plussed.toLocalDate().plusDays(1), workdayStart.plusMinutes(remainingMinutes));
            }
            for (int i = 0; i < days.intValue(); i++) {
                // if plussed is a holiday or weekend, add 1 day 'for free'
                while (isWeekend(plussed.toLocalDate()) || isHoliday(plussed.toLocalDate())) {
                    plussed = plussed.plusDays(1);
                }
                plussed = plussed.plusDays(1);
            }
            return plussed;
        }

        private boolean isHoliday(LocalDate startDate) {
            return holidays.contains(startDate);
        }

        private boolean isWeekend(LocalDate startDate) {
            return startDate.getDayOfWeek() == DayOfWeek.SUNDAY || startDate.getDayOfWeek() == DayOfWeek.SATURDAY;
        }
    }
