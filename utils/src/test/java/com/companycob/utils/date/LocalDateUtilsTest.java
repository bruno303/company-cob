package com.companycob.utils.date;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

public class LocalDateUtilsTest {

    @Test
    public void testDifferencePositive() {
        LocalDate start = LocalDate.of(2020, 1, 1);
        LocalDate end = LocalDate.of(2020, 6, 1);

        long days = LocalDateUtils.differenceInDays(start, end);
        Assert.assertEquals(152, days);
    }

    @Test
    public void testSameDate() {
        LocalDate start = LocalDate.of(2020, 1, 1);
        LocalDate end = LocalDate.of(2020, 1, 1);

        long days = LocalDateUtils.differenceInDays(start, end);
        Assert.assertEquals(0, days);
    }

    @Test
    public void testDifferenceNegative() {
        LocalDate start = LocalDate.of(2020, 6, 1);
        LocalDate end = LocalDate.of(2020, 1, 1);

        long days = LocalDateUtils.differenceInDays(start, end);
        Assert.assertEquals(-152, days);
    }
}
