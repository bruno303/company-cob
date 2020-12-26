package com.companycob.tests.fixture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.companycob.domain.model.persistence.BankRepository;
import com.companycob.domain.model.persistence.ContractRepository;

@Component
public class Fixture {

	@Autowired
	private BankRepository bankRepository;

	@Autowired
	private ContractRepository contractRepository;

	public void clearDatabase() {
		contractRepository.removeAll();
		bankRepository.removeAll();
	}

}
