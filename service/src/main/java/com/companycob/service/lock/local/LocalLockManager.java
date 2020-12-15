package com.companycob.service.lock.local;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.companycob.domain.lock.LockManager;
import com.companycob.domain.lock.Lockable;

public class LocalLockManager<T extends Lockable> implements LockManager<T> {

	private final Map<String, LocalLock> locks = new HashMap<>();
	private final Object lockObject = new Object();

	@Override
	public void tryLock(final T object) {
		this.tryLock(object, 60, TimeUnit.SECONDS);
	}

	@Override
	public void tryLock(final T object, final long timeout, final TimeUnit timeUnit) {
		LocalLock lock = null;

		synchronized (lockObject) {

			lock = locks.get(object.getKey());
			if (lock == null) {
				lock = new LocalLock();
				locks.putIfAbsent(object.getKey(), lock);
			}

		}

		lock.tryLock(timeout, timeUnit);
	}

	@Override
	public void release(final T object) {
		final LocalLock lock = locks.get(object.getKey());
		if (lock == null) {
			return;
		}

		lock.release();
	}

	public boolean isLocked(final T object) {
		final LocalLock lock = locks.get(object.getKey());
		return (lock != null && lock.isLocked());
	}
}
