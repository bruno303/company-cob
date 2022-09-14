package com.companycob.infrastructure.cache.redis;

import com.companycob.infrastructure.annotations.ConditionalOnRedisCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.time.Duration;

@Configuration
@EnableCaching
@ConditionalOnRedisCache
public class RedisCacheConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisCacheConfig.class);

    public static final String CONTRACT_CACHE_NAME = "CONTRACT_CACHE";

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory, CacheApplicationProperties cacheProps) {
        final RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory);

        LOGGER.info("Creating contract cache with TTL {} seconds", cacheProps.getContractTtl());
        final RedisCacheConfiguration contractCacheConfig = createRedisConfiguration(cacheProps.getContractTtl());

        return RedisCacheManager.RedisCacheManagerBuilder.fromCacheWriter(redisCacheWriter)
                .withCacheConfiguration(CONTRACT_CACHE_NAME, contractCacheConfig)
                .build();
    }

    private RedisCacheConfiguration createRedisConfiguration(Long seconds) {
        return RedisCacheConfiguration.defaultCacheConfig()
                .computePrefixWith(c -> "companycob:$name")
                .entryTtl(Duration.ofSeconds(seconds));
    }
}
