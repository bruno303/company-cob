package com.companycob.service;

import com.companycob.domain.exception.ValidationException;
import com.companycob.domain.model.dto.ValidationErrorsCollection;
import com.companycob.domain.model.entity.Bank;
import com.companycob.domain.model.entity.BankCalculationValues;
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

    public Bank save(Bank bank) throws ValidationException {
        var verifyResult = verify(bank);
        if (verifyResult.hasErrors()) {
            throw new ValidationException(verifyResult);
        }

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

        if (bank.getCalcType() == null) {
            result.addError("calcType", "CalcType should be defined for bank");
        }

        var errorsBankCalculationValues = verifyCalculationValues(bank.getBankCalculationValues());
        if (errorsBankCalculationValues.hasErrors()) {
            result.addAllErrors(errorsBankCalculationValues.getErrors());
        }

        return result;
    }

    private ValidationErrorsCollection verifyCalculationValues(BankCalculationValues bankCalculationValues) {
        ValidationErrorsCollection errors = new ValidationErrorsCollection();

        if (bankCalculationValues == null) {
            errors.addError("bankCalculationValues", "Bank calculation values should not be empty");;
            return errors;
        }

        if (bankCalculationValues.getCommission() == null || bankCalculationValues.getCommission().compareTo(BigDecimal.ZERO) < 0) {
            errors.addError("commission", "Commission should not be empty or negative");
        }

        return errors;
    }
}
