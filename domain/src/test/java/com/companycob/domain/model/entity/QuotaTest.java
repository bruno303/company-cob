package com.companycob.domain.model.entity;

import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class QuotaTest {

	@Test
	public void testEqualsQuotaDifferentId() {
		LocalDate dueDate = LocalDate.of(2021, 1, 1);
		Contract contract = new Contract();
		contract.setId(1L);

		Quota quota1 = new Quota();
		quota1.setId(1);
		quota1.setArrearsDays(100L);
		quota1.setDueDate(dueDate);
		quota1.setNumber(1);
		quota1.setInitialValue(BigDecimal.ONE);
		quota1.setUpdatedValue(BigDecimal.TEN);
		quota1.setContract(contract);

		Quota quota2 = new Quota();
		quota2.setId(2); // diff
		quota2.setArrearsDays(100L);
		quota2.setDueDate(dueDate);
		quota2.setNumber(1);
		quota2.setInitialValue(BigDecimal.ONE);
		quota2.setUpdatedValue(BigDecimal.TEN);
		quota2.setContract(contract);

		assertThat(quota1.equals(quota2)).isFalse();
	}

	@Test
	public void testEqualsQuotaDifferentArrearsDays() {
		LocalDate dueDate = LocalDate.of(2021, 1, 1);
		Contract contract = new Contract();
		contract.setId(1L);

		Quota quota1 = new Quota();
		quota1.setId(1);
		quota1.setArrearsDays(100L);
		quota1.setDueDate(dueDate);
		quota1.setNumber(1);
		quota1.setInitialValue(BigDecimal.ONE);
		quota1.setUpdatedValue(BigDecimal.TEN);
		quota1.setContract(contract);

		Quota quota2 = new Quota();
		quota2.setId(1);
		quota2.setArrearsDays(101L); // diff
		quota2.setDueDate(dueDate);
		quota2.setNumber(1);
		quota2.setInitialValue(BigDecimal.ONE);
		quota2.setUpdatedValue(BigDecimal.TEN);
		quota2.setContract(contract);

		assertThat(quota1.equals(quota2)).isFalse();
	}

	@Test
	public void testEqualsQuotaDifferentDueDate() {
		LocalDate dueDate = LocalDate.of(2021, 1, 1);
		Contract contract = new Contract();
		contract.setId(1L);

		Quota quota1 = new Quota();
		quota1.setId(1);
		quota1.setArrearsDays(100L);
		quota1.setDueDate(dueDate);
		quota1.setNumber(1);
		quota1.setInitialValue(BigDecimal.ONE);
		quota1.setUpdatedValue(BigDecimal.TEN);
		quota1.setContract(contract);

		Quota quota2 = new Quota();
		quota2.setId(1);
		quota2.setArrearsDays(100L);
		quota2.setDueDate(dueDate.plusDays(1)); // diff
		quota2.setNumber(1);
		quota2.setInitialValue(BigDecimal.ONE);
		quota2.setUpdatedValue(BigDecimal.TEN);
		quota2.setContract(contract);

		assertThat(quota1.equals(quota2)).isFalse();
	}

	@Test
	public void testEqualsQuotaDifferentNumber() {
		LocalDate dueDate = LocalDate.of(2021, 1, 1);
		Contract contract = new Contract();
		contract.setId(1L);

		Quota quota1 = new Quota();
		quota1.setId(1);
		quota1.setArrearsDays(100L);
		quota1.setDueDate(dueDate);
		quota1.setNumber(1);
		quota1.setInitialValue(BigDecimal.ONE);
		quota1.setUpdatedValue(BigDecimal.TEN);
		quota1.setContract(contract);

		Quota quota2 = new Quota();
		quota2.setId(1);
		quota2.setArrearsDays(100L);
		quota2.setDueDate(dueDate);
		quota2.setNumber(2); // diff
		quota2.setInitialValue(BigDecimal.ONE);
		quota2.setUpdatedValue(BigDecimal.TEN);
		quota2.setContract(contract);

		assertThat(quota1.equals(quota2)).isFalse();
	}

	@Test
	public void testEqualsQuotaDifferentInitialValue() {
		LocalDate dueDate = LocalDate.of(2021, 1, 1);
		Contract contract = new Contract();
		contract.setId(1L);

		Quota quota1 = new Quota();
		quota1.setId(1);
		quota1.setArrearsDays(100L);
		quota1.setDueDate(dueDate);
		quota1.setNumber(1);
		quota1.setInitialValue(BigDecimal.ONE);
		quota1.setUpdatedValue(BigDecimal.TEN);
		quota1.setContract(contract);

		Quota quota2 = new Quota();
		quota2.setId(1);
		quota2.setArrearsDays(100L);
		quota2.setDueDate(dueDate);
		quota2.setNumber(1);
		quota2.setInitialValue(BigDecimal.valueOf(25)); // diff
		quota2.setUpdatedValue(BigDecimal.TEN);
		quota2.setContract(contract);

		assertThat(quota1.equals(quota2)).isFalse();
	}

	@Test
	public void testEqualsQuotaDifferentUpdatedValue() {
		LocalDate dueDate = LocalDate.of(2021, 1, 1);
		Contract contract = new Contract();
		contract.setId(1L);

		Quota quota1 = new Quota();
		quota1.setId(1);
		quota1.setArrearsDays(100L);
		quota1.setDueDate(dueDate);
		quota1.setNumber(1);
		quota1.setInitialValue(BigDecimal.ONE);
		quota1.setUpdatedValue(BigDecimal.TEN);
		quota1.setContract(contract);

		Quota quota2 = new Quota();
		quota2.setId(1);
		quota2.setArrearsDays(100L);
		quota2.setDueDate(dueDate);
		quota2.setNumber(1);
		quota2.setInitialValue(BigDecimal.ONE);
		quota2.setUpdatedValue(BigDecimal.valueOf(25)); // diff
		quota2.setContract(contract);

		assertThat(quota1.equals(quota2)).isFalse();
	}

	@Test
	public void testEqualsQuotaDifferentContract() {
		LocalDate dueDate = LocalDate.of(2021, 1, 1);
		Contract contract = new Contract();
		contract.setId(1L);

		Contract contract2 = new Contract();
		contract2.setId(2L);

		Quota quota1 = new Quota();
		quota1.setId(1);
		quota1.setArrearsDays(100L);
		quota1.setDueDate(dueDate);
		quota1.setNumber(1);
		quota1.setInitialValue(BigDecimal.ONE);
		quota1.setUpdatedValue(BigDecimal.TEN);
		quota1.setContract(contract);

		Quota quota2 = new Quota();
		quota2.setId(1);
		quota2.setArrearsDays(100L);
		quota2.setDueDate(dueDate);
		quota2.setNumber(1);
		quota2.setInitialValue(BigDecimal.ONE);
		quota2.setUpdatedValue(BigDecimal.TEN);
		quota2.setContract(contract2); // diff

		assertThat(quota1.equals(quota2)).isFalse();
	}

	@Test
	public void testEqualsQuotaAllEquals() {
		LocalDate dueDate = LocalDate.of(2021, 1, 1);
		Contract contract = new Contract();
		contract.setId(1L);

		Quota quota1 = new Quota();
		quota1.setId(1);
		quota1.setArrearsDays(100L);
		quota1.setDueDate(dueDate);
		quota1.setNumber(1);
		quota1.setInitialValue(BigDecimal.ONE);
		quota1.setUpdatedValue(BigDecimal.TEN);
		quota1.setContract(contract);

		Quota quota2 = new Quota();
		quota2.setId(1);
		quota2.setArrearsDays(100L);
		quota2.setDueDate(dueDate);
		quota2.setNumber(1);
		quota2.setInitialValue(BigDecimal.ONE);
		quota2.setUpdatedValue(BigDecimal.TEN);
		quota2.setContract(contract);

		assertThat(quota1.equals(quota2)).isTrue();
	}

	@Test
	public void testCompareQuotaWithSmallerArrearsDays() {
		Quota quota1 = new Quota();
		quota1.setArrearsDays(100L);

		Quota quota2 = new Quota();
		quota2.setArrearsDays(99L);

		assertThat(quota1.compareTo(quota2)).isEqualTo(1);
	}

	@Test
	public void testCompareQuotaWithGreaterArrearsDays() {
		Quota quota1 = new Quota();
		quota1.setArrearsDays(100L);

		Quota quota2 = new Quota();
		quota2.setArrearsDays(101L);

		assertThat(quota1.compareTo(quota2)).isEqualTo(-1);
	}

	@Test
	public void testCompareQuotaWithSameArrearsDays() {
		Quota quota1 = new Quota();
		quota1.setArrearsDays(100L);

		Quota quota2 = new Quota();
		quota2.setArrearsDays(100L);

		assertThat(quota1.compareTo(quota2)).isEqualTo(0);
	}
}
