package com.companycob.service.calc.impl;

import com.companycob.domain.model.entity.Contract;
import com.companycob.domain.model.entity.Quota;
import com.companycob.domain.model.enumerators.CalcType;
import com.companycob.service.calc.CalcService;
import com.companycob.service.calc.defaults.SimpleInterestCalcService;
import com.companycob.utils.date.LocalDateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultCalcService implements CalcService {

    private final SimpleInterestCalcService simpleInterestCalcService;

    @Autowired
    public DefaultCalcService(SimpleInterestCalcService simpleInterestCalcService) {
        this.simpleInterestCalcService = simpleInterestCalcService;
    }

    @Override
    public CalcType getCalcType() {
        return CalcType.DEFAULT;
    }

    @Override
    public boolean accept(Contract contract) {
        CalcType calcType = contract.getBank().getCalcType();
        return this.getCalcType().equals(calcType);
    }

    @Override
    public void calculate(Contract contract) {
        updateArrearsDaysInAllQuotas(contract);
        simpleInterestCalcService.calculate(contract);
    }

    private void updateArrearsDaysInAllQuotas(Contract contract) {
        contract.getQuotas()
                .forEach(quota -> quota.setArrearsDays(LocalDateUtils.differenceInDays(quota.getDueDate(), LocalDate.now())));
    }
}
