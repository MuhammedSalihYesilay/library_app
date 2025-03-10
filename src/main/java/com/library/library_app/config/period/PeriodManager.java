package com.library.library_app.config.period;

import com.library.library_app.exception.InvalidPeriodNumberException;
import org.springframework.stereotype.Component;
import lombok.Getter;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.LocalDate;

// service = dışarıya service edilen metotları barındırıyor
//component = serviceler hizmet eden ama aplication complekslerin bir nesnesi olan bir anatsayon
@Component
public class PeriodManager {

    @Getter
    private static final LocalTime[] PERIOD_START_TIMES = {
            LocalTime.of(0, 0),  // Period 1: 00:00 - 04:00
            LocalTime.of(4, 0),  // Period 2: 04:00 - 08:00
            LocalTime.of(8, 0),  // Period 3: 08:00 - 12:00
            LocalTime.of(12, 0), // Period 4: 12:00 - 16:00
            LocalTime.of(16, 0), // Period 5: 16:00 - 20:00
            LocalTime.of(20, 0)  // Period 6: 20:00 - 00:00
    };

    public LocalDateTime calculatePeriodEndTime(int periodNumber, LocalDate date) {
        validatePeriodNumber(periodNumber);

        if (periodNumber == 6) {
            return date.plusDays(1).atTime(LocalTime.MIDNIGHT);
        }
        return date.atTime(PERIOD_START_TIMES[periodNumber]);
    }

    public boolean isPeriodAvailableForReservation(int periodNumber, LocalDate date) {
        LocalDateTime periodEnd = calculatePeriodEndTime(periodNumber, date);  // Bitiş zamanını hesapla
        LocalDateTime now = LocalDateTime.now();

        if (date.isBefore(LocalDate.now())) {
            return false;
        }

        if (date.isAfter(LocalDate.now().plusDays(2))) {
            return false;
        }

        if (date.isEqual(LocalDate.now())) {
            return (now.isBefore(periodEnd));
        }

        return true;
    }

    public void validatePeriodNumber(int periodNumber) {
        if (periodNumber < 1 || periodNumber > 6) {
            throw new InvalidPeriodNumberException(periodNumber);
        }
    }
}
