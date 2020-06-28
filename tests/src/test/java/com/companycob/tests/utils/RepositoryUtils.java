package com.companycob.tests.utils;

import com.companycob.domain.exception.ValidationException;
import com.companycob.domain.model.entity.Bank;
import com.companycob.domain.model.persistence.BankRepository;
import com.companycob.domain.model.persistence.ContractRepository;
import com.companycob.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RepositoryUtils {

    private final ContractRepository contractRepository;

    private final BankService bankService;

    @Autowired
    public RepositoryUtils(ContractRepository contractRepository, BankService bankService) {
        this.contractRepository = contractRepository;
        this.bankService = bankService;
    }

    public Bank saveBank(Bank bank) throws ValidationException {
        return bankService.save(bank);
    }
}
