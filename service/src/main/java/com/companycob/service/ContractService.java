package com.companycob.service;

import com.companycob.domain.model.entity.Contract;
import com.companycob.domain.model.persistence.ContractRepository;
import com.companycob.infrastructure.cache.RedisCacheConfig;
import com.companycob.service.lock.contract.ContractLocker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ContractService {

	private final ContractRepository contractRepository;
	private final ContractLocker contractLocker;

	@Autowired
	public ContractService(ContractRepository contractRepository, ContractLocker contractLocker) {
		this.contractRepository = contractRepository;
		this.contractLocker = contractLocker;
	}

	@Transactional
	@CacheEvict(cacheNames = RedisCacheConfig.CONTRACT_CACHE_NAME)
	@CachePut(cacheNames = RedisCacheConfig.CONTRACT_CACHE_NAME)
	public Contract save(final Contract contract) {

		contractLocker.tryLock(contract);
		try {
			return contractRepository.save(contract);
		} finally {
			contractLocker.release(contract);
		}
	}

	@Transactional
	@Cacheable(cacheNames = RedisCacheConfig.CONTRACT_CACHE_NAME)
	public Optional<Contract> findById(final Long id) {
		return contractRepository.findById(id);
	}

	@Transactional
	@Cacheable(cacheNames = RedisCacheConfig.CONTRACT_CACHE_NAME)
	public List<Contract> findByContractNumber(final String contractNumber) {
		return contractRepository.findByContractNumber(contractNumber);
	}
}
