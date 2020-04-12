package com.companycob.domain.core.interfaces;

import com.companycob.domain.core.model.Quota;

public interface Bank {
	
	double calculateDebt(Quota quota);
	
	double calculateCommission(Quota quota);
	
}
