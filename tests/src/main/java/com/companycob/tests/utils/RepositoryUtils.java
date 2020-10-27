package com.companycob.tests.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.companycob.domain.exception.ValidationException;
import com.companycob.domain.model.entity.Bank;
import com.companycob.domain.model.persistence.BankRepository;
import com.companycob.domain.model.persistence.ContractRepository;

@Component
public class RepositoryUtils {

	private final BankRepository bankRepository;

	@Autowired
	public RepositoryUtils(final ContractRepository contractRepository, final BankRepository bankRepository) {
		this.bankRepository = bankRepository;
	}

	public Bank saveBank(final Bank bank) throws ValidationException {
		return bankRepository.save(bank);
	}
}
