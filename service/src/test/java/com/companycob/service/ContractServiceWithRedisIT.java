package com.companycob.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import com.companycob.tests.AbstractDatabaseIntegrationTest;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ContractServiceWithRedisIT extends AbstractDatabaseIntegrationTest {

    @Autowired
	private ContractService contractService;

    @Test
	public void testSaveNewContractAndLoadUsingCache() {

		final var contract = contractGenerator.generate();

		final var contractSaved = contractService.save(contract);
		final var id = contractSaved.getId();

		// cache will be loaded
		contractService.findById(id);

		// contract will be changed
		contractSaved.setDate(LocalDate.now().plusDays(1));

		contractService.save(contractSaved);

		final var contractFound = contractService.findById(id).orElse(null);

		assertThat(contractFound).isNotNull();
		assertThat(contractFound.getId()).isEqualTo(id);

		// assure the contract found have old date
		assertThat(contractFound.getDate()).isEqualTo(contract.getDate());
		assertThat(contractFound.getDate()).isNotEqualTo(contractSaved.getDate());
		assertThat(contractFound.getQuotas().size()).isEqualTo(2);
	}

	@Test
	public void testSaveNewContract_changeBankAndSaveAgain_bankShouldNotBeChanged() {

		final var contract = contractGenerator.generate();

		final var contract2 = contractService.save(contract);
		assertThat(contract2).isNotNull();
		assertThat(contract2.getId()).isNotNull();
		assertThat(contract2.getBank()).isNotNull();
		assertThat(contract2.getQuotas().size()).isEqualTo(2);

		final var bankName = contract2.getBank().getName();
		contract2.getBank().setName("Bank changed");

		final var contract3 = contractService.save(contract2);
		assertThat(contract3).isNotNull();
		assertThat(contract3.getId()).isNotNull();
		assertThat(contract3.getBank()).isNotNull();
		assertThat(contract3.getQuotas().size()).isEqualTo(2);
		assertThat(contract3.getBank().getName()).isEqualTo(bankName);
	}
    
}
