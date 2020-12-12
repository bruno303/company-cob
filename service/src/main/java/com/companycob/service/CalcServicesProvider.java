package com.companycob.service;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.companycob.domain.model.entity.Contract;
import com.companycob.domain.model.enumerators.CalcType;
import com.companycob.service.calc.CalcService;

@Component
public class CalcServicesProvider {

	private final Map<CalcType, CalcService> calcServices;

	public CalcServicesProvider(Set<CalcService> calcServices) {
		this.calcServices = new EnumMap<>(CalcType.class);
		setCalcServices(calcServices);
	}

	private void setCalcServices(Set<CalcService> calcServices) {
		calcServices.forEach(this::putIfAbsent);
	}

	private void putIfAbsent(CalcService calcService) {
		this.calcServices.putIfAbsent(calcService.getCalcType(), calcService);
	}

	public CalcService getCalcService(Contract contract) {
		return this.getCalcService(contract.getBank().getCalcType());
	}

	public CalcService getCalcService(CalcType calcType) {
		if (!this.calcServices.containsKey(calcType)) {
			throw new IllegalStateException("There is no CalcService for type " + calcType.getDescription());
		}
		return this.calcServices.get(calcType);
	}
}
