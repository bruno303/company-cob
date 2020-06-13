package com.companycob.domain.model.entity;

import java.time.LocalDate;

public class Quota {

	private long id;
	private int number;
	private double initialValue;
	private double updatedValue;
	private LocalDate dueDate;
	private Contract contract;
	
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

	public double getInitialValue() {
		return initialValue;
	}

	public void setInitialValue(double initialValue) {
		this.initialValue = initialValue;
	}

	public double getUpdatedValue() {
		return updatedValue;
	}

	public void setUpdatedValue(double updatedValue) {
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

	@Override
	public String toString() {
		return "Quota [id=" + id + ", number=" + number + ", initialValue=" + initialValue + ", updatedValue="
				+ updatedValue + ", dueDate=" + dueDate + ", contract=" + contract + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contract == null) ? 0 : contract.hashCode());
		result = prime * result + ((dueDate == null) ? 0 : dueDate.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		long temp;
		temp = Double.doubleToLongBits(initialValue);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + number;
		temp = Double.doubleToLongBits(updatedValue);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Quota other = (Quota) obj;
		if (contract == null) {
			if (other.contract != null)
				return false;
		} else if (!contract.equals(other.contract))
			return false;
		if (dueDate == null) {
			if (other.dueDate != null)
				return false;
		} else if (!dueDate.equals(other.dueDate))
			return false;
		if (id != other.id)
			return false;
		if (Double.doubleToLongBits(initialValue) != Double.doubleToLongBits(other.initialValue))
			return false;
		if (number != other.number)
			return false;
		if (Double.doubleToLongBits(updatedValue) != Double.doubleToLongBits(other.updatedValue))
			return false;
		return true;
	}
}
