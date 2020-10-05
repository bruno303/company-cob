package com.companycob.infrastructure.persistence.converter;

import com.companycob.infrastructure.persistence.entity.Bank;
import com.companycob.infrastructure.persistence.entity.BankCalculationValues;

public class BankCalculationValuesConverter {

    public static BankCalculationValues domainToPersistence(com.companycob.domain.model.entity.BankCalculationValues domain,
                                                            Bank bank) {
        BankCalculationValues bcv = new BankCalculationValues();
        bcv.setId(domain.getId());
        bcv.setCommission(domain.getCommission());
        bcv.setInterestRate(domain.getInterestRate());
        bcv.setBank(bank);

        return bcv;
    }

    public static com.companycob.domain.model.entity.BankCalculationValues persistenceToDomain(BankCalculationValues persistence,
                                                                                               com.companycob.domain.model.entity.Bank bank) {
        var bcv = new com.companycob.domain.model.entity.BankCalculationValues();
        bcv.setId(persistence.getId());
        bcv.setCommission(persistence.getCommission());
        bcv.setInterestRate(persistence.getInterestRate());
        bcv.setBank(bank);

        return bcv;
    }

}
