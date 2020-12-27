package com.companycob.service.lock.contract;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.companycob.domain.model.entity.Contract;
import com.companycob.tests.AbstractUnitTest;
import com.companycob.utils.thread.ThreadUtils;

public class ContractLockerTest extends AbstractUnitTest {

	@Autowired
	private ContractLocker contractLocker;

	@Test
	public void testLockAndReleaseSingleThread() {

		final Contract contract = this.contractGenerator.generate();
		contractLocker.tryLock(contract);

		assertThat(contractLocker.isLocked(contract)).isTrue();

		contractLocker.release(contract);
		assertThat(contractLocker.isLocked(contract)).isFalse();
	}

	@Test
	public void testMultipleLocksAndReleaseSingleThread() {

		final Contract contract = this.contractGenerator.generate();
		contractLocker.tryLock(contract);
		contractLocker.tryLock(contract);
		contractLocker.tryLock(contract);

		assertThat(contractLocker.isLocked(contract)).isTrue();

		contractLocker.release(contract);
		assertThat(contractLocker.isLocked(contract)).isFalse();
	}

	@Test
	public void testMultipleLocksWithWaitAndReleaseMultiThread() {

		final Contract contract = this.contractGenerator.generate();

		final var run1 = runAsync(() -> lockAwaitAndRelease(contract));
		final var run2 = runAsync(() -> lockAwaitAndRelease(contract));
		final var run3 = runAsync(() -> lockAwaitAndRelease(contract));

		awaitAllCompletableFutures(run1, run2, run3);

		assertThat(contractLocker.isLocked(contract)).isFalse();
	}

	@Test
	public void testMultipleLocksWithoutWaitAndReleaseMultiThread() {

		final Contract contract = this.contractGenerator.generate();

		final var run1 = runAsync(() -> lockAwaitAndRelease(contract, 1L));
		final var run2 = runAsync(() -> lockAwaitAndRelease(contract, 1L));
		final var run3 = runAsync(() -> lockAwaitAndRelease(contract, 1L));
		final var run4 = runAsync(() -> lockAwaitAndRelease(contract, 1L));
		final var run5 = runAsync(() -> lockAwaitAndRelease(contract, 1L));

		awaitAllCompletableFutures(run1, run2, run3, run4, run5);

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
