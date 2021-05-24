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
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import com.companycob.tests.config.AppConfig;
import com.companycob.tests.database.testcontainers.CompanyCobRedisBackedCacheContainer;
import com.companycob.tests.fixture.Fixture;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@ActiveProfiles({ "test" })
@ContextConfiguration(classes = { AppConfig.class })
@TestPropertySource(value = { "classpath:application-test.yml" })
@Testcontainers
public class AbstractTest {

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