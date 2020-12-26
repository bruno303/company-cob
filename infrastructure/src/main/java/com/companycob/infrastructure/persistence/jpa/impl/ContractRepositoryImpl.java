package com.companycob.infrastructure.persistence.jpa.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.companycob.domain.model.entity.Contract;
import com.companycob.domain.model.persistence.ContractRepository;
import com.companycob.infrastructure.persistence.converter.ContractConverter;
import com.companycob.infrastructure.persistence.jpa.JpaContractRepository;

@Component
@Transactional
public class ContractRepositoryImpl implements ContractRepository {

	private final JpaContractRepository repository;

	@Autowired
	public ContractRepositoryImpl(final JpaContractRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public Contract save(final Contract entity) {
		final com.companycob.infrastructure.persistence.entity.Contract contractPersistence = ContractConverter
				.contractDomainToContractPersistence(entity);
		final com.companycob.infrastructure.persistence.entity.Contract saved = repository.save(contractPersistence);

		return ContractConverter.contractPersistenceToContractDomain(saved);
	}

	@Override
	public List<Contract> saveAll(final List<Contract> entities) {
		final List<com.companycob.infrastructure.persistence.entity.Contract> contracts = new ArrayList<>();

		entities.parallelStream()
				.forEach(entity -> contracts.add(ContractConverter.contractDomainToContractPersistence(entity)));

		final List<com.companycob.infrastructure.persistence.entity.Contract> contractsSaved = repository
				.saveAll(contracts);

		return contractsSaved.stream().map(ContractConverter::contractPersistenceToContractDomain)
				.collect(Collectors.toList());
	}

	@Override
	public void remove(final Contract entity) {
		final com.companycob.infrastructure.persistence.entity.Contract contractPersistence = ContractConverter
				.contractDomainToContractPersistence(entity);
		repository.delete(contractPersistence);
	}

	@Override
	public Optional<Contract> findById(final Long key) {
		final Optional<com.companycob.infrastructure.persistence.entity.Contract> optionalContract = repository
				.findById(key);

		return optionalContract.map(ContractConverter::contractPersistenceToContractDomain);
	}

	@Override
	public List<Contract> findAll() {
		final List<com.companycob.infrastructure.persistence.entity.Contract> all = repository.findAll();

		return all.stream().map(ContractConverter::contractPersistenceToContractDomain).collect(Collectors.toList());
	}

	@Override
	public List<Contract> findByContractNumber(final String contractNumber) {
		final List<com.companycob.infrastructure.persistence.entity.Contract> all = repository
				.findByContractNumber(contractNumber);

		return all.stream().map(ContractConverter::contractPersistenceToContractDomain).collect(Collectors.toList());
	}

	@Override
	public void removeAll() {
		repository.deleteAll();
	}

}
