package com.companycob.domain.core.model;

import com.companycob.domain.core.model.abstracts.AbstractBank;

public class ItauBank extends AbstractBank {

	@Override
	public double calculateDebt(Quota quota) {
		
		if (!quota.isCalculated()) {
			var expirationDays = quota.calculateExpirationDays();
			var calculatedValue = expirationDays * this.getInterestDailyPercentage();
			quota.setCalculatedValue(calculatedValue);
		}
		
		return quota.getCalculatedValue();
	}
	
	@Override
	public double calculateCommission(Quota quota) {
		var debtValue = calculateDebt(quota);
		
		return debtValue * this.getCommissionPercentage();
	}

}
