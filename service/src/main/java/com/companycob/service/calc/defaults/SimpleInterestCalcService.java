package com.companycob.service.calc.defaults;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.companycob.domain.model.entity.Contract;
import com.companycob.domain.model.entity.Quota;

@Service
public class SimpleInterestCalcService {

	public void calculate(final Contract contract) {
		final var bankValues = contract.getBank().getBankCalculationValues();
		contract.getQuotas().forEach(quota -> calculateQuota(quota, bankValues.getInterestRate()));
	}

	private void calculateQuota(final Quota quota, final BigDecimal interestRate) {
		final BigDecimal initialValue = quota.getInitialValue();
		final BigDecimal percent = BigDecimal.valueOf(100);
		final BigDecimal arrearsDaysBigDecimal = BigDecimal.valueOf(quota.getArrearsDays());
		final BigDecimal one = BigDecimal.ONE;

		final var value = one.add(interestRate.divide(percent).multiply(arrearsDaysBigDecimal));
		final BigDecimal updatedValue = initialValue.multiply(value);

		quota.setUpdatedValue(updatedValue);
	}

}
