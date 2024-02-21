    package org.example.test;

    import org.assertj.core.api.Condition;
    import org.example.WorkingHoursService;
    import org.junit.jupiter.api.Test;

    import java.math.BigDecimal;
    import java.time.DayOfWeek;
    import java.time.LocalDate;
    import java.time.LocalDateTime;
    import java.time.LocalTime;
    import java.util.List;

    import static java.time.DayOfWeek.*;
    import static org.assertj.core.api.Assertions.assertThat;

    class WorkingHoursServiceTest {
        private final WorkingHoursService service = new WorkingHoursService(
                List.of(
                        LocalDate.of(2024, 1, 1), // First of Year
                        LocalDate.of(2024, 3, 31), // Ester day
                        LocalDate.of(2024, 4, 1), // Easter Monday
                        LocalDate.of(2024, 12, 25) // Christmas
                ),
                LocalTime.of(8, 0), // start working day
                LocalTime.of(16, 0) // end working day
        );

        private Condition<? super LocalDateTime> dayOfTheWeek(DayOfWeek dayOfWeek) {
            return new Condition<>(localDateTime -> localDateTime.getDayOfWeek() == dayOfWeek, "day of the week " + dayOfWeek);
        }

        @Test
        void addWorkingDaysTest() {
            // Given 2024.2.21 15:00 (Wednesday), plus 1 working day, expected 2024.2.22 15:00
            LocalDateTime start = LocalDateTime.of(2024, 2, 21, 15, 0);
            BigDecimal workDays = BigDecimal.valueOf(1);
            // When
            LocalDateTime result = service.addWorkingDays(start, workDays);
            // Then
            assertThat(result).isEqualTo(start.plusDays(1));
        }

        @Test
        void addWorkingDaysTestCrossWeekend() {
            // Given 2024.2.21 15:00 (Wednesday), plus 5 working days, expected 2024.2.28 15:00
            LocalDateTime start = LocalDateTime.of(2024, 2, 21, 15, 0);
            BigDecimal workDays = BigDecimal.valueOf(5);
            // When
            LocalDateTime result = service.addWorkingDays(start, workDays);
            // Then
            assertThat(result)
                    .isEqualTo(LocalDateTime.of(2024, 2, 28, 15, 0))
                    .is(dayOfTheWeek(WEDNESDAY));
        }

        @Test
        void addWorkingDaysTestCrossWeekendWithHoliday() {
            // Given 2024.3.28 8:00 (Wednesday), plus 5 working days, expected 2024.4.8 8:00 (Friday) because of the weekend and Ester Monday
            LocalDateTime start = LocalDateTime.of(2024, 3, 28, 8, 0);
            BigDecimal workDays = BigDecimal.valueOf(5);
            // When
            LocalDateTime result = service.addWorkingDays(start, workDays);
            // Then
            assertThat(result)
                    .isEqualTo(LocalDateTime.of(2024, 4, 5, 8, 0))
                    .is(dayOfTheWeek(FRIDAY));
        }

        @Test
        void addWorkingDaysTestDecimalValue() {
            // Given 2024.2.21 10:00 (Wednesday), plus 2.5 working days, expected 2024.2.23 14:00 (Friday)
            LocalDateTime start = LocalDateTime.of(2024, 2, 21, 10, 0);
            BigDecimal workDays = BigDecimal.valueOf(2.5);
            // When
            LocalDateTime result = service.addWorkingDays(start, workDays);
            // Then
            assertThat(result)
                    .isEqualTo(LocalDateTime.of(2024, 2, 23, 14, 0))
                    .is(dayOfTheWeek(FRIDAY));
        }

        @Test
        void addWorkingDaysTestDecimalValueCrossDay() {
            // Given 2024.2.21 14:00 (Wednesday), plus 0.5 working days (4 h), expected 2024.2.22 10:00 (Thursday)
            LocalDateTime start = LocalDateTime.of(2024, 2, 21, 14, 0);
            BigDecimal workDays = BigDecimal.valueOf(0.5);
            // When
            LocalDateTime result = service.addWorkingDays(start, workDays);
            // Then
            assertThat(result)
                    .isEqualTo(LocalDateTime.of(2024, 2, 22, 10, 0))
                    .is(dayOfTheWeek(THURSDAY));
        }
    }