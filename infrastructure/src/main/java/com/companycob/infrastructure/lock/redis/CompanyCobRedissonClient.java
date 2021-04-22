package com.companycob.infrastructure.lock.redis;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.stereotype.Component;

import com.companycob.infrastructure.annotations.ConditionalOnRedisLock;

@Component
@ConditionalOnRedisLock
public class CompanyCobRedissonClient {

    private final RedissonClient redissonClient;
    private final RedisApplicationProperties redisApplicationProperties;

    public CompanyCobRedissonClient(RedisApplicationProperties redisApplicationProperties) {
        this.redisApplicationProperties = redisApplicationProperties;
        this.redissonClient = Redisson.create(getRedissonConfig());
    }

    private Config getRedissonConfig() {
        final Config config = new Config();
        final String host = redisApplicationProperties.getHost();
        final int port = redisApplicationProperties.getPort();

        config.useSingleServer().setAddress(String.format("redis://%s:%d", host, port));

        return config;
    }

    public RLock getLock(String identifier) {
        return redissonClient.getLock(identifier);
    }
}
