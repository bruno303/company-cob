package com.companycob.service.calc.impl;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.companycob.domain.model.entity.Quota;
import com.companycob.service.arrears.ArrearsDaysService;
import com.companycob.tests.AbstractDatabaseIntegrationTest;

public class DefaultCalcServiceTest extends AbstractDatabaseIntegrationTest {

	@MockBean
	private ArrearsDaysService arrearsDaysService;

	@Autowired
	@InjectMocks
	private DefaultCalcService defaultCalcService;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testCalculateContract() {

		Mockito.when(arrearsDaysService.calculateArrearsDaysInSingleQuota(Mockito.any(Quota.class))).thenReturn(181L, 151L);

		final var bank = bankGenerator.generate(BigDecimal.valueOf(0.1));
		final var contract = contractGenerator.generate(bank);

		Assert.assertTrue(defaultCalcService.accept(contract));

		defaultCalcService.calculate(contract);
		Assert.assertEquals(2, contract.getQuotas().size());

		final var quota1 = contract.getQuotas().get(0);
		final var quota2 = contract.getQuotas().get(1);

		Assert.assertEquals(181L, quota1.getArrearsDays());
		Assert.assertEquals(151L, quota2.getArrearsDays());
		assertEqualsBigDecimal(BigDecimal.valueOf(236.2), quota1.getUpdatedValue());
		assertEqualsBigDecimal(BigDecimal.valueOf(230.2), quota2.getUpdatedValue());
	}

}
