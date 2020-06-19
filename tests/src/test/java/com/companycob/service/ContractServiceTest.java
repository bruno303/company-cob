package com.companycob.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.companycob.domain.exception.ValidationException;
import com.companycob.domain.model.entity.Contract;
import com.companycob.domain.model.entity.Quota;
import com.companycob.tests.AbstractServiceTest;
import com.companycob.utils.thread.ThreadUtils;

public class ContractServiceTest extends AbstractServiceTest {
	
	@Autowired
	private ContractService contractService;
	
	@Test(expected = ValidationException.class)
	public void testSaveNewContract_withNoContractNumber() throws ValidationException {
		
		Contract contract = new Contract();
		contract.setDate(LocalDate.now());
		
		Contract contract2 = contractService.save(contract);
		Assert.assertNull(contract2);
	}
	
	@Test(expected = ValidationException.class)
	public void testSaveNewContract_withEmptyContractNumber() throws ValidationException {
		
		Contract contract = new Contract();
		contract.setDate(LocalDate.now());
		contract.setContractNumber("");
		
		Contract contract2 = contractService.save(contract);
		Assert.assertNull(contract2);
	}
	
	@Test(expected = ValidationException.class)
	public void testSaveNewContract_withNoContractDate() throws ValidationException {
		
		Contract contract = new Contract();
		contract.setContractNumber("abc");
		
		Contract contract2 = contractService.save(contract);
		Assert.assertNull(contract2);
	}
	
	@Test
	public void testSaveNewContract_withSucess() throws ValidationException {
		
		Contract contract = generateValidContract();
		
		Contract contract2 = contractService.save(contract);
		Assert.assertNotNull(contract2);
		Assert.assertNotNull(contract2.getId());
		Assert.assertEquals(Long.valueOf(1L), contract2.getId());
		Assert.assertEquals(2, contract2.getQuotas().size());
	}
	
	@Test
	public void testSaveNewContractAndLoadById() throws ValidationException {
		Contract contract = generateValidContract();
		
		Contract contractSaved = contractService.save(contract);
		
		Optional<Contract> contractLoadedOptional = contractService.findById(contractSaved.getId());
		Assert.assertTrue(contractLoadedOptional.isPresent());

		Contract contractLoaded = contractLoadedOptional.get();
		
		Assert.assertNotNull(contractLoaded);
		Assert.assertEquals(contractSaved.getId(), contractLoaded.getId());
		Assert.assertEquals(contractSaved.getDate(), contractLoaded.getDate());
		Assert.assertEquals(contractSaved.getContractNumber(), contractLoaded.getContractNumber());
		Assert.assertEquals(2, contractLoaded.getQuotas().size());
	}
	
	@Test
	public void testSaveNewContractAndLoadByContractNumber() throws ValidationException {
		Contract contract = generateValidContract();
		
		Contract contractSaved = contractService.save(contract);
		
		List<Contract> contractsLoaded = contractService.findByContractNumber(contractSaved.getContractNumber());
		Assert.assertEquals(1, contractsLoaded.size());

		Contract contractLoaded = contractsLoaded.get(0);
		
		Assert.assertNotNull(contractLoaded);
		Assert.assertEquals(contractSaved.getId(), contractLoaded.getId());
		Assert.assertEquals(contractSaved.getDate(), contractLoaded.getDate());
		Assert.assertEquals(contractSaved.getContractNumber(), contractLoaded.getContractNumber());
		Assert.assertEquals(2, contractLoaded.getQuotas().size());
	}
	
	@Test
	public void testSaveNewContractTwice_withSucess() throws ValidationException {
		
		Contract contract = generateValidContract();
		
		Contract contractSaved = contractService.save(contract);
		final Long id = contractSaved.getId();
		
		Optional<Contract> contract2Optional = contractService.findById(id);
		Assert.assertTrue(contract2Optional.isPresent());
		
		Contract contract2 = contract2Optional.get();
		contractSaved.setDate(LocalDate.now().plusDays(1));
		contract2.setDate(LocalDate.now().plusDays(2));

		CompletableFuture<Void> save1Async = executeAsync(() -> {
			try {
				contractService.save(contractSaved);
			} catch (ValidationException e) {
				Assert.fail(e.getMessage());
			}
		});
		
		ThreadUtils.threadSleep(100L);
		
		CompletableFuture<Void> save2Async = executeAsync(() -> {
			try {
				contractService.save(contract2);
			} catch (ValidationException e) {
				Assert.fail(e.getMessage());
			}
		});
		
		CompletableFuture.allOf(save1Async, save2Async).join();
		
		Contract contractFound = contractService.findById(id).get();
		
		Assert.assertNotNull(contractFound);
		Assert.assertEquals(id, contractFound.getId());
		Assert.assertEquals(LocalDate.now().plusDays(2), contractFound.getDate());
		Assert.assertEquals(2, contractFound.getQuotas().size());
	}
	
	private CompletableFuture<Void> executeAsync(Runnable runnable) {
		return CompletableFuture.runAsync(runnable);
	}
	
	private Contract generateValidContract() {
		Contract contract = new Contract();
		contract.setDate(LocalDate.now());
		contract.setContractNumber(RandomStringUtils.randomAlphanumeric(20));
		
		Quota quota = new Quota();
		quota.setContract(contract);
		quota.setDueDate(LocalDate.of(2020, 01, 01));
		quota.setInitialValue(200);
		quota.setNumber(1);
		
		Quota quota2 = new Quota();
		quota2.setContract(contract);
		quota2.setDueDate(LocalDate.of(2020, 02, 01));
		quota2.setInitialValue(200);
		quota2.setNumber(2);
		
		contract.setQuotas(List.of(quota, quota2));
		
		return contract;
	}
}