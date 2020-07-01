package com.companycob.tests.generator;

import com.companycob.domain.model.entity.Contract;
import com.companycob.domain.model.entity.Quota;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class QuotaGenerator {

    public List<Quota> generate(Contract contract, int quotaCount) {
        List<Quota> quotas = new ArrayList<>();

        LocalDate date = LocalDate.now().minusDays(181);

        for (int i = 0; i < quotaCount; i++) {
            Quota quota = new Quota();
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
