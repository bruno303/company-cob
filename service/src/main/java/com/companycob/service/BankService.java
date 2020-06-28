package com.companycob.service;

import com.companycob.domain.model.dto.ValidationErrorsCollection;
import com.companycob.domain.model.entity.Bank;
import com.companycob.domain.model.persistence.BankRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BankService {

    private final BankRepository bankRepository;

    @Autowired
    public BankService(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    public Bank save(Bank bank) {
        return bankRepository.save(bank);
    }

    public List<Bank> findAll() {
        return bankRepository.findAll();
    }

    public ValidationErrorsCollection verify(Bank bank) {
        var result = new ValidationErrorsCollection();

        if (bank == null) {
            result.addError("bank", "Bank should not be null!");
            return result;
        }

        if (bank.getName() == null || StringUtils.isEmpty(bank.getName())) {
            result.addError("name", "Bank's name should not be empty");
        }

        if (bank.getSocialName() == null || StringUtils.isEmpty(bank.getSocialName())) {
            result.addError("socialName", "Bank's social name should not be empty");
        }

        if (bank.getCommission() == null || bank.getCommission().compareTo(BigDecimal.ZERO) < 0) {
            result.addError("commission", "Comission should not be zero or positive");
        }

        return result;
    }
}
