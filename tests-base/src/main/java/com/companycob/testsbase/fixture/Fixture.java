package com.companycob.testsbase.fixture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.companycob.domain.model.persistence.BankRepository;
import com.companycob.domain.model.persistence.ContractRepository;

@Component
public class Fixture {

	@Autowired
	private BankRepository bankRepository;

	@Autowired
	private ContractRepository contractRepository;

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void clearDatabase() {
		contractRepository.removeAll();
		bankRepository.removeAll();
	}

}
