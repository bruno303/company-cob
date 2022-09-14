package com.companycob.tests.fixture.unit;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;

import com.companycob.domain.model.entity.Bank;
import com.companycob.domain.model.entity.BankCalculationValues;
import com.companycob.domain.model.entity.Contract;
import com.companycob.domain.model.entity.Quota;
import com.companycob.domain.model.enumerators.CalcType;

public class Generator {

    public Contract generateContract() {
        return generateContract(true, true);
    }

    public Contract generateContract(boolean generateQuotas, boolean generateBank) {
        Contract contract = new Contract();
        contract.setDate(LocalDate.now());
        contract.setContractNumber(RandomStringUtils.randomAlphanumeric(20));

        if (generateBank) {
            contract.setBank(generateBank());
        }

        if (generateQuotas) {
            contract.setQuotas(generateQuotas(contract, 2));
        }

        return contract;
    }

    public Contract generateContract(Bank bank) {
        Contract contract = new Contract();
        contract.setDate(LocalDate.now());
        contract.setContractNumber(RandomStringUtils.randomAlphanumeric(20));
        contract.setBank(bank);
        contract.setQuotas(generateQuotas(contract, 2));

        return contract;
    }

    public Bank generateBank(CalcType calcType, BigDecimal interestRate, BigDecimal commission) {
        Bank bank = new Bank();
        bank.setName("Bank");
        bank.setSocialName("Bank Social Name");
        bank.setBankCalculationValues(createValidBankCalculationValues(bank, interestRate, commission));
        bank.setCalcType(calcType);

        return bank;
    }

    public Bank generateBank(BigDecimal interestRate) {
        return generateBank(CalcType.DEFAULT, interestRate, BigDecimal.TEN);
    }

    public Bank generateBank() {
        return generateBank(CalcType.DEFAULT, BigDecimal.TEN, BigDecimal.TEN);
    }

    public BankCalculationValues createValidBankCalculationValues(Bank bank, BigDecimal interestRate,
                                                                   BigDecimal commission) {
        BankCalculationValues bankCalculationValues = new BankCalculationValues();
        bankCalculationValues.setBank(bank);
        bankCalculationValues.setCommission(commission);
        bankCalculationValues.setInterestRate(interestRate);

        return bankCalculationValues;
    }

    public List<Quota> generateQuotas(final Contract contract, final int quotaCount) {
        final List<Quota> quotas = new ArrayList<>();

        LocalDate date = LocalDate.now().minusDays(181);

        for (int i = 0; i < quotaCount; i++) {
            final Quota quota = new Quota();
            quota.setContract(contract);
            quota.setDueDate(date);
            quota.setInitialValue(BigDecimal.valueOf(200));
            quota.setNumber(i + 1);

            quotas.add(quota);

            date = date.plusMonths(1);
        }

        return quotas;
    }

    public Contract copy(Contract source) {
        Contract target = new Contract();
        target.setBank(source.getBank());
        target.setContractNumber(source.getContractNumber());
        target.setQuotas(source.getQuotas());
        target.setDate(source.getDate());
        target.setId(source.getId());
        return target;
    }

}
