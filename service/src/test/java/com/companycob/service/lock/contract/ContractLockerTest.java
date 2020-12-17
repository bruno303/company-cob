package com.companycob.service.lock.contract;

import java.util.List;

import org.junit.Assert;
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

		Assert.assertTrue(contractLocker.isLocked(contract));

		contractLocker.release(contract);
		Assert.assertFalse(contractLocker.isLocked(contract));
	}

	@Test
	public void testMultipleLocksAndReleaseSingleThread() {

		final Contract contract = this.contractGenerator.generate();
		contractLocker.tryLock(contract);
		contractLocker.tryLock(contract);
		contractLocker.tryLock(contract);

		Assert.assertTrue(contractLocker.isLocked(contract));

		contractLocker.release(contract);
		Assert.assertFalse(contractLocker.isLocked(contract));
	}

	@Test
	public void testMultipleLocksWithWaitAndReleaseMultiThread() {

		final Contract contract = this.contractGenerator.generate();

		final var run1 = runAsync(() -> lockAwaitAndRelease(contract));
		final var run2 = runAsync(() -> lockAwaitAndRelease(contract));
		final var run3 = runAsync(() -> lockAwaitAndRelease(contract));

		awaitAllCompletableFutures(List.of(run1, run2, run3));

		Assert.assertFalse(contractLocker.isLocked(contract));
	}

	@Test
	public void testMultipleLocksWithoutWaitAndReleaseMultiThread() {

		final Contract contract = this.contractGenerator.generate();

		final var run1 = runAsync(() -> lockAwaitAndRelease(contract, 1L));
		final var run2 = runAsync(() -> lockAwaitAndRelease(contract, 1L));
		final var run3 = runAsync(() -> lockAwaitAndRelease(contract, 1L));
		final var run4 = runAsync(() -> lockAwaitAndRelease(contract, 1L));
		final var run5 = runAsync(() -> lockAwaitAndRelease(contract, 1L));

		awaitAllCompletableFutures(List.of(run1, run2, run3, run4, run5));

		Assert.assertFalse(contractLocker.isLocked(contract));
	}

	private void lockAwaitAndRelease(Contract contract) {
		lockAwaitAndRelease(contract, 200L);
	}

	private void lockAwaitAndRelease(Contract contract, long milis) {
		contractLocker.tryLock(contract);
		ThreadUtils.threadSleep(milis);
		contractLocker.release(contract);
	}
}
