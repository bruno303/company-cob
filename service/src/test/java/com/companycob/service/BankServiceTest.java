package com.companycob.service;

import java.math.BigDecimal;
import java.util.stream.Collectors;

import org.junit.Assert;
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
		Assert.assertFalse(result.hasErrors());
	}

	@Test
	public void testVerifyBankWithoutName() {
		final var bank = new Bank();
		bank.setSocialName("Bank Social Media");
		bank.setBankCalculationValues(createValidBankCalculationValues(bank));
		bank.setCalcType(CalcType.DEFAULT);

		final var verify = bankService.verify(bank);
		Assert.assertTrue(verify.hasErrors());
		Assert.assertEquals(1, verify.getErrors().size());
		Assert.assertEquals("name", verify.getErrors().get(0).getProperty());
	}

	@Test
	public void testVerifyBankWithoutSocialName() {
		final var bank = new Bank();
		bank.setName("Bank");
		bank.setBankCalculationValues(createValidBankCalculationValues(bank));
		bank.setCalcType(CalcType.DEFAULT);

		final var verify = bankService.verify(bank);
		Assert.assertTrue(verify.hasErrors());
		Assert.assertEquals(1, verify.getErrors().size());
		Assert.assertEquals("socialName", verify.getErrors().get(0).getProperty());
	}

	@Test
	public void testVerifyBankWithoutCommission() {
		final var bank = createValidBank();
		bank.setBankCalculationValues(createValidBankCalculationValues(bank, null));

		final var verify = bankService.verify(bank);
		Assert.assertTrue(verify.hasErrors());
		Assert.assertEquals(1, verify.getErrors().size());
		Assert.assertEquals("commission", verify.getErrors().get(0).getProperty());
	}

	@Test
	public void testVerifyBankWithNegativeCommission() {
		final var bank = createValidBank();
		bank.setBankCalculationValues(createValidBankCalculationValues(bank, BigDecimal.valueOf(-10)));

		final var verify = bankService.verify(bank);
		Assert.assertTrue(verify.hasErrors());
		Assert.assertEquals(1, verify.getErrors().size());
		Assert.assertEquals("commission", verify.getErrors().get(0).getProperty());
	}

	@Test
	public void testVerifyBankWithZeroCommission() {
		final var bank = createValidBank();
		bank.setBankCalculationValues(createValidBankCalculationValues(bank));

		final var verify = bankService.verify(bank);
		Assert.assertFalse(verify.hasErrors());
	}

	@Test
	public void testVerifyBankWithoutCalcType() {
		final var bank = createValidBank();
		bank.setBankCalculationValues(createValidBankCalculationValues(bank));
		bank.setCalcType(null);

		final var verify = bankService.verify(bank);
		Assert.assertTrue(verify.hasErrors());
		Assert.assertEquals(1, verify.getErrors().size());
		Assert.assertEquals("calcType", verify.getErrors().get(0).getProperty());
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

		final var bankLoadedList = bankService.findAll()
				.stream()
				.filter(b -> b.getId() == id)
				.collect(Collectors.toList());

		Assert.assertEquals(1, bankLoadedList.size());

		final var bankLoaded = bankLoadedList.get(0);
		Assert.assertEquals(id, bankLoaded.getId());
		Assert.assertEquals("Bank saved", bank.getName());
		Assert.assertEquals("Bank saved", bank.getSocialName());
		Assert.assertNotNull(bank.getBankCalculationValues());
	}

	private Bank createValidBank() {
		final var bank = new Bank();
		bank.setName("Bank saved");
		bank.setSocialName("Bank saved");
		bank.setCalcType(CalcType.DEFAULT);

		return bank;
	}

	private BankCalculationValues createValidBankCalculationValues(final Bank bank, final BigDecimal commission, final BigDecimal interestRate) {
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
