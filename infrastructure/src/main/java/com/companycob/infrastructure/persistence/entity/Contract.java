package com.companycob.infrastructure.persistence.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "contract", schema = "public")
public class Contract {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;

	@Column(name = "CONTRACT_NUMBER", length = 60, nullable = false)
	private String contractNumber;

	@Column(name = "DATE")
	private LocalDate date;

	@OneToMany(mappedBy = "contract", cascade = CascadeType.ALL)
	private List<Quota> quotas;

	@ManyToOne(optional = false)
	@JoinColumn(name = "ID_BANK", nullable = false)
	private Bank bank;

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(final String contractNumber) {
		this.contractNumber = contractNumber;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(final LocalDate date) {
		this.date = date;
	}

	public List<Quota> getQuotas() {
		return quotas;
	}

	public void setQuotas(final List<Quota> quotas) {
		this.quotas = quotas;
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(final Bank bank) {
		this.bank = bank;
	}
}
