package com.companycob.tests.fixture;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.companycob.domain.model.entity.Contract;
import com.companycob.domain.model.persistence.BankRepository;
import com.companycob.domain.model.persistence.ContractRepository;
import com.companycob.tests.AbstractDatabaseIntegrationTest;

public class FixtureTest extends AbstractDatabaseIntegrationTest {

	@Autowired
	private Fixture fixture;

	@Autowired
	private ContractRepository contractRepository;

	@Autowired
	private BankRepository bankRepository;

	@Test
	public void testClearEmptyDatabase() {
		contractRepository.removeAll();
		bankRepository.removeAll();

		Assert.assertTrue(contractRepository.findAll().isEmpty());
		Assert.assertTrue(bankRepository.findAll().isEmpty());

		fixture.clearDatabase();

		Assert.assertTrue(contractRepository.findAll().isEmpty());
		Assert.assertTrue(bankRepository.findAll().isEmpty());
	}

	@Test
	public void testClearPopulatedDatabase() {
		final Contract contract = contractGenerator.generate(true, true);
		contractRepository.save(contract);

		Assert.assertEquals(1, contractRepository.findAll().size());
		Assert.assertEquals(1, bankRepository.findAll().size());

		fixture.clearDatabase();

		Assert.assertTrue(contractRepository.findAll().isEmpty());
		Assert.assertTrue(bankRepository.findAll().isEmpty());
	}
}
