package com.companycob.infrastructure.persistence.converter;

import com.companycob.infrastructure.persistence.entity.Bank;
import com.companycob.infrastructure.persistence.entity.BankCalculationValues;

public class BankCalculationValuesConverter {

    private BankCalculationValuesConverter() { }

    public static BankCalculationValues domainToPersistence(com.companycob.domain.model.entity.BankCalculationValues domain,
                                                            Bank bank) {
        BankCalculationValues bcv = new BankCalculationValues();
        bcv.setCommission(domain.getCommission());
        bcv.setInterestRate(domain.getInterestRate());
        bcv.setBank(bank);

        return bcv;
    }

    public static com.companycob.domain.model.entity.BankCalculationValues persistenceToDomain(BankCalculationValues persistence) {
        return new com.companycob.domain.model.entity.BankCalculationValues(persistence.getCommission(),
                                                                            persistence.getInterestRate());
    }

}
