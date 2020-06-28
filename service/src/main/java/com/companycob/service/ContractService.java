package com.companycob.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.companycob.domain.model.entity.Bank;
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
	private final BankService bankService;

	@Autowired
	public ContractService(ContractRepository contractRepository, QuotaService quotaService, BankService bankService) {
		super();
		this.contractRepository = contractRepository;
		this.quotaService = quotaService;
		this.bankService = bankService;
	}

	@Transactional
	public Contract save(Contract contract) throws ValidationException {

		verify(contract);

		return contractRepository.save(contract);
	}
	
	public void verify(Contract contract) throws ValidationException {
		List<ValidationErrorsCollection> errors = new ArrayList<>();
		
		verifyContract(contract, errors);
		verifyQuotas(contract.getQuotas(), errors);
		verifyBank(contract.getBank(), errors);

		if (!errors.isEmpty()) {
			throw new ValidationException(errors);
		}
	}
	
	private void verifyQuotas(List<Quota> quotas, List<ValidationErrorsCollection> errors) {
		var result = quotas
				.stream()
				.map(quotaService::verify)
				.filter(ValidationErrorsCollection::hasErrors)
				.collect(Collectors.toList());

		errors.addAll(result);
	}

	private void verifyBank(Bank bank, List<ValidationErrorsCollection> errors) {
		ValidationErrorsCollection verify = bankService.verify(bank);

		if (verify.hasErrors()) {
			errors.add(verify);
		}
	}

	private void verifyContract(Contract contract, List<ValidationErrorsCollection> errors) {
		ValidationErrorsCollection result = new ValidationErrorsCollection();

		if (contract == null) {
			result.addError("contract", "Contract can't be null");
			errors.add(result);
			return;
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

		if (contract.getBank() == null) {
			result.addError("bank", "Contract must have a bank");
		}

		if (result.hasErrors()) {
			errors.add(result);
		}
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
