package com.companycob.tests.fixture;

import static org.assertj.core.api.Assertions.assertThat;

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

		assertThat(contractRepository.findAll().isEmpty()).isTrue();
		assertThat(bankRepository.findAll().isEmpty()).isTrue();

		fixture.clearDatabase();

		assertThat(contractRepository.findAll().isEmpty()).isTrue();
		assertThat(bankRepository.findAll().isEmpty()).isTrue();
	}

	@Test
	public void testClearPopulatedDatabase() {
		final Contract contract = contractGenerator.generate(true, true);
		contractRepository.save(contract);

		assertThat(contractRepository.findAll().size()).isEqualTo(1);
		assertThat(bankRepository.findAll().size()).isEqualTo(1);

		fixture.clearDatabase();

		assertThat(contractRepository.findAll().isEmpty()).isTrue();
		assertThat(bankRepository.findAll().isEmpty()).isTrue();
	}
}
