package com.companycob.service;

import com.companycob.domain.exception.ValidationException;
import com.companycob.domain.model.entity.Contract;
import com.companycob.tests.AbstractDatabaseIntegrationTest;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class ContractServiceTest extends AbstractDatabaseIntegrationTest {

	@Autowired
	private ContractService contractService;

	@Test
	public void testSaveNewContract_withNoContractNumber() {

		final var contract = this.contractGenerator.generate();
		contract.setContractNumber(null);

		Contract contract2 = null;
		try {
			contract2 = contractService.save(contract);
			Assertions.fail("ValidationException expected");
		} catch (final ValidationException ignored) {
		}

		assertThat(contract2).isNull();
	}

	@Test
	public void testSaveNewContract_withEmptyContractNumber() {

		final var contract = this.contractGenerator.generate();
		contract.setContractNumber("");

		Contract contract2 = null;

		try {
			contract2 = contractService.save(contract);
			Assertions.fail("ValidationException expected");
		} catch (final ValidationException ignored) {
		}

		assertThat(contract2).isNull();
	}

	@Test
	public void testSaveNewContract_withNoContractDate() {

		final var contract = contractGenerator.generate();
		contract.setDate(null);

		Contract contract2 = null;

		try {
			contract2 = contractService.save(contract);
			Assertions.fail("ValidationException expected");
		} catch (final ValidationException ignored) {
		}

		assertThat(contract2).isNull();
	}

	@Test
	public void testSaveNewContract_withSucess() {

		final var contract = contractGenerator.generate();

		final var contract2 = contractService.save(contract);
		assertThat(contract2).isNotNull();
		assertThat(contract2.getId()).isNotNull();
		assertThat(contract2.getBank()).isNotNull();
		assertThat(contract2.getQuotas().size()).isEqualTo(2);
	}

	@Test
	public void testSaveNewContractAndLoadById() {
		final var contract = contractGenerator.generate();

		final var contractSaved = contractService.save(contract);

		final var contractLoadedOptional = contractService.findById(contractSaved.getId());
		assertThat(contractLoadedOptional).isPresent();

		final var contractLoaded = contractLoadedOptional.get();

		assertThat(contractLoaded).isNotNull();
		assertThat(contractLoaded.getId()).isEqualTo(contractSaved.getId());
		assertThat(contractLoaded.getDate()).isEqualTo(contractSaved.getDate());
		assertThat(contractLoaded.getContractNumber()).isEqualTo(contractSaved.getContractNumber());
		assertThat(contractLoaded.getQuotas().size()).isEqualTo(2);
	}

	@Test
	public void testSaveNewContractAndLoadByContractNumber() {
		final var contract = contractGenerator.generate();

		final var contractSaved = contractService.save(contract);

		final var contractsLoaded = contractService.findByContractNumber(contractSaved.getContractNumber());
		assertThat(contractsLoaded.size()).isEqualTo(1);

		final var contractLoaded = contractsLoaded.get(0);

		assertThat(contractLoaded).isNotNull();
		assertThat(contractLoaded.getId()).isEqualTo(contractSaved.getId());
		assertThat(contractLoaded.getDate()).isEqualTo(contractSaved.getDate());
		assertThat(contractLoaded.getContractNumber()).isEqualTo(contractSaved.getContractNumber());
		assertThat(contractLoaded.getQuotas().size()).isEqualTo(2);
	}

	@Test
	public void testSaveNewContractMultipleTimes_withSucess() {

		final var contract = contractGenerator.generate();

		final var contractSaved = contractService.save(contract);
		final var id = contractSaved.getId();

		// Assure contract is persisted first
		assertThat(contractService.findById(id)).isPresent();

		contractSaved.setDate(LocalDate.now().plusDays(1));

		final var save1Async = runAsync(() -> contractService.save(contractSaved));
		final var save2Async = runAsync(() -> contractService.save(contractSaved));
		final var save3Async = runAsync(() -> contractService.save(contractSaved));
		final var save4Async = runAsync(() -> contractService.save(contractSaved));
		final var save5Async = runAsync(() -> contractService.save(contractSaved));

		awaitAllCompletableFutures(save1Async, save2Async, save3Async, save4Async, save5Async);

		final var contractFound = contractService.findById(id).orElse(null);

		assertThat(contractFound).isNotNull();
		assertThat(contractFound.getId()).isEqualTo(id);

		assertThat(contractFound.getDate()).isEqualTo(contractSaved.getDate());
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

	@Test
	public void testSaveNewContractWithoutBank_withError() {

		final var contract = contractGenerator.generate(true, false);

		Contract contractSaved = null;

		try {
			contractSaved = contractService.save(contract);
			Assertions.fail("ValidationException expected");
		} catch (final ValidationException ignored) {
		}

		assertThat(contractSaved).isNull();
	}
}