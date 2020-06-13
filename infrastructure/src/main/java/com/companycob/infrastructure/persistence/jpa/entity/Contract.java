package com.companycob.infrastructure.persistence.jpa.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "CONTRACT")
public class Contract {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "CONTRACT_NUMBER", length = 60, nullable = false)
	private String contractNumber;
	
	@Column(name = "DATE")
	private LocalDate date;
	
	@OneToMany(mappedBy = "contract", cascade = CascadeType.ALL)
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
}
