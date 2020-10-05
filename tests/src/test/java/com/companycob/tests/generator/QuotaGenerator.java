package com.companycob.tests.generator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.companycob.domain.model.entity.Contract;
import com.companycob.domain.model.entity.Quota;

@Component
public class QuotaGenerator {

	public List<Quota> generate(final Contract contract, final int quotaCount) {
		final List<Quota> quotas = new ArrayList<>();

		LocalDate date = LocalDate.now().minusDays(181);

		for (int i = 0; i < quotaCount; i++) {
			final Quota quota = new Quota();
			quota.setContract(contract);
			quota.setDueDate(date);
			quota.setInitialValue(BigDecimal.valueOf(200));
			quota.setNumber(i + 1);

			quotas.add(quota);

			date = date.plusMonths(1);
		}

		return quotas;
	}

}
