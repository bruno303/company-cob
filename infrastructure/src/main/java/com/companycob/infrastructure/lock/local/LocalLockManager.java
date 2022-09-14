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
		LOGGER.info("Acquiring lock for object with key {}", object.getLockIdentifier());
		final LocalLock lock = acquireLock(object);
		final boolean lockResult = lock.tryLock(timeout, timeUnit);
		if (lockResult) {
			LOGGER.info("Lock acquired for object with key {}", object.getLockIdentifier());
		}
	}

	private synchronized LocalLock acquireLock(final T object) {

		LocalLock lock = locks.get(object.getLockIdentifier());
		if (lock == null) {
			LOGGER.info("Creating new lock for object with key {}", object.getLockIdentifier());
			lock = new LocalLock();
			locks.putIfAbsent(object.getLockIdentifier(), lock);
		}

		return lock;
	}

	@Override
	public void release(final T object) {
		LOGGER.info("Releasing lock for object with key {}", object.getLockIdentifier());
		final LocalLock lock = locks.get(object.getLockIdentifier());
		if (lock == null) {
			LOGGER.info("Object with key {} not found! Ignoring...", object.getLockIdentifier());
			return;
		}

		lock.release();
		LOGGER.info("Lock released for object with key {}", object.getLockIdentifier());
	}

	@Override
	public boolean isLocked(final T object) {
		final LocalLock lock = locks.get(object.getLockIdentifier());
		return (lock != null && lock.isLocked());
	}
}
