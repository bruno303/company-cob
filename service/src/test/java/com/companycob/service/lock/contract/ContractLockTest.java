package com.companycob.service.lock.contract;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.companycob.domain.exception.ValidationException;
import com.companycob.domain.model.entity.Contract;
import com.companycob.tests.AbstractUnitTest;

public class ContractLockTest extends AbstractUnitTest {

	@Autowired
	private ContractLocker contractLocker;

	@Test
	public void testLockAndReleaseSingleThread() throws ValidationException {

		final Contract contract = this.contractGenerator.generate();
		contractLocker.tryLock(contract);

		Assert.assertTrue(contractLocker.isLocked(contract));

		contractLocker.release(contract);
		Assert.assertFalse(contractLocker.isLocked(contract));
	}

	@Test
	public void testMultipleLocksAndReleaseSingleThread() throws ValidationException {

		final Contract contract = this.contractGenerator.generate();
		contractLocker.tryLock(contract);
		contractLocker.tryLock(contract);
		contractLocker.tryLock(contract);

		Assert.assertTrue(contractLocker.isLocked(contract));

		contractLocker.release(contract);
		Assert.assertFalse(contractLocker.isLocked(contract));
	}
}
