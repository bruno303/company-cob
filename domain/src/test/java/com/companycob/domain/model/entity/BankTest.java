package com.companycob.domain.model.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

import java.math.BigDecimal;

import com.companycob.domain.exception.DomainException;
import com.companycob.domain.model.enumerators.CalcType;

import org.junit.Test;

public class BankTest {

	@Test
	public void testVerifyBankWithSuccess() {
        BankCalculationValues calcValues = createValidBankCalculationValues();

        final var bank = new Bank(
            1L,
            "Bank",
            "Bank Social Name",
            CalcType.DEFAULT,
            calcValues
        );
        
        assertThat(bank).isNotNull();
        assertThat(bank.getId()).isEqualTo(1);
        assertThat(bank.getName()).isEqualTo("Bank");
        assertThat(bank.getSocialName()).isEqualTo("Bank Social Name");
        assertThat(bank.getCalcType()).isEqualTo(CalcType.DEFAULT);
        assertThat(bank.getBankCalculationValues().getCommission()).isEqualByComparingTo(calcValues.getCommission());
        assertThat(bank.getBankCalculationValues().getInterestRate()).isEqualByComparingTo(calcValues.getInterestRate());
	}

	@Test
	public void testVerifyBankWithNameEmpty() {

        assertThrows(DomainException.class, () -> {
            new Bank(
                1L,
                "",
                "Bank Social Name",
                CalcType.DEFAULT,
                createValidBankCalculationValues());
        });
	}

    @Test
	public void testVerifyBankWithNameNull() {

        assertThrows(DomainException.class, () -> {
            new Bank(
                1L,
                null,
                "Bank Social Name",
                CalcType.DEFAULT,
                createValidBankCalculationValues());
        });
	}

	@Test
	public void testVerifyBankWithSocialNameEmpty() {

        assertThrows(DomainException.class, () -> {
            new Bank(
                1L,
                "Bank",
                "",
                CalcType.DEFAULT,
                createValidBankCalculationValues());
        });
	}

    @Test
	public void testVerifyBankWithSocialNameNull() {

        assertThrows(DomainException.class, () -> {
            new Bank(
                1L,
                "Bank",
                "",
                CalcType.DEFAULT,
                createValidBankCalculationValues());
        });
	}

	@Test
	public void testVerifyBankWithCommissionNull() {

        assertThrows(DomainException.class, () -> {
            new Bank(
                1L,
                "Bank",
                "",
                CalcType.DEFAULT,
                createValidBankCalculationValues(null));
        });
	}

    @Test
	public void testVerifyBankWithNegativeCommission() {

        assertThrows(DomainException.class, () -> {
            new Bank(
                1L,
                "Bank",
                "",
                CalcType.DEFAULT,
                createValidBankCalculationValues(BigDecimal.valueOf(-10)));
        });
	}

	@Test
	public void testVerifyBankWithCommissionZero() {

        assertThrows(DomainException.class, () -> {
            new Bank(
                1L,
                "Bank",
                "",
                CalcType.DEFAULT,
                createValidBankCalculationValues(BigDecimal.ZERO));
        });
	}

	@Test
	public void testVerifyBankWithCalcTypeNull() {
    
        assertThrows(DomainException.class, () -> {
            new Bank(
                1L,
                "Bank",
                "",
                CalcType.DEFAULT,
                null);
        });
    }

	private BankCalculationValues createValidBankCalculationValues(final BigDecimal commission, final BigDecimal interestRate) {
		return new BankCalculationValues(commission, interestRate);
	}

	private BankCalculationValues createValidBankCalculationValues(final BigDecimal commission) {
		return createValidBankCalculationValues(commission, BigDecimal.valueOf(0.10));
	}

	private BankCalculationValues createValidBankCalculationValues() {
		return createValidBankCalculationValues(BigDecimal.TEN, BigDecimal.valueOf(0.10));
	}
}
