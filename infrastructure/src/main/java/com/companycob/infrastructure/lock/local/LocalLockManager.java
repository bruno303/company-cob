package com.companycob.infrastructure.lock.local;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.companycob.domain.lock.LockManager;
import com.companycob.domain.lock.Lockable;

public class LocalLockManager<T extends Lockable> implements LockManager<T> {

	private static final Logger LOGGER = LoggerFactory.getLogger(LocalLockManager.class);

	private final Map<String, LocalLock> locks = new HashMap<>();

	@Override
	public void tryLock(final T object) {
		this.tryLock(object, 60, TimeUnit.SECONDS);
	}

	@Override
	public void tryLock(final T object, final long timeout, final TimeUnit timeUnit) {
		LOGGER.info("Acquiring lock for contract with key {}", object.getKey());
		final LocalLock lock = acquireLock(object);
		final boolean lockResult = lock.tryLock(timeout, timeUnit);
		if (lockResult) {
			LOGGER.info("Lock acquired for contract with key {}", object.getKey());
		}
	}

	private synchronized LocalLock acquireLock(final T object) {

		LocalLock lock = locks.get(object.getKey());
		if (lock == null) {
			LOGGER.info("Creating new lock for contract with key {}", object.getKey());
			lock = new LocalLock();
			locks.putIfAbsent(object.getKey(), lock);
		}

		return lock;
	}

	@Override
	public void release(final T object) {
		LOGGER.info("Releasing lock for contract with key {}", object.getKey());
		final LocalLock lock = locks.get(object.getKey());
		if (lock == null) {
			LOGGER.info("Contract with key {} not found! Ignoring...", object.getKey());
			return;
		}

		lock.release();
		LOGGER.info("Lock released for contract with key {}", object.getKey());
	}

	@Override
	public boolean isLocked(final T object) {
		final LocalLock lock = locks.get(object.getKey());
		return (lock != null && lock.isLocked());
	}
}
