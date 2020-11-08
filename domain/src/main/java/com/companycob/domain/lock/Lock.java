package com.companycob.domain.lock;

import java.util.concurrent.TimeUnit;

public interface Lock {

	boolean tryLock();

	boolean tryLock(final long timeout, final TimeUnit timeUnit);

	boolean isLocked();

	void release();

}
