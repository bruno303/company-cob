package com.companycob.tests.testcontainers;

import org.testcontainers.containers.GenericContainer;

public class CompanyCobRedisContainer extends GenericContainer<CompanyCobRedisContainer> {
    private static final String IMAGE_VERSION = "redis:5.0.3-alpine";
    private static CompanyCobRedisContainer container;

    private CompanyCobRedisContainer() {
        super(IMAGE_VERSION);
        this.withExposedPorts(6379);
    }

    public static CompanyCobRedisContainer getInstance() {
        if (container == null) {
            container = new CompanyCobRedisContainer();
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
