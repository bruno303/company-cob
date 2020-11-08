package com.companycob.domain.lock;

import java.util.concurrent.TimeUnit;

public interface LockManager<T extends Lockable> {

	void tryLock(final T object);

	void tryLock(final T object, long timeout, TimeUnit timeUnit);

	void release(final T object);

}
