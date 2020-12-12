package com.companycob.infrastructure.persistence.converter;

import com.companycob.infrastructure.persistence.entity.Contract;

public class ContractConverter {

	private ContractConverter() { }

	public static Contract contractDomainToContractPersistence(com.companycob.domain.model.entity.Contract domainContract) {
		Contract persistenceContract = new Contract();
		persistenceContract.setId(domainContract.getId());
		persistenceContract.setDate(domainContract.getDate());
		persistenceContract.setContractNumber(domainContract.getContractNumber());
		persistenceContract.setQuotas(QuotaConverter.quotaDomainListToQuotaPersistenceList(domainContract.getQuotas(), persistenceContract));
		persistenceContract.setBank(BankConverter.bankDomainToBankPersistence(domainContract.getBank()));
		return persistenceContract;
	}
	
	public static com.companycob.domain.model.entity.Contract contractPersistenceToContractDomain(Contract persistenceContract) {
		com.companycob.domain.model.entity.Contract domainContract = new com.companycob.domain.model.entity.Contract();
		domainContract.setId(persistenceContract.getId());
		domainContract.setDate(persistenceContract.getDate());
		domainContract.setContractNumber(persistenceContract.getContractNumber());
		domainContract.setQuotas(QuotaConverter.quotaPersistenceListToQuotaDomainList(persistenceContract.getQuotas(), domainContract));
		domainContract.setBank(BankConverter.bankPersistenceToBankDomain(persistenceContract.getBank()));
		return domainContract;
	}
	
}
