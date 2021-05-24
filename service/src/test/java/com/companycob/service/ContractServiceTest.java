package com.companycob.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import com.companycob.domain.exception.ValidationException;
import com.companycob.domain.model.entity.Contract;
import com.companycob.domain.model.persistence.ContractRepository;
import com.companycob.service.lock.contract.ContractLocker;
import com.companycob.tests.fixture.unit.Generator;
import com.companycob.tests.utils.UnitTestsUtils;

public class ContractServiceTest {

	// Mocks
	private final ContractRepository contractRepository = Mockito.mock(ContractRepository.class);
	private final ContractLocker contractLocker = Mockito.mock(ContractLocker.class);

	private final Generator generator = new Generator();

	private ContractService contractService;

	@Before
	public void init() {
		contractService = new ContractService(contractRepository, contractLocker);
	}

	@Test
	public void testSaveNewContract_withSucess() {
		final var contract = generator.generateContract();
		final var expectedResponse = generator.copy(contract);
		expectedResponse.setId(1L);

		Mockito.when(contractRepository.save(Mockito.any(Contract.class))).thenReturn(expectedResponse);

		final var contract2 = contractService.save(contract);
		assertThat(contract2).isNotNull();
		assertThat(contract2.getId()).isEqualTo(1L);
		assertThat(contract2.getBank()).isNotNull();
		assertThat(contract2.getQuotas().size()).isEqualTo(2);

		Mockito.verify(contractRepository, VerificationModeFactory.times(1)).save(Mockito.any(Contract.class));
	}

	@Test
	public void testSaveNewContractAndLoadById() {
		final var contract = generator.generateContract();
		final var expectedResponse = generator.copy(contract);
		expectedResponse.setId(1L);

		Mockito.when(contractRepository.save(Mockito.any(Contract.class))).thenReturn(expectedResponse);
		Mockito.when(contractRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(expectedResponse));

		final var contractSaved = contractService.save(contract);

		final var contractLoadedOptional = contractService.findById(contractSaved.getId());
		assertThat(contractLoadedOptional).isPresent();

		final var contractLoaded = contractLoadedOptional.get();

		assertThat(contractLoaded).isNotNull();
		assertThat(contractLoaded.getId()).isEqualTo(contractSaved.getId());
		assertThat(contractLoaded.getDate()).isEqualTo(contractSaved.getDate());
		assertThat(contractLoaded.getContractNumber()).isEqualTo(contractSaved.getContractNumber());
		assertThat(contractLoaded.getQuotas().size()).isEqualTo(2);

		Mockito.verify(contractRepository, VerificationModeFactory.times(1)).save(Mockito.any(Contract.class));
		Mockito.verify(contractRepository, VerificationModeFactory.times(1)).findById(Mockito.anyLong());
	}

	@Test
	public void testSaveNewContractAndLoadByContractNumber() {
		final var contract = generator.generateContract();
		final var expectedResponse = generator.copy(contract);
		expectedResponse.setId(1L);

		Mockito.when(contractRepository.save(Mockito.any(Contract.class))).thenReturn(expectedResponse);
		Mockito.when(contractRepository.findByContractNumber(Mockito.anyString())).thenReturn(List.of(expectedResponse));

		final var contractSaved = contractService.save(contract);

		final var contractsLoaded = contractService.findByContractNumber(contractSaved.getContractNumber());
		assertThat(contractsLoaded.size()).isEqualTo(1);

		final var contractLoaded = contractsLoaded.get(0);

		assertThat(contractLoaded).isNotNull();
		assertThat(contractLoaded.getId()).isEqualTo(contractSaved.getId());
		assertThat(contractLoaded.getDate()).isEqualTo(contractSaved.getDate());
		assertThat(contractLoaded.getContractNumber()).isEqualTo(contractSaved.getContractNumber());
		assertThat(contractLoaded.getQuotas().size()).isEqualTo(2);

		Mockito.verify(contractRepository, VerificationModeFactory.times(1)).save(Mockito.any(Contract.class));
		Mockito.verify(contractRepository, VerificationModeFactory.times(1)).findByContractNumber(Mockito.anyString());
	}

	@Test
	public void testSaveNewContractMultipleTimes_withSucess() {

		final var contract = generator.generateContract();

		final var expectedResponse = generator.copy(contract);
		expectedResponse.setId(1L);

		Mockito.when(contractRepository.save(Mockito.any(Contract.class))).thenReturn(expectedResponse);
		Mockito.when(contractRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(expectedResponse));

		final var contractSaved = contractService.save(contract);
		final var id = contractSaved.getId();

		contractSaved.setDate(LocalDate.now().plusDays(1));

		final var save1Async = UnitTestsUtils.runAsync(() -> contractService.save(contractSaved));
		final var save2Async = UnitTestsUtils.runAsync(() -> contractService.save(contractSaved));
		final var save3Async = UnitTestsUtils.runAsync(() -> contractService.save(contractSaved));
		final var save4Async = UnitTestsUtils.runAsync(() -> contractService.save(contractSaved));
		final var save5Async = UnitTestsUtils.runAsync(() -> contractService.save(contractSaved));

		UnitTestsUtils.awaitAllCompletableFutures(save1Async, save2Async, save3Async, save4Async, save5Async);

		final var contractFound = contractService.findById(id).orElse(null);

		assertThat(contractFound).isNotNull();
		assertThat(contractFound.getId()).isEqualTo(id);

		assertThat(contractFound.getDate()).isEqualTo(contractSaved.getDate());
		assertThat(contractFound.getQuotas().size()).isEqualTo(2);

		Mockito.verify(contractRepository, VerificationModeFactory.times(6)).save(Mockito.any(Contract.class));
		Mockito.verify(contractRepository, VerificationModeFactory.times(1)).findById(Mockito.anyLong());
	}

	@Test
	public void testSaveNewContractWithoutBank_withError() {

		final var contract = generator.generateContract(true, false);

		assertThrows(ValidationException.class, () -> contractService.save(contract));
	}
}