package com.companycob.service.calc;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.companycob.domain.model.entity.Bank;
import com.companycob.domain.model.entity.Contract;
import com.companycob.service.calc.defaults.SimpleInterestCalcService;
import com.companycob.testsbase.fixture.unit.Generator;

public class SimpleInterestCalcServiceTest {

    private SimpleInterestCalcService interestCalcService;
    private final Generator generator = new Generator();

    @Before
    public void init() {
        interestCalcService = new SimpleInterestCalcService();
    }

    @Test
    public void testCalculate() {
        final Bank bank = generator.generateBank(BigDecimal.valueOf(0.1));
        final Contract contract = generator.generateContract(bank);

        contract.getQuotas().get(0).setArrearsDays(181L);
        contract.getQuotas().get(1).setArrearsDays(151L);

        interestCalcService.calculate(contract);

        assertThat(contract.getQuotas().size()).isEqualTo(2);

        final var quota1 = contract.getQuotas().get(0);
        final var quota2 = contract.getQuotas().get(1);

        assertThat(quota1.getUpdatedValue()).isEqualByComparingTo(BigDecimal.valueOf(236.2));
        assertThat(quota2.getUpdatedValue()).isEqualByComparingTo(BigDecimal.valueOf(230.2));
    }
}
