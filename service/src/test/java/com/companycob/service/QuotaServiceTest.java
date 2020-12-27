package com.companycob.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;

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
		assertThat(validationErrors.getErrors().size()).isEqualTo(1);

		final var validationErrorDTO = validationErrors.getErrors().get(0);
		assertThat(validationErrorDTO.getError()).isEqualTo("DueDate can't be empty");
		assertThat(validationErrorDTO.getProperty()).isEqualTo("dueDate");
	}

	@Test
	public void testQuotaWithoutContract() {
		final var quota = new Quota();
		quota.setInitialValue(BigDecimal.valueOf(200));
		quota.setDueDate(LocalDate.now());
		quota.setNumber(1);

		final var validationErrors = quotaService.verify(quota);
		assertThat(validationErrors.getErrors().size()).isEqualTo(1);

		final var validationErrorDTO = validationErrors.getErrors().get(0);
		assertThat(validationErrorDTO.getError()).isEqualTo("Contract can't be empty");
		assertThat(validationErrorDTO.getProperty()).isEqualTo("contract");
	}

	@Test
	public void testQuotaWithInitialValueNegative() {
		final var quota = new Quota();
		quota.setInitialValue(BigDecimal.valueOf(-200));
		quota.setContract(new Contract());
		quota.setDueDate(LocalDate.now());
		quota.setNumber(1);

		final var validationErrors = quotaService.verify(quota);
		assertThat(validationErrors.getErrors().size()).isEqualTo(1);

		final var validationErrorDTO = validationErrors.getErrors().get(0);
		assertThat(validationErrorDTO.getError()).isEqualTo("InitialValue can't be negative");
		assertThat(validationErrorDTO.getProperty()).isEqualTo("initialValue");
	}

	@Test
	public void testQuotaWithNumberZero() {
		final var quota = new Quota();
		quota.setInitialValue(BigDecimal.valueOf(200));
		quota.setContract(new Contract());
		quota.setDueDate(LocalDate.now());
		quota.setNumber(0);

		final var validationErrors = quotaService.verify(quota);
		assertThat(validationErrors.getErrors().size()).isEqualTo(1);

		final var validationErrorDTO = validationErrors.getErrors().get(0);
		assertThat(validationErrorDTO.getError()).isEqualTo("Number must be greather than 0");
		assertThat(validationErrorDTO.getProperty()).isEqualTo("number");
	}

	@Test
	public void testQuotaWithMultipleErrors() {
		final var quota = new Quota();
		quota.setInitialValue(BigDecimal.valueOf(-200));
		quota.setDueDate(LocalDate.now());
		quota.setNumber(0);

		final var validationErrors = quotaService.verify(quota);
		assertThat(validationErrors.getErrors().size()).isEqualTo(3);
	}

	@Test
	public void testQuotaCorrect() {
		final var quota = new Quota();
		quota.setInitialValue(BigDecimal.valueOf(200));
		quota.setContract(new Contract());
		quota.setDueDate(LocalDate.now());
		quota.setNumber(1);

		final var validationErrors = quotaService.verify(quota);
		assertThat(validationErrors.hasErrors()).isFalse();
	}
}
