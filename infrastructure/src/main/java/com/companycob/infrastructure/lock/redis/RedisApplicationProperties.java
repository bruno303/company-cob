package com.companycob.infrastructure.lock.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.companycob.infrastructure.annotations.ConditionalOnRedisLock;

@Configuration
@ConfigurationProperties(prefix = "spring.redis")
@ConditionalOnRedisLock
public class RedisApplicationProperties {

    private String host;
    private int port;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
