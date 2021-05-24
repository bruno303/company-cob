package com.companycob.domain.model.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.companycob.domain.exception.DomainException;
import com.companycob.domain.lock.Lockable;

import org.apache.commons.lang3.StringUtils;

public class Contract implements Entity, Lockable {

	private Long id;
	private String contractNumber;
	private LocalDate date;
	private List<Quota> quotas;
	private Bank bank;

	public Contract(Long id, String contractNumber, LocalDate date, List<Quota> quotas, Bank bank) {
		this.id = id;
		this.contractNumber = contractNumber;
		this.date = date;
		this.quotas = quotas;
		this.bank = bank;
		validate();
	}

	@Override
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
		if (quotas == null) {
			quotas = new ArrayList<>();
		}

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

	@Override
	public String toString() {
		return "Contract [id=" + id + ", contractNumber=" + contractNumber + ", date=" + date + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Contract)) {
			return false;
		}
		final Contract other = (Contract) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String getKey() {
		return String.format("contract:%s", this.getContractNumber());
	}

	private void validate() {
		
		DomainException.throwsWhen(StringUtils.isEmpty(contractNumber), "Contract number can't be null or empty");
		DomainException.throwsWhen(date == null, "Contract date can't be null");
		DomainException.throwsWhen(quotas == null || quotas.isEmpty(), "Contract must have quotas");
		DomainException.throwsWhen(bank == null, "Contract must have a bank");
	}
}
