package com.companycob.domain.model.persistence;

import java.util.List;

import com.companycob.domain.model.entity.Contract;

public interface ContractRepository extends Repository<Contract, Long> {

	List<Contract> findByContractNumber(String contractNumber);
	
}
