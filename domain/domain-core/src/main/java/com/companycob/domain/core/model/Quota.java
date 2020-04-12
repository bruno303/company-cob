package com.companycob.domain.core.model;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.stereotype.Component;

import com.companycob.domain.core.interfaces.Bank;

@Component
public class Quota {
	
	private long id;
	
	private double value;
	
	private double calculatedValue;
	
	private LocalDate expiration;
	
	private LocalDate createdAt;
	
	private LocalDate updatedAt;
	
	private long contractId;

	public long getId() {
		return id;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public double getCalculatedValue() {
		return calculatedValue;
	}

	public void setCalculatedValue(double calculatedValue) {
		this.calculatedValue = calculatedValue;
	}

	public LocalDate getExpiration() {
		return expiration;
	}

	public void setExpiration(LocalDate expiration) {
		this.expiration = expiration;
	}

	public LocalDate getCreatedAt() {
		return createdAt;
	}

	public LocalDate getUpdatedAt() {
		return updatedAt;
	}

	public long getContractId() {
		return contractId;
	}

	public void setContractId(long contractId) {
		this.contractId = contractId;
	}
	
	public double calculateUpdatedValue(Bank bank) {		
		return bank.calculateDebt(this);
	}
	
	public int calculateExpirationDays() {
		var days = Period.between(LocalDate.now(), this.getExpiration()).getDays();
		return days > 0 ? days : 0;
	}
	
	public boolean isCalculated() {
		return getCalculatedValue() > 0;
	}

}
