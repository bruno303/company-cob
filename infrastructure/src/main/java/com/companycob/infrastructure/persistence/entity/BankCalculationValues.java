package com.companycob.infrastructure.persistence.entity;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "bank_calculation_values", schema = "public")
public class BankCalculationValues {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
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

	public void setId(final long id) {
		this.id = id;
	}

	public BigDecimal getCommission() {
		return commission;
	}

	public void setCommission(final BigDecimal commission) {
		this.commission = commission;
	}

	public BigDecimal getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(final BigDecimal interestRate) {
		this.interestRate = interestRate;
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(final Bank bank) {
		this.bank = bank;
	}
}
