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
import com.companycob.domain.model.entity.Bank;
import com.companycob.domain.model.entity.Contract;
import com.companycob.domain.model.entity.Quota;
import com.companycob.domain.model.persistence.ContractRepository;
import com.companycob.service.calc.CalcService;
import com.companycob.service.lock.contract.ContractLocker;

@Service
public class ContractService {

	private final ContractRepository contractRepository;
	private final QuotaService quotaService;
	private final BankService bankService;
	private final CalcServicesProvider calcServicesProvider;
	private final ContractLocker contractLocker;

	@Autowired
	public ContractService(ContractRepository contractRepository, QuotaService quotaService,
						   BankService bankService, CalcServicesProvider calcServicesProvider, final ContractLocker contractLocker) {
		this.contractRepository = contractRepository;
		this.quotaService = quotaService;
		this.bankService = bankService;
		this.calcServicesProvider = calcServicesProvider;
		this.contractLocker = contractLocker;
	}

	@Transactional
	public Contract save(final Contract contract) throws ValidationException {

		contractLocker.tryLock(contract);
		try {
			verify(contract);

			return contractRepository.save(contract);
		} finally {
			contractLocker.release(contract);
		}
	}

	public void calculate(Contract contract) {
		final CalcService calcService = calcServicesProvider.getCalcService(contract);
		calcService.calculate(contract);
	}

	public void verify(Contract contract) throws ValidationException {
		final List<ValidationErrorsCollection> errors = new ArrayList<>();

		verifyContract(contract, errors);
		verifyQuotas(contract.getQuotas(), errors);
		verifyBank(contract.getBank(), errors);

		if (!errors.isEmpty()) {
			throw new ValidationException(errors);
		}
	}

	private void verifyQuotas(final List<Quota> quotas, final List<ValidationErrorsCollection> errors) {
		final var result = quotas.stream().map(quotaService::verify).filter(ValidationErrorsCollection::hasErrors)
				.collect(Collectors.toList());

		errors.addAll(result);
	}

	private void verifyBank(final Bank bank, final List<ValidationErrorsCollection> errors) {
		final ValidationErrorsCollection verify = bankService.verify(bank);

		if (verify.hasErrors()) {
			errors.add(verify);
		}
	}

	private void verifyContract(final Contract contract, final List<ValidationErrorsCollection> errors) {
		final ValidationErrorsCollection result = new ValidationErrorsCollection();

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
	public Optional<Contract> findById(final Long id) {
		return contractRepository.findById(id);
	}

	@Transactional
	public List<Contract> findByContractNumber(final String contractNumber) {
		return contractRepository.findByContractNumber(contractNumber);
	}
}
