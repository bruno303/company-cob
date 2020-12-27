package com.companycob.utils.date;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.Test;

public class LocalDateUtilsTest {

	@Test
	public void testDifferencePositive() {
		final LocalDate start = LocalDate.of(2020, 1, 1);
		final LocalDate end = LocalDate.of(2020, 6, 1);

		final long days = LocalDateUtils.differenceInDays(start, end);
		assertThat(days).isEqualTo(152);
	}

	@Test
	public void testSameDate() {
		final LocalDate start = LocalDate.of(2020, 1, 1);
		final LocalDate end = LocalDate.of(2020, 1, 1);

		final long days = LocalDateUtils.differenceInDays(start, end);
		assertThat(days).isZero();
	}

	@Test
	public void testDifferenceNegative() {
		final LocalDate start = LocalDate.of(2020, 6, 1);
		final LocalDate end = LocalDate.of(2020, 1, 1);

		final long days = LocalDateUtils.differenceInDays(start, end);
		assertThat(days).isEqualTo(-152);
	}
}
