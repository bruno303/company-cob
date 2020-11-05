package com.companycob.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.companycob.domain.model.entity.Contract;
import com.companycob.domain.model.entity.Quota;
import com.companycob.tests.AbstractDatabaseIntegrationTest;

public class QuotaServiceTest extends AbstractDatabaseIntegrationTest {

	@Autowired
	private QuotaService quotaService;

	@Test
	public void testQuotaWithoutDueDate() {
		final var quota = new Quota();
		quota.setInitialValue(BigDecimal.valueOf(200));
		quota.setContract(new Contract());
		quota.setNumber(1);

		final var validationErrors = quotaService.verify(quota);
		Assert.assertEquals(1, validationErrors.getErrors().size());

		final var validationErrorDTO = validationErrors.getErrors().get(0);
		Assert.assertEquals("DueDate can't be empty", validationErrorDTO.getError());
		Assert.assertEquals("dueDate", validationErrorDTO.getProperty());
	}

	@Test
	public void testQuotaWithoutContract() {
		final var quota = new Quota();
		quota.setInitialValue(BigDecimal.valueOf(200));
		quota.setDueDate(LocalDate.now());
		quota.setNumber(1);

		final var validationErrors = quotaService.verify(quota);
		Assert.assertEquals(1, validationErrors.getErrors().size());

		final var validationErrorDTO = validationErrors.getErrors().get(0);
		Assert.assertEquals("Contract can't be empty", validationErrorDTO.getError());
		Assert.assertEquals("contract", validationErrorDTO.getProperty());
	}

	@Test
	public void testQuotaWithInitialValueNegative() {
		final var quota = new Quota();
		quota.setInitialValue(BigDecimal.valueOf(-200));
		quota.setContract(new Contract());
		quota.setDueDate(LocalDate.now());
		quota.setNumber(1);

		final var validationErrors = quotaService.verify(quota);
		Assert.assertEquals(1, validationErrors.getErrors().size());

		final var validationErrorDTO = validationErrors.getErrors().get(0);
		Assert.assertEquals("InitialValue can't be negative", validationErrorDTO.getError());
		Assert.assertEquals("initialValue", validationErrorDTO.getProperty());
	}

	@Test
	public void testQuotaWithNumberZero() {
		final var quota = new Quota();
		quota.setInitialValue(BigDecimal.valueOf(200));
		quota.setContract(new Contract());
		quota.setDueDate(LocalDate.now());
		quota.setNumber(0);

		final var validationErrors = quotaService.verify(quota);
		Assert.assertEquals(1, validationErrors.getErrors().size());

		final var validationErrorDTO = validationErrors.getErrors().get(0);
		Assert.assertEquals("Number must be greather than 0", validationErrorDTO.getError());
		Assert.assertEquals("number", validationErrorDTO.getProperty());
	}

	@Test
	public void testQuotaWithMultipleErrors() {
		final var quota = new Quota();
		quota.setInitialValue(BigDecimal.valueOf(-200));
		quota.setDueDate(LocalDate.now());
		quota.setNumber(0);

		final var validationErrors = quotaService.verify(quota);
		Assert.assertEquals(3, validationErrors.getErrors().size());
	}

	@Test
	public void testQuotaCorrect() {
		final var quota = new Quota();
		quota.setInitialValue(BigDecimal.valueOf(200));
		quota.setContract(new Contract());
		quota.setDueDate(LocalDate.now());
		quota.setNumber(1);

		final var validationErrors = quotaService.verify(quota);
		Assert.assertFalse(validationErrors.hasErrors());
	}
}
