package com.companycob.tests.fixture.generator;

import com.companycob.domain.model.entity.Bank;
import com.companycob.domain.model.entity.Contract;
import com.companycob.tests.utils.RepositoryUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ContractGenerator {

    private final RepositoryUtils repositoryUtils;
    private final BankGenerator bankGenerator;
    private final QuotaGenerator quotaGenerator;

    @Autowired
    public ContractGenerator(RepositoryUtils repositoryUtils, BankGenerator bankGenerator, QuotaGenerator quotaGenerator) {
        this.repositoryUtils = repositoryUtils;
        this.bankGenerator = bankGenerator;
        this.quotaGenerator = quotaGenerator;
    }

    public Contract generate() {
        return generate(true, true);
    }

    public Contract generate(boolean generateQuotas, boolean generateBank) {
        Contract contract = new Contract();
        contract.setDate(LocalDate.now());
        contract.setContractNumber(RandomStringUtils.randomAlphanumeric(20));

        if (generateBank) {
            var bank = repositoryUtils.saveBank(bankGenerator.generate());
            contract.setBank(bank);
        }

        if (generateQuotas) {
            contract.setQuotas(quotaGenerator.generate(contract, 2));
        }

        return contract;
    }

    public Contract generate(Bank bank) {
        Contract contract = new Contract();
        contract.setDate(LocalDate.now());
        contract.setContractNumber(RandomStringUtils.randomAlphanumeric(20));
        contract.setBank(bank);
        contract.setQuotas(quotaGenerator.generate(contract, 2));

        return contract;
    }
}
