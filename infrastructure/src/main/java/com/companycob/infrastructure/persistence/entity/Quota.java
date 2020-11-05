package com.companycob.infrastructure.persistence.entity;

import java.math.BigDecimal;
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
@Table(name = "quota", schema = "public")
public class Quota {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private long id;

	@Column(name = "NUMBER", nullable = false)
	private int number;

	@Column(name = "INITIAL_VALUE", nullable = false)
	private BigDecimal initialValue;

	@Transient
	private BigDecimal updatedValue;

	@Column(name = "DUE_DATE", nullable = false)
	private LocalDate dueDate;

	@ManyToOne(optional = false)
	@JoinColumn(name = "ID_CONTRACT", nullable = false)
	private Contract contract;

	public long getId() {
		return id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(final int number) {
		this.number = number;
	}

	public BigDecimal getInitialValue() {
		return initialValue;
	}

	public void setInitialValue(final BigDecimal initialValue) {
		this.initialValue = initialValue;
	}

	public BigDecimal getUpdatedValue() {
		return updatedValue;
	}

	public void setUpdatedValue(final BigDecimal updatedValue) {
		this.updatedValue = updatedValue;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(final LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public Contract getContract() {
		return contract;
	}

	public void setContract(final Contract contract) {
		this.contract = contract;
	}
}
