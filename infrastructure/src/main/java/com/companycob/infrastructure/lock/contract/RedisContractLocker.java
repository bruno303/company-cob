package com.companycob.infrastructure.lock.contract;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.companycob.domain.lock.contract.ContractLocker;
import com.companycob.domain.model.entity.Contract;
import com.companycob.infrastructure.annotations.ConditionalOnRedisLock;
import com.companycob.infrastructure.lock.redis.CompanyCobRedissonClient;
import com.companycob.infrastructure.lock.redis.RedisLockManager;

@Component
@ConditionalOnRedisLock
public class RedisContractLocker extends RedisLockManager<Contract> implements ContractLocker {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisContractLocker.class);

    public RedisContractLocker(CompanyCobRedissonClient redisson) {
        super(redisson);
    }

    @PostConstruct
    public void init() {
        LOGGER.info("Using redis lock manager");
    }
}
