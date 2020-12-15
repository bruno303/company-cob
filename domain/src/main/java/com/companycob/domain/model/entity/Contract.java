package com.companycob.domain.model.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.companycob.domain.lock.Lockable;

public class Contract implements Lockable {

	private Long id;
	private String contractNumber;
	private LocalDate date;
	private List<Quota> quotas;
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
		if (quotas == null) {
			quotas = new ArrayList<>();
		}

		return quotas;
	}

	public void setQuotas(final List<Quota> quotas) {
		this.quotas = quotas;
		this.quotas.forEach(q -> q.setContract(this));
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
}
