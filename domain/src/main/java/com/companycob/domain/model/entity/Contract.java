package com.companycob.domain.model.entity;

import java.time.LocalDate;
import java.util.List;

public class Contract {

	private Long id;
	private String contractNumber;
	private LocalDate date;
	private List<Quota> quotas;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getContractNumber() {
		return contractNumber;
	}
	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}	
	public List<Quota> getQuotas() {
		return quotas;
	}
	public void setQuotas(List<Quota> quotas) {
		this.quotas = quotas;
	}
	
	@Override
	public String toString() {
		return "Contract [id=" + id + ", contractNumber=" + contractNumber + ", date=" + date + "]";
	}
	@Override
	public int hashCode() {
		return super.hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		return true;
	}
}
