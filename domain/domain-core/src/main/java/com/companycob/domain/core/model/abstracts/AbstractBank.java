package com.companycob.domain.core.model.abstracts;

import com.companycob.domain.core.interfaces.Bank;
import com.companycob.domain.core.model.Quota;

public abstract class AbstractBank implements Bank {
	
	private double interestDailyPercentage;
	
	private double commissionPercentage;
		
	public double getInterestDailyPercentage() {
		return interestDailyPercentage;
	}

	public void setInterestDailyPercentage(double interestDailyPercentage) {
		this.interestDailyPercentage = interestDailyPercentage;
	}

	public double getCommissionPercentage() {
		return commissionPercentage;
	}

	public void setCommissionPercentage(double commissionPercentage) {
		this.commissionPercentage = commissionPercentage;
	}

	@Override
	public abstract double calculateDebt(Quota quota);
	
	@Override
	public abstract double calculateCommission(Quota quota);
}
