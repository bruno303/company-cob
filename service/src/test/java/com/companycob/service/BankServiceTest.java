package com.companycob.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.stream.Collectors;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.companycob.domain.model.entity.Bank;
import com.companycob.domain.model.entity.BankCalculationValues;
import com.companycob.domain.model.enumerators.CalcType;
import com.companycob.tests.AbstractDatabaseIntegrationTest;

public class BankServiceTest extends AbstractDatabaseIntegrationTest {

	@Autowired
	private BankService bankService;

	@Test
	public void testVerifyBankWithSuccess() {
		final var bank = new Bank();
		bank.setName("Bank");
		bank.setSocialName("Bank Social Media");
		bank.setBankCalculationValues(createValidBankCalculationValues(bank));
		bank.setCalcType(CalcType.DEFAULT);

		final var result = bankService.verify(bank);
		assertThat(result.hasErrors()).isFalse();
	}

	@Test
	public void testVerifyBankWithoutName() {
		final var bank = new Bank();
		bank.setSocialName("Bank Social Media");
		bank.setBankCalculationValues(createValidBankCalculationValues(bank));
		bank.setCalcType(CalcType.DEFAULT);

		final var verify = bankService.verify(bank);
		assertThat(verify.hasErrors()).isTrue();
		assertThat(verify.getErrors().size()).isEqualTo(1);
		assertThat(verify.getErrors().get(0).getProperty()).isEqualTo("name");
	}

	@Test
	public void testVerifyBankWithoutSocialName() {
		final var bank = new Bank();
		bank.setName("Bank");
		bank.setBankCalculationValues(createValidBankCalculationValues(bank));
		bank.setCalcType(CalcType.DEFAULT);

		final var verify = bankService.verify(bank);
		assertThat(verify.hasErrors()).isTrue();
		assertThat(verify.getErrors().size()).isEqualTo(1);
		assertThat(verify.getErrors().get(0).getProperty()).isEqualTo("socialName");
	}

	@Test
	public void testVerifyBankWithoutCommission() {
		final var bank = createValidBank();
		bank.setBankCalculationValues(createValidBankCalculationValues(bank, null));

		final var verify = bankService.verify(bank);
		assertThat(verify.hasErrors()).isTrue();
		assertThat(verify.getErrors().size()).isEqualTo(1);
		assertThat(verify.getErrors().get(0).getProperty()).isEqualTo("commission");
	}

	@Test
	public void testVerifyBankWithNegativeCommission() {
		final var bank = createValidBank();
		bank.setBankCalculationValues(createValidBankCalculationValues(bank, BigDecimal.valueOf(-10)));

		final var verify = bankService.verify(bank);
		assertThat(verify.hasErrors()).isTrue();
		assertThat(verify.getErrors().size()).isEqualTo(1);
		assertThat(verify.getErrors().get(0).getProperty()).isEqualTo("commission");
	}

	@Test
	public void testVerifyBankWithZeroCommission() {
		final var bank = createValidBank();
		bank.setBankCalculationValues(createValidBankCalculationValues(bank));

		final var verify = bankService.verify(bank);
		assertThat(verify.hasErrors()).isFalse();
	}

	@Test
	public void testVerifyBankWithoutCalcType() {
		final var bank = createValidBank();
		bank.setBankCalculationValues(createValidBankCalculationValues(bank));
		bank.setCalcType(null);

		final var verify = bankService.verify(bank);
		assertThat(verify.hasErrors()).isTrue();
		assertThat(verify.getErrors().size()).isEqualTo(1);
		assertThat(verify.getErrors().get(0).getProperty()).isEqualTo("calcType");
	}

	@Test
	public void testSaveAndLoadBank() {
		final var bank = new Bank();
		bank.setName("Bank saved");
		bank.setSocialName("Bank saved");
		bank.setBankCalculationValues(createValidBankCalculationValues(bank, BigDecimal.valueOf(25)));
		bank.setCalcType(CalcType.DEFAULT);

		final var saved = bankService.save(bank);
		final var id = saved.getId();

		final var bankLoadedList = bankService.findAll().stream().filter(b -> b.getId() == id)
				.collect(Collectors.toList());

		assertThat(bankLoadedList.size()).isEqualTo(1);

		final var bankLoaded = bankLoadedList.get(0);
		assertThat(bankLoaded.getId()).isEqualTo(id);
		assertThat(bank.getName()).isEqualTo("Bank saved");
		assertThat(bank.getSocialName()).isEqualTo("Bank saved");
		assertThat(bank.getBankCalculationValues()).isNotNull();
	}

	private Bank createValidBank() {
		final var bank = new Bank();
		bank.setName("Bank saved");
		bank.setSocialName("Bank saved");
		bank.setCalcType(CalcType.DEFAULT);

		return bank;
	}

	private BankCalculationValues createValidBankCalculationValues(final Bank bank, final BigDecimal commission,
			final BigDecimal interestRate) {
		final var bankCalculationValues = new BankCalculationValues();
		bankCalculationValues.setBank(bank);
		bankCalculationValues.setCommission(commission);
		bankCalculationValues.setInterestRate(interestRate);

		return bankCalculationValues;
	}

	private BankCalculationValues createValidBankCalculationValues(final Bank bank, final BigDecimal commission) {
		return createValidBankCalculationValues(bank, commission, BigDecimal.valueOf(0.10));
	}

	private BankCalculationValues createValidBankCalculationValues(final Bank bank) {
		return createValidBankCalculationValues(bank, BigDecimal.TEN, BigDecimal.valueOf(0.10));
	}
}
