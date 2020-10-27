package com.companycob.service;

import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.companycob.domain.exception.ValidationException;
import com.companycob.domain.model.entity.Contract;
import com.companycob.tests.AbstractDatabaseIntegrationTest;
import com.companycob.utils.thread.ThreadUtils;

public class ContractServiceTest extends AbstractDatabaseIntegrationTest {

	@Autowired
	private ContractService contractService;

	@Test(expected = ValidationException.class)
	public void testSaveNewContract_withNoContractNumber() throws ValidationException {

		final var contract = new Contract();
		contract.setDate(LocalDate.now());

		final var contract2 = contractService.save(contract);
		Assert.assertNull(contract2);
	}

	@Test(expected = ValidationException.class)
	public void testSaveNewContract_withEmptyContractNumber() throws ValidationException {

		final var contract = new Contract();
		contract.setDate(LocalDate.now());
		contract.setContractNumber("");

		final var contract2 = contractService.save(contract);
		Assert.assertNull(contract2);
	}

	@Test(expected = ValidationException.class)
	public void testSaveNewContract_withNoContractDate() throws ValidationException {

		final var contract = new Contract();
		contract.setContractNumber("abc");

		final var contract2 = contractService.save(contract);
		Assert.assertNull(contract2);
	}

	@Test
	public void testSaveNewContract_withSucess() throws ValidationException {

		final var contract = contractGenerator.generate();

		final var contract2 = contractService.save(contract);
		Assert.assertNotNull(contract2);
		Assert.assertNotNull(contract2.getId());
		Assert.assertNotNull(contract2.getBank());
		Assert.assertEquals(2, contract2.getQuotas().size());
	}

	@Test
	public void testSaveNewContractAndLoadById() throws ValidationException {
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
	public void testSaveNewContractAndLoadByContractNumber() throws ValidationException {
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
	public void testSaveNewContractTwice_withSucess() throws ValidationException {

		final var contract = contractGenerator.generate();

		final var contractSaved = contractService.save(contract);
		final var id = contractSaved.getId();

		final var contract2Optional = contractService.findById(id);
		Assert.assertTrue(contract2Optional.isPresent());

		final var contract2 = contract2Optional.get();
		contractSaved.setDate(LocalDate.now().plusDays(1));
		contract2.setDate(LocalDate.now().plusDays(2));

		final var save1Async = executeAsync(() -> {
			try {
				contractService.save(contractSaved);
			} catch (final ValidationException e) {
				Assert.fail(e.getMessage());
			}
		});

		ThreadUtils.threadSleep(100L);

		final var save2Async = executeAsync(() -> {
			try {
				contractService.save(contract2);
			} catch (final ValidationException e) {
				Assert.fail(e.getMessage());
			}
		});

		CompletableFuture.allOf(save1Async, save2Async).join();

		final var contractFound = contractService.findById(id).get();

		Assert.assertNotNull(contractFound);
		Assert.assertEquals(id, contractFound.getId());
		Assert.assertEquals(LocalDate.now().plusDays(2), contractFound.getDate());
		Assert.assertEquals(2, contractFound.getQuotas().size());
	}

	@Test
	public void testSaveNewContract_changeBankAndSaveAgain_bankShouldNotBeChanged() throws ValidationException {

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

	@Test(expected = ValidationException.class)
	public void testSaveNewContractWithoutBank_withError() throws ValidationException {

		final var contract = contractGenerator.generate(true, false);

		final var contractSaved = contractService.save(contract);
		Assert.assertNotNull(contractSaved);
	}

	private CompletableFuture<Void> executeAsync(final Runnable runnable) {
		return CompletableFuture.runAsync(runnable);
	}
}