package com.companycob.domain.model.entity;

import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.companycob.domain.exception.DomainException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

public class QuotaTest {

	@Test
	public void testEqualsQuotaDifferentId() {
		LocalDate dueDate = LocalDate.of(2021, 1, 1);

		Quota quota1 = new Quota(
			1L, 1, BigDecimal.ONE, BigDecimal.TEN, dueDate, 100L
		);

		Quota quota2 = new Quota(
			2L, 1, BigDecimal.ONE, BigDecimal.TEN, dueDate, 100L
		);

		assertThat(quota1.equals(quota2)).isFalse();
	}

	@Test
	public void testEqualsQuotaDifferentArrearsDays() {
		LocalDate dueDate = LocalDate.of(2021, 1, 1);
		
		Quota quota1 = new Quota(
			1L, 1, BigDecimal.ONE, BigDecimal.TEN, dueDate, 100L
		);

		Quota quota2 = new Quota(
			1L, 1, BigDecimal.ONE, BigDecimal.TEN, dueDate, 101L
		);

		assertThat(quota1.equals(quota2)).isFalse();
	}

	@Test
	public void testEqualsQuotaDifferentDueDate() {
		LocalDate dueDate = LocalDate.of(2021, 1, 1);
		
		Quota quota1 = new Quota(
			1L, 1, BigDecimal.ONE, BigDecimal.TEN, dueDate, 100L
		);

		Quota quota2 = new Quota(
			1L, 1, BigDecimal.ONE, BigDecimal.TEN, dueDate.plusDays(1), 100L
		);

		assertThat(quota1.equals(quota2)).isFalse();
	}

	@Test
	public void testEqualsQuotaDifferentNumber() {
		LocalDate dueDate = LocalDate.of(2021, 1, 1);
		
		Quota quota1 = new Quota(
			1L, 1, BigDecimal.ONE, BigDecimal.TEN, dueDate, 100L
		);

		Quota quota2 = new Quota(
			1L, 2, BigDecimal.ONE, BigDecimal.TEN, dueDate, 100L
		);

		assertThat(quota1.equals(quota2)).isFalse();
	}

	@Test
	public void testEqualsQuotaDifferentInitialValue() {
		LocalDate dueDate = LocalDate.of(2021, 1, 1);
		
		Quota quota1 = new Quota(
			1L, 1, BigDecimal.ONE, BigDecimal.TEN, dueDate, 100L
		);

		Quota quota2 = new Quota(
			1L, 1, BigDecimal.valueOf(25), BigDecimal.TEN, dueDate, 100L
		);

		assertThat(quota1.equals(quota2)).isFalse();
	}

	@Test
	public void testEqualsQuotaDifferentUpdatedValue() {
		LocalDate dueDate = LocalDate.of(2021, 1, 1);

		Quota quota1 = new Quota(
			1L, 1, BigDecimal.ONE, BigDecimal.TEN, dueDate, 100L
		);

		Quota quota2 = new Quota(
			1L, 1, BigDecimal.ONE, BigDecimal.valueOf(25), dueDate, 100L
		);

		assertThat(quota1.equals(quota2)).isFalse();
	}

	@Test
	public void testEqualsQuotaAllEquals() {
		LocalDate dueDate = LocalDate.of(2021, 1, 1);
		
		Quota quota1 = new Quota(
			1L, 1, BigDecimal.ONE, BigDecimal.TEN, dueDate, 100L
		);

		Quota quota2 = new Quota(
			1L, 1, BigDecimal.ONE, BigDecimal.TEN, dueDate, 100L
		);

		assertThat(quota1.equals(quota2)).isTrue();
	}

	@Test
	public void testCompareQuotaWithSmallerArrearsDays() {
		LocalDate dueDate = LocalDate.of(2021, 1, 1);
		
		Quota quota1 = new Quota(
			1L, 1, BigDecimal.ONE, BigDecimal.TEN, dueDate, 100L
		);

		Quota quota2 = new Quota(
			1L, 1, BigDecimal.ONE, BigDecimal.TEN, dueDate, 99L
		);

		assertThat(quota1.compareTo(quota2)).isEqualTo(1);
	}

	@Test
	public void testCompareQuotaWithGreaterArrearsDays() {
		LocalDate dueDate = LocalDate.of(2021, 1, 1);
		
		Quota quota1 = new Quota(
			1L, 1, BigDecimal.ONE, BigDecimal.TEN, dueDate, 100L
		);

		Quota quota2 = new Quota(
			1L, 1, BigDecimal.ONE, BigDecimal.TEN, dueDate, 101L
		);

		assertThat(quota1.compareTo(quota2)).isEqualTo(-1);
	}

	@Test
	public void testCompareQuotaWithSameArrearsDays() {
		LocalDate dueDate = LocalDate.of(2021, 1, 1);
		
		Quota quota1 = new Quota(
			1L, 1, BigDecimal.ONE, BigDecimal.TEN, dueDate, 100L
		);

		Quota quota2 = new Quota(
			1L, 1, BigDecimal.ONE, BigDecimal.TEN, dueDate, 100L
		);

		assertThat(quota1.compareTo(quota2)).isEqualTo(0);
	}

	@Test
	public void testQuotaWithoutDueDate() {
		assertThrows(DomainException.class, () -> new Quota(1L, 1, BigDecimal.ONE, BigDecimal.TEN, null, 100L));
	}

	@Test
	public void testQuotaWithInitialValueNegative() {
		assertThrows(DomainException.class, () -> new Quota(1L, 1, BigDecimal.valueOf(-200), BigDecimal.TEN, LocalDate.now(), 100L));
	}

	@Test
	public void testQuotaWithNumberZero() {
		assertThrows(DomainException.class, () -> new Quota(1L, 0, BigDecimal.ONE, BigDecimal.TEN, LocalDate.now(), 100L));
	}

	@Test
	public void testQuotaCorrect() {
		final var dueDate = LocalDate.now();
		Quota quota = new Quota(1L, 1, BigDecimal.ONE, BigDecimal.TEN, dueDate, 100L);

		assertThat(quota).isNotNull();
		assertThat(quota.getId()).isEqualTo(1L);
		assertThat(quota.getNumber()).isEqualTo(1);
		assertThat(quota.getInitialValue()).isEqualByComparingTo(BigDecimal.ONE);
		assertThat(quota.getUpdatedValue()).isEqualByComparingTo(BigDecimal.TEN);
		assertThat(quota.getDueDate()).isEqualTo(dueDate);
		assertThat(quota.getArrearsDays()).isEqualTo(100L);
	}
}
