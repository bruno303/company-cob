package com.companycob.domain.model.entity;

import com.companycob.domain.exception.DomainException;
import com.companycob.domain.model.enumerators.CalcType;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class Bank implements Entity {
    private Long id;
    private String name;
    private String socialName;
    private CalcType calcType;
    private BankCalculationValues bankCalculationValues;

    private List<Contract> contracts;

    public Bank(Long id, String name, String socialName, CalcType calcType, BankCalculationValues calculationValues){
        this.id = id;
        this.name = name;
        this.socialName = socialName;
        this.calcType = calcType;
        this.bankCalculationValues = calculationValues;
        validate();
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSocialName() {
        return socialName;
    }

    public void setSocialName(String socialName) {
        this.socialName = socialName;
    }

    public List<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(List<Contract> contracts) {
        this.contracts = contracts;
    }

    public CalcType getCalcType() {
        return calcType;
    }

    public void setCalcType(CalcType calcType) {
        this.calcType = calcType;
    }

    public BankCalculationValues getBankCalculationValues() {
        return bankCalculationValues;
    }

    public void setBankCalculationValues(BankCalculationValues bankCalculationValues) {
        this.bankCalculationValues = bankCalculationValues;
    }

    private void validate() {
        DomainException.throwsWhen(StringUtils.isEmpty(name), "Bank's name should not be empty");
        DomainException.throwsWhen(StringUtils.isEmpty(socialName), "Bank's social name should not be empty");
        DomainException.throwsWhen(calcType == null, "CalcType should be defined for bank");
        DomainException.throwsWhen(bankCalculationValues == null, "CalculationValues should be defined for bank");
    }
}
