package com.companycob.domain.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import com.companycob.domain.exception.DomainException;
import com.companycob.domain.utils.BigDecimalUtils;

public class Quota implements Entity, Comparable<Quota> {

	private Long id;
	private int number;
	private BigDecimal initialValue;
	private BigDecimal updatedValue;
	private LocalDate dueDate;
	private long arrearsDays;

	public Quota(Long id, int number, BigDecimal initialValue, BigDecimal updatedValue, LocalDate dueDate, long arrearsDays) {
		this.id = id;
		this.number = number;
		this.initialValue = initialValue;
		this.updatedValue = updatedValue;
		this.dueDate = dueDate;
		this.arrearsDays = arrearsDays;
		validate();
	}
	
	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public long getArrearsDays() {
		return arrearsDays;
	}

	public void setArrearsDays(long arrearsDays) {
		this.arrearsDays = arrearsDays;
	}

	@Override
	public String toString() {
		return "Quota [id=" + id + ", number=" + number + ", initialValue=" + initialValue + ", updatedValue="
				+ updatedValue + ", dueDate=" + dueDate + "]";
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
				dueDate.equals(quota.dueDate);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, number, initialValue, updatedValue, dueDate, arrearsDays);
	}

	@Override
	public int compareTo(Quota o) {
		return Long.compare(this.getArrearsDays(), o.getArrearsDays());
	}

	public void validate() {
		DomainException.throwsWhen(dueDate == null, "DueDate can't be empty");
		DomainException.throwsWhen(BigDecimalUtils.lesserThanZero(initialValue), "InitialValue can't be negative");
		DomainException.throwsWhen(number < 1, "Number must be greather than 0");
	}
}
