package com.companycob.infrastructure.persistence.converter;

import com.companycob.infrastructure.persistence.entity.Contract;
import com.companycob.infrastructure.persistence.entity.Quota;

import java.util.List;
import java.util.stream.Collectors;

public class QuotaConverter {

	private QuotaConverter() { }
	
	public static List<Quota> quotaDomainListToQuotaPersistenceList(List<com.companycob.domain.model.entity.Quota> domainQuotas,
			Contract persistenceContract) {
		return domainQuotas
				.parallelStream()
				.map(c -> QuotaConverter.quotaDomainToQuotaPersistence(c, persistenceContract))
				.collect(Collectors.toList());
	}
	
	public static Quota quotaDomainToQuotaPersistence(com.companycob.domain.model.entity.Quota domainQuota,
			Contract persistenceContract) {
		Quota quota = new Quota();
		quota.setId(domainQuota.getId());
		quota.setNumber(domainQuota.getNumber());
		quota.setInitialValue(domainQuota.getInitialValue());
		quota.setUpdatedValue(domainQuota.getUpdatedValue());
		quota.setDueDate(domainQuota.getDueDate());
		quota.setContract(persistenceContract);
		
		return quota;
	}
	
	public static List<com.companycob.domain.model.entity.Quota> quotaPersistenceListToQuotaDomainList(List<Quota> persistenceQuotas,
			com.companycob.domain.model.entity.Contract domainContract) {
		return persistenceQuotas
				.parallelStream()
				.map(c -> QuotaConverter.quotaPersistenceToQuotaDomain(c, domainContract))
				.collect(Collectors.toList());
	}
	
	public static com.companycob.domain.model.entity.Quota quotaPersistenceToQuotaDomain(Quota persistenceQuota,
			com.companycob.domain.model.entity.Contract domainContract) {
		var quota = new com.companycob.domain.model.entity.Quota();
		quota.setId(persistenceQuota.getId());
		quota.setNumber(persistenceQuota.getNumber());
		quota.setInitialValue(persistenceQuota.getInitialValue());
		quota.setUpdatedValue(persistenceQuota.getUpdatedValue());
		quota.setDueDate(persistenceQuota.getDueDate());
		quota.setContract(domainContract);
		
		return quota;
	}

}
