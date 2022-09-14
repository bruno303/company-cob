package com.companycob.tests.testcontainers;

import org.testcontainers.containers.PostgreSQLContainer;

public class CompanyCobPostgresContainer extends PostgreSQLContainer<CompanyCobPostgresContainer> {
	private static final String IMAGE_VERSION = "postgres:13-alpine";
	private static CompanyCobPostgresContainer container;

	private CompanyCobPostgresContainer() {
		super(IMAGE_VERSION);
	}

	public static CompanyCobPostgresContainer getInstance() {
		if (container == null) {
			container = new CompanyCobPostgresContainer();
			container.start();
		}
		return container;
	}

	@Override
	public void start() {
		super.start();
		System.setProperty("DB_URL", container.getJdbcUrl());
		System.setProperty("DB_USERNAME", container.getUsername());
		System.setProperty("DB_PASSWORD", container.getPassword());
	}

	@Override
	public void stop() {
		// do nothing, JVM handles shut down
	}
}
