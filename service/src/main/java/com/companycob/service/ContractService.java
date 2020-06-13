package com.companycob.service;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.companycob.domain.exception.ValidationException;
import com.companycob.domain.model.dto.ValidationErrorsDTO;
import com.companycob.domain.model.entity.Contract;
import com.companycob.domain.model.persistence.ContractRepository;

@Service
public class ContractService {
	
	private final ContractRepository contractRepository;

	@Autowired
	public ContractService(ContractRepository contractRepository) {
		super();
		this.contractRepository = contractRepository;
	}

	@Transactional
	public Contract save(Contract contract) throws ValidationException {
		
		var validationResult = verify(contract);
		
		if (validationResult.hasErrors()) {
			throw new ValidationException(validationResult);
		}
		
		return contractRepository.save(contract);
	}
	
	public ValidationErrorsDTO verify(Contract contract) {
		ValidationErrorsDTO result = new ValidationErrorsDTO();
		
		if (contract == null) {
			result.addError("contract", "Contract can't be null");
			return result;
		}
		
		if (contract.getContractNumber() == null || StringUtils.isEmpty(contract.getContractNumber())) {
			result.addError("contractNumber", "Contract number can't be null or empty");
		}
		
		if (contract.getDate() == null) {
			result.addError("date", "Contract date can't be null");
		}
		
		return result;
	}

	@Transactional
	public Optional<Contract> findById(Long id) {
		return contractRepository.findById(id);
	}

	@Transactional
	public List<Contract> findByContractNumber(String contractNumber) {
		return contractRepository.findByContractNumber(contractNumber);
	}
}
