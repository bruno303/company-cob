package com.companycob.service.calc.impl;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.companycob.domain.model.entity.Contract;
import com.companycob.domain.model.enumerators.CalcType;
import com.companycob.service.calc.CalcService;
import com.companycob.service.calc.defaults.SimpleInterestCalcService;
import com.companycob.utils.date.LocalDateUtils;

@Service
public class DefaultCalcService implements CalcService {

	private final SimpleInterestCalcService simpleInterestCalcService;

	@Autowired
	public DefaultCalcService(final SimpleInterestCalcService simpleInterestCalcService) {
		this.simpleInterestCalcService = simpleInterestCalcService;
	}

	@Override
	public CalcType getCalcType() {
		return CalcType.DEFAULT;
	}

	@Override
	public boolean accept(final Contract contract) {
		final CalcType calcType = contract.getBank().getCalcType();
		return this.getCalcType().equals(calcType);
	}

	@Override
	public void calculate(final Contract contract) {
		updateArrearsDaysInAllQuotas(contract);
		simpleInterestCalcService.calculate(contract);
	}

	private void updateArrearsDaysInAllQuotas(final Contract contract) {
		contract.getQuotas().forEach(
				quota -> quota.setArrearsDays(LocalDateUtils.differenceInDays(quota.getDueDate(), LocalDate.now())));
	}
}
