package com.companycob.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.companycob.domain.exception.ValidationException;
import com.companycob.domain.model.dto.ValidationErrorsCollection;
import com.companycob.domain.model.entity.Contract;
import com.companycob.domain.model.entity.Quota;
import com.companycob.domain.model.persistence.ContractRepository;

@Service
public class ContractService {

	private final ContractRepository contractRepository;
	private final QuotaService quotaService;

	@Autowired
	public ContractService(ContractRepository contractRepository, QuotaService quotaService) {
		super();
		this.contractRepository = contractRepository;
		this.quotaService = quotaService;
	}

	@Transactional
	public Contract save(Contract contract) throws ValidationException {

		verify(contract);

		return contractRepository.save(contract);
	}
	
	public void verify(Contract contract) throws ValidationException {
		List<ValidationErrorsCollection> errors = new ArrayList<>();
		
		ValidationErrorsCollection verifyContractResult = verifyContract(contract);
		
		if (verifyContractResult.hasErrors()) {
			errors.add(verifyContractResult);
		}
		
		errors.addAll(verifyQuotas(contract.getQuotas()));
		
		if (!errors.isEmpty()) {
			throw new ValidationException(errors);
		}
	}
	
	private List<ValidationErrorsCollection> verifyQuotas(List<Quota> quotas) {
		List<ValidationErrorsCollection> quotaErrors = quotas
				.parallelStream()
				.map(quotaService::verify)
				.filter(ValidationErrorsCollection::hasErrors)
				.collect(Collectors.toList());
		
		return quotaErrors;
	}

	private ValidationErrorsCollection verifyContract(Contract contract) {
		ValidationErrorsCollection result = new ValidationErrorsCollection();

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
		
		if (contract.getQuotas() == null || contract.getQuotas().isEmpty()) {
			result.addError("quotas", "Contract must have quotas");
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
