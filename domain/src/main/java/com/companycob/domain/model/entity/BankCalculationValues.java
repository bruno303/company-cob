package com.companycob.domain.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import com.companycob.domain.exception.DomainException;
import com.companycob.domain.utils.BigDecimalUtils;

public class BankCalculationValues implements Serializable {

    private BigDecimal commission;
    private BigDecimal interestRate;
    private Bank bank;

    public BankCalculationValues(BigDecimal commission, BigDecimal interestRate) {
        this.commission = commission;
        this.interestRate = interestRate;
        validate();
    }

    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    private void validate() {
        DomainException.throwsWhen(commission == null, "Commission should not be empty");
        DomainException.throwsWhen(BigDecimalUtils.lesserThanZero(commission), "Commission should not be negative");
        DomainException.throwsWhen(interestRate == null, "Interest rate should not be empty");
        DomainException.throwsWhen(BigDecimalUtils.lesserThanZero(interestRate), "Interest rate should not be negative");
    }
}
