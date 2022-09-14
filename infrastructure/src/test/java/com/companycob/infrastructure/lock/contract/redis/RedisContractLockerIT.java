package com.companycob.infrastructure.lock.contract.redis;

import static org.assertj.core.api.Assertions.assertThat;

import com.companycob.tests.AbstractDatabaseIntegrationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;

import com.companycob.domain.lock.contract.ContractLocker;
import com.companycob.domain.model.entity.Contract;
import com.companycob.tests.fixture.unit.Generator;
import com.companycob.tests.utils.UnitTestsUtils;
import com.companycob.utils.thread.ThreadUtils;

@TestPropertySource(properties = { "application.lock.type=redis" } )
public class RedisContractLockerIT extends AbstractDatabaseIntegrationTest {

	private final Generator generator = new Generator();

	@Autowired
	private ContractLocker contractLocker;

	@Test
	public void testLockAndReleaseSingleThread() {

		final Contract contract = generator.generateContract();
		contractLocker.tryLock(contract);

		assertThat(contractLocker.isLocked(contract)).isTrue();

		contractLocker.release(contract);
		assertThat(contractLocker.isLocked(contract)).isFalse();
	}

	@Test
	public void testMultipleLocksAndReleaseSingleThread() {

		final Contract contract = generator.generateContract();
		contractLocker.tryLock(contract);
		contractLocker.tryLock(contract);
		contractLocker.tryLock(contract);

		assertThat(contractLocker.isLocked(contract)).isTrue();

		contractLocker.release(contract);
		assertThat(contractLocker.isLocked(contract)).isFalse();
	}

	@Test
	public void testMultipleLocksWithWaitAndReleaseMultiThread() {

		final Contract contract = generator.generateContract();

		final var run1 = UnitTestsUtils.runAsync(() -> lockAwaitAndRelease(contract));
		final var run2 = UnitTestsUtils.runAsync(() -> lockAwaitAndRelease(contract));
		final var run3 = UnitTestsUtils.runAsync(() -> lockAwaitAndRelease(contract));

		UnitTestsUtils.awaitAllCompletableFutures(run1, run2, run3);

		assertThat(contractLocker.isLocked(contract)).isFalse();
	}

	@Test
	public void testMultipleLocksWithoutWaitAndReleaseMultiThread() {

		final Contract contract = generator.generateContract();

		final var run1 = UnitTestsUtils.runAsync(() -> lockAwaitAndRelease(contract, 1L));
		final var run2 = UnitTestsUtils.runAsync(() -> lockAwaitAndRelease(contract, 1L));
		final var run3 = UnitTestsUtils.runAsync(() -> lockAwaitAndRelease(contract, 1L));
		final var run4 = UnitTestsUtils.runAsync(() -> lockAwaitAndRelease(contract, 1L));
		final var run5 = UnitTestsUtils.runAsync(() -> lockAwaitAndRelease(contract, 1L));

		UnitTestsUtils.awaitAllCompletableFutures(run1, run2, run3, run4, run5);

		assertThat(contractLocker.isLocked(contract)).isFalse();
	}

	private void lockAwaitAndRelease(final Contract contract) {
		lockAwaitAndRelease(contract, 200L);
	}

	private void lockAwaitAndRelease(final Contract contract, final long milis) {
		contractLocker.tryLock(contract);
		ThreadUtils.threadSleep(milis);
		contractLocker.release(contract);
	}
}
