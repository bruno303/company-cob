package com.companycob.infrastructure.persistence.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "bank", schema = "public")
public class Bank {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
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

	public void setId(final int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getSocialName() {
		return socialName;
	}

	public void setSocialName(final String socialName) {
		this.socialName = socialName;
	}

	public List<Contract> getContracts() {
		return contracts;
	}

	public void setContracts(final List<Contract> contracts) {
		this.contracts = contracts;
	}

	public int getCalcType() {
		return calcType;
	}

	public void setCalcType(final int calcType) {
		this.calcType = calcType;
	}

	public BankCalculationValues getBankCalculationValues() {
		return bankCalculationValues;
	}

	public void setBankCalculationValues(final BankCalculationValues bankCalculationValues) {
		this.bankCalculationValues = bankCalculationValues;
	}
}
