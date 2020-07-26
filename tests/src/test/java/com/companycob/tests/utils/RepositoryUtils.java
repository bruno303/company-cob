package com.companycob.tests.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.companycob.domain.exception.ValidationException;
import com.companycob.domain.model.entity.Bank;
import com.companycob.domain.model.persistence.ContractRepository;
import com.companycob.service.BankService;

@Component
public class RepositoryUtils {

	private final BankService bankService;

	@Autowired
	public RepositoryUtils(final ContractRepository contractRepository, final BankService bankService) {
		this.bankService = bankService;
	}

	public Bank saveBank(final Bank bank) throws ValidationException {
		return bankService.save(bank);
	}
}
