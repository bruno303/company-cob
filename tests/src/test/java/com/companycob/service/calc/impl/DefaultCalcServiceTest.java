package com.companycob.service.calc.impl;

import com.companycob.domain.model.entity.Bank;
import com.companycob.domain.model.entity.Contract;
import com.companycob.domain.model.entity.Quota;
import com.companycob.tests.AbstractServiceTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

public class DefaultCalcServiceTest extends AbstractServiceTest {

    @Autowired
    private DefaultCalcService defaultCalcService;

    @Test
    public void testCalculateContract() {
        Bank bank = bankGenerator.generate(BigDecimal.valueOf(0.1));
        Contract contract = contractGenerator.generate(bank);

        Assert.assertTrue(defaultCalcService.accept(contract));

        defaultCalcService.calculate(contract);
        Assert.assertEquals(2, contract.getQuotas().size());

        final Quota quota1 = contract.getQuotas().get(0);
        final Quota quota2 = contract.getQuotas().get(1);

        Assert.assertTrue(quota1.getArrearsDays() > 0);
        Assert.assertTrue(quota2.getArrearsDays() > 0);
        assertEqualsBigDecimal(BigDecimal.valueOf(236.2), quota1.getUpdatedValue());
        assertEqualsBigDecimal(BigDecimal.valueOf(230.2), quota2.getUpdatedValue());
    }

}
