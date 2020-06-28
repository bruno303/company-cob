package com.companycob.service;

import com.companycob.domain.exception.ValidationException;
import com.companycob.domain.model.entity.BankCalculationValues;
import com.companycob.domain.model.enumerators.CalcType;
import com.companycob.domain.model.dto.ValidationErrorsCollection;
import com.companycob.domain.model.entity.Bank;
import com.companycob.tests.AbstractServiceTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class BankServiceTest extends AbstractServiceTest {

    @Autowired
    private BankService bankService;

    @Test
    public void testVerifyBankWithSuccess() {
        Bank bank = new Bank();
        bank.setName("Bank");
        bank.setSocialName("Bank Social Media");
        bank.setBankCalculationValues(createValidBankCalculationValues(bank));
        bank.setCalcType(CalcType.DEFAULT);

        ValidationErrorsCollection result = bankService.verify(bank);
        Assert.assertFalse(result.hasErrors());
    }

    @Test
    public void testVerifyBankWithoutName() {
        Bank bank = new Bank();
        bank.setSocialName("Bank Social Media");
        bank.setBankCalculationValues(createValidBankCalculationValues(bank));
        bank.setCalcType(CalcType.DEFAULT);

        ValidationErrorsCollection verify = bankService.verify(bank);
        Assert.assertTrue(verify.hasErrors());
        Assert.assertEquals(1, verify.getErrors().size());
        Assert.assertEquals("name", verify.getErrors().get(0).getProperty());
    }

    @Test
    public void testVerifyBankWithoutSocialName() {
        Bank bank = new Bank();
        bank.setName("Bank");
        bank.setBankCalculationValues(createValidBankCalculationValues(bank));
        bank.setCalcType(CalcType.DEFAULT);

        ValidationErrorsCollection verify = bankService.verify(bank);
        Assert.assertTrue(verify.hasErrors());
        Assert.assertEquals(1, verify.getErrors().size());
        Assert.assertEquals("socialName", verify.getErrors().get(0).getProperty());
    }

    @Test
    public void testVerifyBankWithoutCommission() {
        Bank bank = createValidBank();
        bank.setBankCalculationValues(createValidBankCalculationValues(bank, null));

        ValidationErrorsCollection verify = bankService.verify(bank);
        Assert.assertTrue(verify.hasErrors());
        Assert.assertEquals(1, verify.getErrors().size());
        Assert.assertEquals("commission", verify.getErrors().get(0).getProperty());
    }

    @Test
    public void testVerifyBankWithNegativeCommission() {
        Bank bank = createValidBank();
        bank.setBankCalculationValues(createValidBankCalculationValues(bank, BigDecimal.valueOf(-10)));

        ValidationErrorsCollection verify = bankService.verify(bank);
        Assert.assertTrue(verify.hasErrors());
        Assert.assertEquals(1, verify.getErrors().size());
        Assert.assertEquals("commission", verify.getErrors().get(0).getProperty());
    }

    @Test
    public void testVerifyBankWithZeroCommission() {
        Bank bank = createValidBank();
        bank.setBankCalculationValues(createValidBankCalculationValues(bank));

        ValidationErrorsCollection verify = bankService.verify(bank);
        Assert.assertFalse(verify.hasErrors());
    }

    @Test
    public void testVerifyBankWithoutCalcType() {
        Bank bank = createValidBank();
        bank.setBankCalculationValues(createValidBankCalculationValues(bank));
        bank.setCalcType(null);

        ValidationErrorsCollection verify = bankService.verify(bank);
        Assert.assertTrue(verify.hasErrors());
        Assert.assertEquals(1, verify.getErrors().size());
        Assert.assertEquals("calcType", verify.getErrors().get(0).getProperty());
    }

    @Test
    public void testSaveAndLoadBank() throws ValidationException {
        Bank bank = new Bank();
        bank.setName("Bank saved");
        bank.setSocialName("Bank saved");
        bank.setBankCalculationValues(createValidBankCalculationValues(bank, BigDecimal.valueOf(25)));
        bank.setCalcType(CalcType.DEFAULT);

        Bank saved = bankService.save(bank);
        final int id = saved.getId();

        List<Bank> bankLoadedList = bankService.findAll()
                .stream()
                .filter(b -> b.getId() == id)
                .collect(Collectors.toList());

        Assert.assertEquals(1, bankLoadedList.size());

        Bank bankLoaded = bankLoadedList.get(0);
        Assert.assertEquals(id, bankLoaded.getId());
        Assert.assertEquals("Bank saved", bank.getName());
        Assert.assertEquals("Bank saved", bank.getSocialName());
        Assert.assertNotNull(bank.getBankCalculationValues());
    }

    private Bank createValidBank() {
        Bank bank = new Bank();
        bank.setName("Bank saved");
        bank.setSocialName("Bank saved");
        bank.setCalcType(CalcType.DEFAULT);

        return bank;
    }

    private BankCalculationValues createValidBankCalculationValues(Bank bank, BigDecimal commission, BigDecimal interestRate) {
        BankCalculationValues bankCalculationValues = new BankCalculationValues();
        bankCalculationValues.setBank(bank);
        bankCalculationValues.setCommission(commission);
        bankCalculationValues.setInterestRate(interestRate);

        return bankCalculationValues;
    }

    private BankCalculationValues createValidBankCalculationValues(Bank bank, BigDecimal commission) {
        return createValidBankCalculationValues(bank, commission, BigDecimal.valueOf(0.10));
    }

    private BankCalculationValues createValidBankCalculationValues(Bank bank) {
        return createValidBankCalculationValues(bank, BigDecimal.TEN, BigDecimal.valueOf(0.10));
    }
}
