package com.companycob.service.calc.impl;

import com.companycob.domain.model.entity.Contract;
import com.companycob.domain.model.entity.Quota;
import com.companycob.service.calc.CalcService;
import com.companycob.utils.date.LocalDateUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultCalcService implements CalcService {

    public void calculate(Contract contract) {
        //
    }

    private void updateArrearsDaysInAllQuotas(Contract contract) {
        contract.getQuotas()
                .forEach(quota -> quota.setArrearsDays(LocalDateUtils.differenceInDays(quota.getDueDate(), LocalDate.now())));
    }

    private long calculateArrearsDays(Contract contract) {
        updateArrearsDaysInAllQuotas(contract);

        List<Long> arrears = contract.getQuotas()
                .stream()
                .peek(quota -> quota.setArrearsDays(LocalDateUtils.differenceInDays(quota.getDueDate(), LocalDate.now())))
                .map(Quota::getArrearsDays)
                .collect(Collectors.toList());

        return Collections.max(arrears);
    }

}
