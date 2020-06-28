package com.companycob.domain.model.entity;

import com.companycob.domain.model.enumerators.CalcType;

import java.util.List;

public class Bank {
    private int id;
    private String name;
    private String socialName;
    private CalcType calcType;
    private BankCalculationValues bankCalculationValues;

    private List<Contract> contracts;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
}
