package com.companycob.infrastructure.cache.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "application.cache")
public class CacheApplicationProperties {

    private Long contractTtl;

    public Long getContractTtl() {
        return contractTtl;
    }

    public void setContractTtl(Long contractTtl) {
        this.contractTtl = contractTtl;
    }
}
