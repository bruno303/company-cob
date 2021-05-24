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
        return new Contract(1L, RandomStringUtils.randomAlphanumeric(20), LocalDate.now(), generateQuotas(2), generateBank());
    }

    public Contract generateContract(Bank bank) {
        return new Contract(1L, RandomStringUtils.randomAlphanumeric(20), LocalDate.now(), generateQuotas(2), bank);
    }

    public Bank generateBank(CalcType calcType, BigDecimal interestRate, BigDecimal commission) {
        return new Bank(
            1L,
            "Bank",
            "Bank Social Name",
            calcType,
            createValidBankCalculationValues(interestRate, commission)
        );
    }

    public Bank generateBank(BigDecimal interestRate) {
        return generateBank(CalcType.DEFAULT, interestRate, BigDecimal.TEN);
    }

    public Bank generateBank() {
        return generateBank(CalcType.DEFAULT, BigDecimal.TEN, BigDecimal.TEN);
    }

    public BankCalculationValues createValidBankCalculationValues(BigDecimal interestRate,
                                                                  BigDecimal commission) {
        return new BankCalculationValues(
            commission,
            interestRate
        );
    }

    public List<Quota> generateQuotas(final int quotaCount) {
        final List<Quota> quotas = new ArrayList<>();

        LocalDate date = LocalDate.now().minusDays(181);

        for (int i = 0; i < quotaCount; i++) {
            final Quota quota = new Quota(Long.valueOf(1), i + 1, BigDecimal.valueOf(200), null, date, 181L);
            quotas.add(quota);

            date = date.plusMonths(1);
        }

        return quotas;
    }

    public Contract copy(Contract source) {
        return new Contract(source.getId(), source.getContractNumber(), source.getDate(), source.getQuotas(), source.getBank());
    }

}
