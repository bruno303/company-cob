package com.companycob.service.lock.local;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.companycob.domain.lock.Lock;

public class LocalLock implements Lock {

	private static final Logger LOG = LoggerFactory.getLogger(LocalLock.class);

	private final ReentrantLock lock;

	public LocalLock() {
		this.lock = new ReentrantLock();
	}

	@Override
	public boolean tryLock() {
		return lock.tryLock();
	}

	@Override
	public boolean tryLock(final long timeout, final TimeUnit timeUnit) {
		try {
			return lock.tryLock(timeout, timeUnit);
		} catch (final InterruptedException e) {
			LOG.warn("Error when getting lock");
			Thread.currentThread().interrupt();
			return false;
		}
	}

	@Override
	public void release() {
		while (lock.isLocked()) {
			lock.unlock();
		}
	}

	@Override
	public boolean isLocked() {
		return lock.isLocked();
	}
}
