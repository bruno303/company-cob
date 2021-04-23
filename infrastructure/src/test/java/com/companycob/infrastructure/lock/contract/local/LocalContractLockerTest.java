package com.companycob.infrastructure.lock.contract.local;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import com.companycob.domain.lock.contract.ContractLocker;
import com.companycob.domain.model.entity.Contract;
import com.companycob.infrastructure.lock.contract.LocalContractLocker;
import com.companycob.tests.fixture.unit.Generator;
import com.companycob.tests.utils.UnitTestsUtils;
import com.companycob.utils.thread.ThreadUtils;

public class LocalContractLockerTest {

	private final Generator generator = new Generator();

	private ContractLocker contractLocker;

	@Before
	public void init() {
		contractLocker = new LocalContractLocker();
	}

	@Test
	public void testLockAndReleaseSingleThread() {

		final Contract contract = generator.generateContract();
		contractLocker.tryLock(contract);

		Assertions.assertThat(contractLocker.isLocked(contract)).isTrue();

		contractLocker.release(contract);
		Assertions.assertThat(contractLocker.isLocked(contract)).isFalse();
	}

	@Test
	public void testMultipleLocksAndReleaseSingleThread() {

		final Contract contract = generator.generateContract();
		contractLocker.tryLock(contract);
		contractLocker.tryLock(contract);
		contractLocker.tryLock(contract);

		Assertions.assertThat(contractLocker.isLocked(contract)).isTrue();

		contractLocker.release(contract);
		Assertions.assertThat(contractLocker.isLocked(contract)).isFalse();
	}

	@Test
	public void testMultipleLocksWithWaitAndReleaseMultiThread() {

		final Contract contract = generator.generateContract();

		final var run1 = UnitTestsUtils.runAsync(() -> lockAwaitAndRelease(contract));
		final var run2 = UnitTestsUtils.runAsync(() -> lockAwaitAndRelease(contract));
		final var run3 = UnitTestsUtils.runAsync(() -> lockAwaitAndRelease(contract));

		UnitTestsUtils.awaitAllCompletableFutures(run1, run2, run3);

		Assertions.assertThat(contractLocker.isLocked(contract)).isFalse();
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

		Assertions.assertThat(contractLocker.isLocked(contract)).isFalse();
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
