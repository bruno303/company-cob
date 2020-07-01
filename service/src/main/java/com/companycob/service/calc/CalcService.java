package com.companycob.service.calc;

import com.companycob.domain.model.entity.Contract;
import com.companycob.domain.model.enumerators.CalcType;

public interface CalcService {
    CalcType getCalcType();
    boolean accept(Contract contract);
    void calculate(Contract contract);
}
