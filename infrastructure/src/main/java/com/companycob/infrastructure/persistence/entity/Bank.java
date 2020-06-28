package com.companycob.infrastructure.persistence.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "BANK")
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "NAME", nullable = false, length = 300)
    private String name;

    @Column(name = "SOCIAL_NAME", nullable = false, length = 300)
    private String socialName;

    @OneToMany(mappedBy = "bank", fetch = FetchType.LAZY)
    private List<Contract> contracts;

    @Column(name = "CALC_TYPE", nullable = false)
    private int calcType;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "bank")
    private BankCalculationValues bankCalculationValues;

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

    public int getCalcType() {
        return calcType;
    }

    public void setCalcType(int calcType) {
        this.calcType = calcType;
    }

    public BankCalculationValues getBankCalculationValues() {
        return bankCalculationValues;
    }

    public void setBankCalculationValues(BankCalculationValues bankCalculationValues) {
        this.bankCalculationValues = bankCalculationValues;
    }
}
