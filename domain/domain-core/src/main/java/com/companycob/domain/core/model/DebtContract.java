package com.companycob.domain.core.model;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

import com.companycob.domain.core.interfaces.Bank;

@Component
public class DebtContract {
	
	private long id;
	
	private String identificationCode;
	
	private LocalDate date;
	
	private List<Quota> quotas;
	
	private long bankId;

	public String getIdentificationCode() {
		return identificationCode;
	}
	
	public long getId() {
		return id;
	}
	
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	public LocalDate getDate() {
		return date;
	}

	public void setIdentificationCode(String identificationCode) {
		this.identificationCode = identificationCode;
	}

	public List<Quota> getQuotas() {
		return quotas;
	}

	public void setQuotas(List<Quota> quotas) {
		this.quotas = quotas;
	}
	
	public double calculateUpdatedDebts(Bank bank) {
		double finalValue = 0;
		
		for (Quota quota : quotas) {
			finalValue += quota.calculateUpdatedValue(bank);
		}
		
		return finalValue;
	}
}
