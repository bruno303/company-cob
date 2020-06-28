package com.companycob.infrastructure.persistence.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "BANK_CALCULATION_VALUES")
public class BankCalculationValues {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "COMMISSION", precision = 12, scale = 2, nullable = false)
    private BigDecimal commission;

    @Column(name = "INTEREST_RATE", precision = 12, scale = 2)
    private BigDecimal interestRate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_BANK", nullable = false)
    private Bank bank;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }
}
