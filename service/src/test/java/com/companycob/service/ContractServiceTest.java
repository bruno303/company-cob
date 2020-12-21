package com.companycob.service;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.companycob.domain.exception.ValidationException;
import com.companycob.domain.model.entity.Contract;
import com.companycob.tests.AbstractDatabaseIntegrationTest;

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
			Assert.fail("ValidationException expected");
		} catch (final ValidationException ignored) {
		}

		Assert.assertNull(contract2);
	}

	@Test
	public void testSaveNewContract_withEmptyContractNumber() {

		final var contract = this.contractGenerator.generate();
		contract.setContractNumber("");

		Contract contract2 = null;

		try {
			contract2 = contractService.save(contract);
			Assert.fail("ValidationException expected");
		} catch (final ValidationException ignored) {
		}

		Assert.assertNull(contract2);
	}

	@Test
	public void testSaveNewContract_withNoContractDate() {

		final var contract = contractGenerator.generate();
		contract.setDate(null);

		Contract contract2 = null;

		try {
			contract2 = contractService.save(contract);
			Assert.fail("ValidationException expected");
		} catch (final ValidationException ignored) {
		}

		Assert.assertNull(contract2);
	}

	@Test
	public void testSaveNewContract_withSucess() {

		final var contract = contractGenerator.generate();

		final var contract2 = contractService.save(contract);
		Assert.assertNotNull(contract2);
		Assert.assertNotNull(contract2.getId());
		Assert.assertNotNull(contract2.getBank());
		Assert.assertEquals(2, contract2.getQuotas().size());
	}

	@Test
	public void testSaveNewContractAndLoadById() {
		final var contract = contractGenerator.generate();

		final var contractSaved = contractService.save(contract);

		final var contractLoadedOptional = contractService.findById(contractSaved.getId());
		Assert.assertTrue(contractLoadedOptional.isPresent());

		final var contractLoaded = contractLoadedOptional.get();

		Assert.assertNotNull(contractLoaded);
		Assert.assertEquals(contractSaved.getId(), contractLoaded.getId());
		Assert.assertEquals(contractSaved.getDate(), contractLoaded.getDate());
		Assert.assertEquals(contractSaved.getContractNumber(), contractLoaded.getContractNumber());
		Assert.assertEquals(2, contractLoaded.getQuotas().size());
	}

	@Test
	public void testSaveNewContractAndLoadByContractNumber() {
		final var contract = contractGenerator.generate();

		final var contractSaved = contractService.save(contract);

		final var contractsLoaded = contractService.findByContractNumber(contractSaved.getContractNumber());
		Assert.assertEquals(1, contractsLoaded.size());

		final var contractLoaded = contractsLoaded.get(0);

		Assert.assertNotNull(contractLoaded);
		Assert.assertEquals(contractSaved.getId(), contractLoaded.getId());
		Assert.assertEquals(contractSaved.getDate(), contractLoaded.getDate());
		Assert.assertEquals(contractSaved.getContractNumber(), contractLoaded.getContractNumber());
		Assert.assertEquals(2, contractLoaded.getQuotas().size());
	}

	@Test
	public void testSaveNewContractMultipleTimes_withSucess() {

		final var contract = contractGenerator.generate();

		final var contractSaved = contractService.save(contract);
		final var id = contractSaved.getId();

		// Assure contract is persisted first
		Assert.assertTrue(contractService.findById(id).isPresent());

		contractSaved.setDate(LocalDate.now().plusDays(1));

		final var save1Async = runAsync(() -> contractService.save(contractSaved));
		final var save2Async = runAsync(() -> contractService.save(contractSaved));
		final var save3Async = runAsync(() -> contractService.save(contractSaved));
		final var save4Async = runAsync(() -> contractService.save(contractSaved));
		final var save5Async = runAsync(() -> contractService.save(contractSaved));

		awaitAllCompletableFutures(save1Async, save2Async, save3Async, save4Async, save5Async);

		final var contractFound = contractService.findById(id).orElse(null);

		Assert.assertNotNull(contractFound);
		Assert.assertEquals(id, contractFound.getId());

		Assert.assertEquals(contractSaved.getDate(), contractFound.getDate());
		Assert.assertEquals(2, contractFound.getQuotas().size());
	}

	@Test
	public void testSaveNewContract_changeBankAndSaveAgain_bankShouldNotBeChanged() {

		final var contract = contractGenerator.generate();

		final var contract2 = contractService.save(contract);
		Assert.assertNotNull(contract2);
		Assert.assertNotNull(contract2.getId());
		Assert.assertNotNull(contract2.getBank());
		Assert.assertEquals(2, contract2.getQuotas().size());

		final var bankName = contract2.getBank().getName();
		contract2.getBank().setName("Bank changed");

		final var contract3 = contractService.save(contract2);
		Assert.assertNotNull(contract3);
		Assert.assertNotNull(contract3.getId());
		Assert.assertNotNull(contract3.getBank());
		Assert.assertEquals(2, contract3.getQuotas().size());
		Assert.assertEquals(bankName, contract3.getBank().getName());
	}

	@Test
	public void testSaveNewContractWithoutBank_withError() {

		final var contract = contractGenerator.generate(true, false);

		Contract contractSaved = null;

		try {
			contractSaved = contractService.save(contract);
			Assert.fail("ValidationException expected");
		} catch (final ValidationException ignored) {
		}

		Assert.assertNull(contractSaved);
	}
}