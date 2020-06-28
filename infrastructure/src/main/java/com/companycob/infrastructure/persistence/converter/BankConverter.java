package com.companycob.infrastructure.persistence.converter;

import com.companycob.infrastructure.persistence.jpa.entity.Bank;

public class BankConverter {

    public static Bank bankDomainToBankPersistence(com.companycob.domain.model.entity.Bank domainBank) {
        Bank persistenceBank = new Bank();
        persistenceBank.setId(domainBank.getId());
        persistenceBank.setCommission(domainBank.getCommission());
        persistenceBank.setName(domainBank.getName());
        persistenceBank.setSocialName(domainBank.getSocialName());

        return persistenceBank;
    }

    public static com.companycob.domain.model.entity.Bank bankPersistenceToBankDomain(Bank persistenceBank) {
        var domainBank = new com.companycob.domain.model.entity.Bank();
        domainBank.setId(persistenceBank.getId());
        domainBank.setCommission(persistenceBank.getCommission());
        domainBank.setName(persistenceBank.getName());
        domainBank.setSocialName(persistenceBank.getSocialName());

        return domainBank;
    }

}
