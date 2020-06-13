package com.companycob.infrastructure.persistence.jpa;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.companycob.domain.model.entity.Contract;
import com.companycob.domain.model.persistence.ContractRepository;
import com.companycob.infrastructure.persistence.converter.ContractConverter;

@Component
public class ContractRepositoryImpl implements ContractRepository {
	
	private final JpaContractRepository repository;
	
	@Autowired
	public ContractRepositoryImpl(JpaContractRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public Contract save(Contract entity) {
		com.companycob.infrastructure.persistence.jpa.entity.Contract contractPersistence = ContractConverter.contractDomainToContractPersistence(entity);
		com.companycob.infrastructure.persistence.jpa.entity.Contract saved = repository.save(contractPersistence);
		
		return ContractConverter.contractPersistenceToContractDomain(saved);
	}

	@Override
	public List<Contract> saveAll(List<Contract> entities) {
		List<com.companycob.infrastructure.persistence.jpa.entity.Contract> contracts = new ArrayList<>();
		
		entities.parallelStream().forEach(entity -> contracts.add(ContractConverter.contractDomainToContractPersistence(entity)));
		
		List<com.companycob.infrastructure.persistence.jpa.entity.Contract> contractsSaved = repository.saveAll(contracts);
		
		return contractsSaved
				.stream()
				.map(ContractConverter::contractPersistenceToContractDomain)
				.collect(Collectors.toList());
	}

	@Override
	public void remove(Contract entity) {
		com.companycob.infrastructure.persistence.jpa.entity.Contract contractPersistence = ContractConverter.contractDomainToContractPersistence(entity);
		repository.delete(contractPersistence);
	}

	@Override
	public Optional<Contract> findById(Long key) {
		Optional<com.companycob.infrastructure.persistence.jpa.entity.Contract> optionalContract = repository.findById(key);
		
		return optionalContract.map(ContractConverter::contractPersistenceToContractDomain);
	}

	@Override
	public List<Contract> findAll() {
		List<com.companycob.infrastructure.persistence.jpa.entity.Contract> all = repository.findAll();
		
		return all
				.stream()
				.map(ContractConverter::contractPersistenceToContractDomain)
				.collect(Collectors.toList());
	}

	@Override
	public List<Contract> findByContractNumber(String contractNumber) {
		List<com.companycob.infrastructure.persistence.jpa.entity.Contract> all = repository.findByContractNumber(contractNumber);
		
		return all
				.stream()
				.map(ContractConverter::contractPersistenceToContractDomain)
				.collect(Collectors.toList());
	}

}
