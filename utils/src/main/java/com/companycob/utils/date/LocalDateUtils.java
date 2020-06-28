package com.companycob.utils.date;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public final class LocalDateUtils {

    public static long differenceInDays(LocalDate startInclusive, LocalDate endExclusive) {
        return ChronoUnit.DAYS.between(startInclusive, endExclusive);
    }

}
