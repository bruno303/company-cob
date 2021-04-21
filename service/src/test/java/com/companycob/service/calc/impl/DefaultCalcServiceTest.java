package com.companycob.service.calc.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.companycob.domain.model.entity.Contract;
import com.companycob.domain.model.entity.Quota;
import com.companycob.service.arrears.ArrearsDaysService;
import com.companycob.service.calc.defaults.SimpleInterestCalcService;
import com.companycob.testsbase.fixture.unit.Generator;

public class DefaultCalcServiceTest {

	// Mocks
	private final ArrearsDaysService arrearsDaysService = Mockito.mock(ArrearsDaysService.class);
	private final SimpleInterestCalcService simpleInterestCalcService = Mockito.mock(SimpleInterestCalcService.class);

	private final Generator generator = new Generator();

	private DefaultCalcService defaultCalcService;

	@Before
	public void setUp() {
		defaultCalcService = new DefaultCalcService(simpleInterestCalcService, arrearsDaysService);
	}

	@Test
	public void testCalculateContract() {

		Mockito.when(arrearsDaysService.calculateArrearsDaysInSingleQuota(Mockito.any(Quota.class))).thenReturn(181L,
				151L);

		final var bank = generator.generateBank(BigDecimal.valueOf(0.1));
		final var contract = generator.generateContract(bank);

		assertThat(defaultCalcService.accept(contract)).isTrue();

		defaultCalcService.calculate(contract);
		assertThat(contract.getQuotas().size()).isEqualTo(2);

		final var quota1 = contract.getQuotas().get(0);
		final var quota2 = contract.getQuotas().get(1);

		assertThat(quota1.getArrearsDays()).isEqualTo(181L);
		assertThat(quota2.getArrearsDays()).isEqualTo(151L);

		Mockito.verify(simpleInterestCalcService, Mockito.times(1)).calculate(Mockito.any(Contract.class));
	}

}
