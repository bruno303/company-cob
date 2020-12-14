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
        var domainBank = new com.companycob.domain.model.entity.Bank();
        domainBank.setId(persistenceBank.getId());
        domainBank.setName(persistenceBank.getName());
        domainBank.setSocialName(persistenceBank.getSocialName());
        domainBank.setCalcType(CalcType.of(persistenceBank.getCalcType()));

        var bankCalculationValues =
            BankCalculationValuesConverter.persistenceToDomain(persistenceBank.getBankCalculationValues(), domainBank);

        domainBank.setBankCalculationValues(bankCalculationValues);

        return domainBank;
    }

}
