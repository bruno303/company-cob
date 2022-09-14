package com.companycob.infrastructure.lock.redis;

import com.companycob.infrastructure.annotations.ConditionalOnRedisLock;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnRedisLock
public class RedissonConfiguration {

    @Bean
    public RedissonClient redissonClient(RedisApplicationProperties redisApplicationProperties) {
        final Config redissonConfig = getRedissonConfig(redisApplicationProperties);
        return Redisson.create(redissonConfig);
    }

    private Config getRedissonConfig(RedisApplicationProperties redisApplicationProperties) {
        final Config config = new Config();
        final String host = redisApplicationProperties.getHost();
        final int port = redisApplicationProperties.getPort();

        config.useSingleServer().setAddress(String.format("redis://%s:%d", host, port));

        return config;
    }
}
