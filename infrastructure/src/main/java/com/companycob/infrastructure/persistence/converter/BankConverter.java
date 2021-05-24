package com.companycob.infrastructure.persistence.converter;

import com.companycob.domain.model.enumerators.CalcType;
import com.companycob.infrastructure.persistence.entity.Bank;

public class BankConverter {

    private BankConverter() { }

    public static Bank bankDomainToBankPersistence(com.companycob.domain.model.entity.Bank domainBank) {
        Bank persistenceBank = new Bank();
        persistenceBank.setId(domainBank.getId());
        persistenceBank.setName(domainBank.getName());
        persistenceBank.setSocialName(domainBank.getSocialName());
        persistenceBank.setCalcType(domainBank.getCalcType().getId());

        var bankCalculationValues =
            BankCalculationValuesConverter.domainToPersistence(domainBank.getBankCalculationValues(), persistenceBank);

        persistenceBank.setBankCalculationValues(bankCalculationValues);

        return persistenceBank;
    }

    public static com.companycob.domain.model.entity.Bank bankPersistenceToBankDomain(Bank persistenceBank) {
        var bankCalculationValues =
            BankCalculationValuesConverter.persistenceToDomain(persistenceBank.getBankCalculationValues());

        var domainBank = new com.companycob.domain.model.entity.Bank(
            persistenceBank.getId(),
            persistenceBank.getName(),
            persistenceBank.getSocialName(),
            CalcType.of(persistenceBank.getCalcType()),
            bankCalculationValues
        );

        return domainBank;
    }

}
