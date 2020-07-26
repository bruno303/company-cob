package com.companycob.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.companycob.domain.exception.ValidationException;
import com.companycob.domain.model.entity.Contract;
import com.companycob.tests.AbstractServiceTest;
import com.companycob.utils.thread.ThreadUtils;

public class ContractServiceTest extends AbstractServiceTest {

	@Autowired
	private ContractService contractService;

	@Test(expected = ValidationException.class)
	public void testSaveNewContract_withNoContractNumber() throws ValidationException {

		final Contract contract = new Contract();
		contract.setDate(LocalDate.now());

		final Contract contract2 = contractService.save(contract);
		Assert.assertNull(contract2);
	}

	@Test(expected = ValidationException.class)
	public void testSaveNewContract_withEmptyContractNumber() throws ValidationException {

		final Contract contract = new Contract();
		contract.setDate(LocalDate.now());
		contract.setContractNumber("");

		final Contract contract2 = contractService.save(contract);
		Assert.assertNull(contract2);
	}

	@Test(expected = ValidationException.class)
	public void testSaveNewContract_withNoContractDate() throws ValidationException {

		final Contract contract = new Contract();
		contract.setContractNumber("abc");

		final Contract contract2 = contractService.save(contract);
		Assert.assertNull(contract2);
	}

	@Test
	public void testSaveNewContract_withSucess() throws ValidationException {

		final Contract contract = contractGenerator.generate();

		final Contract contract2 = contractService.save(contract);
		Assert.assertNotNull(contract2);
		Assert.assertNotNull(contract2.getId());
		Assert.assertNotNull(contract2.getBank());
		Assert.assertEquals(2, contract2.getQuotas().size());
	}

	@Test
	public void testSaveNewContractAndLoadById() throws ValidationException {
		final Contract contract = contractGenerator.generate();

		final Contract contractSaved = contractService.save(contract);

		final Optional<Contract> contractLoadedOptional = contractService.findById(contractSaved.getId());
		Assert.assertTrue(contractLoadedOptional.isPresent());

		final Contract contractLoaded = contractLoadedOptional.get();

		Assert.assertNotNull(contractLoaded);
		Assert.assertEquals(contractSaved.getId(), contractLoaded.getId());
		Assert.assertEquals(contractSaved.getDate(), contractLoaded.getDate());
		Assert.assertEquals(contractSaved.getContractNumber(), contractLoaded.getContractNumber());
		Assert.assertEquals(2, contractLoaded.getQuotas().size());
	}

	@Test
	public void testSaveNewContractAndLoadByContractNumber() throws ValidationException {
		final Contract contract = contractGenerator.generate();

		final Contract contractSaved = contractService.save(contract);

		final List<Contract> contractsLoaded = contractService.findByContractNumber(contractSaved.getContractNumber());
		Assert.assertEquals(1, contractsLoaded.size());

		final Contract contractLoaded = contractsLoaded.get(0);

		Assert.assertNotNull(contractLoaded);
		Assert.assertEquals(contractSaved.getId(), contractLoaded.getId());
		Assert.assertEquals(contractSaved.getDate(), contractLoaded.getDate());
		Assert.assertEquals(contractSaved.getContractNumber(), contractLoaded.getContractNumber());
		Assert.assertEquals(2, contractLoaded.getQuotas().size());
	}

	@Test
	public void testSaveNewContractTwice_withSucess() throws ValidationException {

		final Contract contract = contractGenerator.generate();

		final Contract contractSaved = contractService.save(contract);
		final Long id = contractSaved.getId();

		final Optional<Contract> contract2Optional = contractService.findById(id);
		Assert.assertTrue(contract2Optional.isPresent());

		final Contract contract2 = contract2Optional.get();
		contractSaved.setDate(LocalDate.now().plusDays(1));
		contract2.setDate(LocalDate.now().plusDays(2));

		final CompletableFuture<Void> save1Async = executeAsync(() -> {
			try {
				contractService.save(contractSaved);
			} catch (final ValidationException e) {
				Assert.fail(e.getMessage());
			}
		});

		ThreadUtils.threadSleep(10L);

		final CompletableFuture<Void> save2Async = executeAsync(() -> {
			try {
				contractService.save(contract2);
			} catch (final ValidationException e) {
				Assert.fail(e.getMessage());
			}
		});

		CompletableFuture.allOf(save1Async, save2Async).join();

		final Contract contractFound = contractService.findById(id).get();

		Assert.assertNotNull(contractFound);
		Assert.assertEquals(id, contractFound.getId());
		Assert.assertEquals(LocalDate.now().plusDays(2), contractFound.getDate());
		Assert.assertEquals(2, contractFound.getQuotas().size());
	}

	@Test
	public void testSaveNewContract_changeBankAndSaveAgain_bankShouldNotBeChanged() throws ValidationException {

		final Contract contract = contractGenerator.generate();

		final Contract contract2 = contractService.save(contract);
		Assert.assertNotNull(contract2);
		Assert.assertNotNull(contract2.getId());
		Assert.assertNotNull(contract2.getBank());
		Assert.assertEquals(2, contract2.getQuotas().size());

		final String bankName = contract2.getBank().getName();
		contract2.getBank().setName("Bank changed");

		final Contract contract3 = contractService.save(contract2);
		Assert.assertNotNull(contract3);
		Assert.assertNotNull(contract3.getId());
		Assert.assertNotNull(contract3.getBank());
		Assert.assertEquals(2, contract3.getQuotas().size());
		Assert.assertEquals(bankName, contract3.getBank().getName());
	}

	@Test(expected = ValidationException.class)
	public void testSaveNewContractWithoutBank_withError() throws ValidationException {

		final Contract contract = contractGenerator.generate(true, false);

		final Contract contractSaved = contractService.save(contract);
		Assert.assertNotNull(contractSaved);
	}

	private CompletableFuture<Void> executeAsync(final Runnable runnable) {
		return CompletableFuture.runAsync(runnable);
	}
}