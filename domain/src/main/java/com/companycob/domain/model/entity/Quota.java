package com.companycob.domain.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Quota implements Comparable<Quota> {

	private long id;
	private int number;
	private BigDecimal initialValue;
	private BigDecimal updatedValue;
	private LocalDate dueDate;
	private Contract contract;
	private long arrearsDays;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public BigDecimal getInitialValue() {
		return initialValue;
	}

	public void setInitialValue(BigDecimal initialValue) {
		this.initialValue = initialValue;
	}

	public BigDecimal getUpdatedValue() {
		return updatedValue;
	}

	public void setUpdatedValue(BigDecimal updatedValue) {
		this.updatedValue = updatedValue;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

	public long getArrearsDays() {
		return arrearsDays;
	}

	public void setArrearsDays(long arrearsDays) {
		this.arrearsDays = arrearsDays;
	}

	@Override
	public String toString() {
		return "Quota [id=" + id + ", number=" + number + ", initialValue=" + initialValue + ", updatedValue="
				+ updatedValue + ", dueDate=" + dueDate + ", contract=" + contract + "]";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Quota quota = (Quota) o;
		return id == quota.id &&
				number == quota.number &&
				arrearsDays == quota.arrearsDays &&
				initialValue.equals(quota.initialValue) &&
				Objects.equals(updatedValue, quota.updatedValue) &&
				dueDate.equals(quota.dueDate) &&
				contract.equals(quota.contract);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, number, initialValue, updatedValue, dueDate, contract, arrearsDays);
	}

	@Override
	public int compareTo(Quota o) {
		return Long.compare(this.getArrearsDays(), o.getArrearsDays());
	}
}
