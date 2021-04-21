package com.companycob.tests;

import java.util.concurrent.CompletableFuture;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import com.companycob.tests.config.AppConfig;
import com.companycob.tests.database.testcontainers.CompanyCobRedisBackedCacheContainer;
import com.companycob.tests.exception.SetupDatabaseException;
import com.companycob.tests.fixture.Fixture;
import com.companycob.tests.fixture.generator.BankGenerator;
import com.companycob.tests.fixture.generator.ContractGenerator;
import com.companycob.tests.fixture.generator.QuotaGenerator;

import liquibase.Liquibase;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@ActiveProfiles({ "test" })
@ContextConfiguration(classes = { AppConfig.class })
@TestPropertySource(value = { "classpath:application-test.yml" })
@Testcontainers
public class AbstractTest {

	@Autowired
	protected BankGenerator bankGenerator;

	@Autowired
	protected QuotaGenerator quotaGenerator;

	@Autowired
	protected ContractGenerator contractGenerator;

	@Autowired
	protected Fixture fixture;

	@ClassRule
	@Container
	public static final CompanyCobRedisBackedCacheContainer redis = CompanyCobRedisBackedCacheContainer.getInstance();

	@Before
	public void setUp() {
		fixture.clearDatabase();
	}

	protected CompletableFuture<Void> runAsync(final Runnable runnable) {
		return CompletableFuture.runAsync(runnable);
	}

	protected void awaitAllCompletableFutures(final CompletableFuture<?>... completableFutures) {
		CompletableFuture.allOf(completableFutures).join();
	}
}