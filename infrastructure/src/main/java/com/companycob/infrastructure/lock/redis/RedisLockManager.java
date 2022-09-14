package com.companycob.infrastructure.lock.redis;

import com.companycob.domain.lock.LockManager;
import com.companycob.domain.lock.Lockable;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class RedisLockManager<T extends Lockable> implements LockManager<T> {

    private static final Logger LOG = LoggerFactory.getLogger(RedisLockManager.class);
    private final RedissonClient redissonClient;

    public RedisLockManager(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public void tryLock(T object) {
        this.tryLock(object, 60, TimeUnit.SECONDS);
    }

    @Override
    public void tryLock(T object, long timeout, TimeUnit timeUnit) {
        final RLock lock = getLock(object);
        boolean lockResult;

        LOG.info("Locking...");
        try {
            lockResult = lock.tryLock(timeout, timeUnit);
        } catch (InterruptedException e) {
            LOG.warn("Error when getting lock");
            Thread.currentThread().interrupt();
            lockResult = false;
        }

        if (lockResult) {
            LOG.info("Lock acquired for object with key {}", object.getLockIdentifier());
        }
    }

    @Override
    public void release(T object) {
        LOG.info("Releasing lock for object with key {}", object.getLockIdentifier());
        final RLock lock = getLock(object);
        while (lock.isLocked() && lock.isHeldByCurrentThread()) {
            lock.unlock();
        }
        LOG.info("Lock released for object with key {}", object.getLockIdentifier());
    }

    @Override
    public boolean isLocked(T object) {
        final RLock lock = getLock(object);
        return lock.isLocked();
    }

    private synchronized RLock getLock(T object) {
        final String lockIdentifier = object.getLockIdentifier();
        return redissonClient.getLock(lockIdentifier);
    }
}
