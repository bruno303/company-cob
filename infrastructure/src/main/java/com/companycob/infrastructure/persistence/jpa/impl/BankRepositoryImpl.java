package com.companycob.infrastructure.persistence.jpa.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.companycob.domain.model.entity.Bank;
import com.companycob.domain.model.persistence.BankRepository;
import com.companycob.infrastructure.persistence.converter.BankConverter;
import com.companycob.infrastructure.persistence.jpa.JpaBankRepository;

@Component
public class BankRepositoryImpl implements BankRepository {

	private final JpaBankRepository jpaBankRepository;

	@Autowired
	public BankRepositoryImpl(final JpaBankRepository jpaBankRepository) {
		this.jpaBankRepository = jpaBankRepository;
	}

	@Override
	public Bank save(final Bank entity) {
		final var bankPersistence = BankConverter.bankDomainToBankPersistence(entity);
		final var save = jpaBankRepository.save(bankPersistence);

		return BankConverter.bankPersistenceToBankDomain(save);
	}

	@Override
	public List<Bank> saveAll(final List<Bank> entities) {
		final var banks = entities.stream().map(BankConverter::bankDomainToBankPersistence)
				.collect(Collectors.toList());

		final var saveAll = jpaBankRepository.saveAll(banks);

		return saveAll.stream().map(BankConverter::bankPersistenceToBankDomain).collect(Collectors.toList());
	}

	@Override
	public void remove(final Bank entity) {
		final var bankPersistence = BankConverter.bankDomainToBankPersistence(entity);
		jpaBankRepository.delete(bankPersistence);
	}

	@Override
	public Optional<Bank> findById(final Integer integer) {
		final var result = jpaBankRepository.findById(integer);
		return result.map(BankConverter::bankPersistenceToBankDomain);
	}

	@Override
	public List<Bank> findAll() {
		final var all = jpaBankRepository.findAll();
		return all.stream().map(BankConverter::bankPersistenceToBankDomain).collect(Collectors.toList());
	}
}
