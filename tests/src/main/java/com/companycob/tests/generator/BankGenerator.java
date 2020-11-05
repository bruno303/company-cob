package com.companycob.tests.generator;

import com.companycob.domain.model.entity.Bank;
import com.companycob.domain.model.entity.BankCalculationValues;
import com.companycob.domain.model.enumerators.CalcType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class BankGenerator {

    public Bank generate(CalcType calcType, BigDecimal interestRate, BigDecimal commission) {
        Bank bank = new Bank();
        bank.setName("Bank");
        bank.setSocialName("Bank Social Name");
        bank.setBankCalculationValues(createValidBankCalculationValues(bank, interestRate, commission));
        bank.setCalcType(calcType);

        return bank;
    }

    public Bank generate(BigDecimal interestRate) {
        return generate(CalcType.DEFAULT, interestRate, BigDecimal.TEN);
    }

    public Bank generate() {
        return generate(CalcType.DEFAULT, BigDecimal.TEN, BigDecimal.TEN);
    }

    private BankCalculationValues createValidBankCalculationValues(Bank bank, BigDecimal interestRate,
                                                                   BigDecimal commission) {
        BankCalculationValues bankCalculationValues = new BankCalculationValues();
        bankCalculationValues.setBank(bank);
        bankCalculationValues.setCommission(commission);
        bankCalculationValues.setInterestRate(interestRate);

        return bankCalculationValues;
    }

}
