package com.companycob.tests.utils;

import com.companycob.domain.model.entity.Bank;
import com.companycob.domain.model.persistence.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RepositoryUtils {

	private final BankRepository bankRepository;

	@Autowired
	public RepositoryUtils(final BankRepository bankRepository) {
		this.bankRepository = bankRepository;
	}

	public Bank saveBank(final Bank bank) {
		return bankRepository.save(bank);
	}
}
