package com.companycob.service.calc.defaults;

import com.companycob.domain.model.entity.Contract;
import com.companycob.domain.model.entity.Quota;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class SimpleInterestCalcService {

    public void calculate(Contract contract) {
        var bankValues = contract.getBank().getBankCalculationValues();
        contract.getQuotas().forEach(quota -> calculateQuota(quota, bankValues.getInterestRate()));
    }

    private void calculateQuota(Quota quota, BigDecimal interestRate) {
        final BigDecimal initialValue = quota.getInitialValue();
        final BigDecimal percent = BigDecimal.valueOf(100);
        final BigDecimal arrearsDaysBigDecimal = BigDecimal.valueOf(quota.getArrearsDays());
        final BigDecimal one = BigDecimal.ONE;

        final var value = one.add(interestRate
                .divide(percent)
                .multiply(arrearsDaysBigDecimal));
        final BigDecimal updatedValue = initialValue.multiply(value);

        quota.setUpdatedValue(updatedValue);
    }

}
