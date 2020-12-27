package com.companycob.service.calc.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.companycob.domain.model.entity.Quota;
import com.companycob.service.arrears.ArrearsDaysService;
import com.companycob.tests.AbstractUnitTest;

public class DefaultCalcServiceTest extends AbstractUnitTest {

	@MockBean
	private ArrearsDaysService arrearsDaysService;

	@Autowired
	@InjectMocks
	private DefaultCalcService defaultCalcService;

	@Override
	@Before
	public void setUp() {
		super.setUp();
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testCalculateContract() {

		Mockito.when(arrearsDaysService.calculateArrearsDaysInSingleQuota(Mockito.any(Quota.class))).thenReturn(181L,
				151L);

		final var bank = bankGenerator.generate(BigDecimal.valueOf(0.1));
		final var contract = contractGenerator.generate(bank);

		assertThat(defaultCalcService.accept(contract)).isTrue();

		defaultCalcService.calculate(contract);
		assertThat(contract.getQuotas().size()).isEqualTo(2);

		final var quota1 = contract.getQuotas().get(0);
		final var quota2 = contract.getQuotas().get(1);

		assertThat(quota1.getArrearsDays()).isEqualTo(181L);
		assertThat(quota2.getArrearsDays()).isEqualTo(151L);
		assertThat(quota1.getUpdatedValue()).isEqualByComparingTo(BigDecimal.valueOf(236.2));
		assertThat(quota2.getUpdatedValue()).isEqualByComparingTo(BigDecimal.valueOf(230.2));
	}

}
