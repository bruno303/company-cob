package com.companycob.infrastructure.persistence.jpa.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "QUOTA")
public class Quota {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private long id;
	
	@Column(name = "NUMBER", nullable = false)
	private int number;
	
	@Column(name = "INITIAL_VALUE", nullable = false)
	private double initialValue;
	
	@Transient
	private double updatedValue;
	
	@Column(name = "DUE_DATE", nullable = false)
	private LocalDate dueDate;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "ID_CONTRACT", nullable = false)
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
}
