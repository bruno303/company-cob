package com.companycob.tests.database.testcontainers;

import org.testcontainers.containers.GenericContainer;

public class CompanyCobRedisBackedCacheContainer extends GenericContainer<CompanyCobRedisBackedCacheContainer> {
    private static final String IMAGE_VERSION = "redis:5.0.3-alpine";
    private static CompanyCobRedisBackedCacheContainer container;

    private CompanyCobRedisBackedCacheContainer() {
        super(IMAGE_VERSION);
        this.withExposedPorts(6379);
    }

    public static CompanyCobRedisBackedCacheContainer getInstance() {
        if (container == null) {
            container = new CompanyCobRedisBackedCacheContainer();
            container.start();
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("REDIS_HOST", container.getHost());
        System.setProperty("REDIS_PORT", String.valueOf(container.getFirstMappedPort()));
    }

    @Override
    public void stop() {
        // do nothing, JVM handles shut down
    }
}
