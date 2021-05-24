package com.companycob.domain.model.entity;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.companycob.domain.exception.DomainException;
import com.companycob.domain.model.enumerators.CalcType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

public class ContractTest {

	@Test
	public void testContractEqualsDifferentId() {
		final var contract = createContract(1L);
		final var contract2 = createContract(2L);
		assertThat(contract.equals(contract2)).isFalse();
	}

	@Test
	public void testContractEqualsSameId() {
		final var contract = createContract(1L);
		final var contract2 = createContract(1L);
		assertThat(contract.equals(contract2)).isTrue();
	}

	@Test
	public void testContractWithEmptyNumber() {
		assertThrows(DomainException.class, () -> createContract(1L, ""));
	}

	@Test
	public void testContractWithNullNumber() {
		assertThrows(DomainException.class, () -> createContract(1L, null));
	}

	@Test
	public void testContractGetKeyWithNumberFilled() {
		final var contract = createContract(1L, "123456");

		final String key = contract.getKey();

		assertThat(key).isEqualTo("contract:123456");
	}

	@Test
	public void testContractWithNullDate() {
		assertThrows(DomainException.class, () -> createContract((LocalDate)null));
	}

	private Contract createContract(Long contractId) {
		return createContract(contractId, RandomStringUtils.randomAlphanumeric(20), LocalDate.now());
	}

	private Contract createContract(Long contractId, String contractNumber) {
		return createContract(contractId, contractNumber, LocalDate.now());
	}

	private Contract createContract(LocalDate contractDate) {
		return createContract(1L, RandomStringUtils.randomAlphanumeric(20), contractDate);
	}

	private Contract createContract(Long contractId, String contractNumber, LocalDate contractDate) {
		final BankCalculationValues calcValues = new BankCalculationValues(BigDecimal.TEN, BigDecimal.TEN);
		final Bank bank = new Bank(1L, "Bank", "Bank Social Name", CalcType.DEFAULT, calcValues);
		final List<Quota> quotas = new ArrayList<>();
		quotas.add(new Quota(1L, 1, BigDecimal.ONE, BigDecimal.ONE, LocalDate.now(), 100L));

		return new Contract(contractId, contractNumber, contractDate, quotas, bank);
	}
}
