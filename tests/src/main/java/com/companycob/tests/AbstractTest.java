package com.companycob.tests;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;

import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.companycob.tests.config.AppConfig;
import com.companycob.tests.fixture.Fixture;
import com.companycob.tests.fixture.generator.BankGenerator;
import com.companycob.tests.fixture.generator.ContractGenerator;
import com.companycob.tests.fixture.generator.QuotaGenerator;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@ActiveProfiles({ "test" })
@ContextConfiguration(classes = { AppConfig.class })
@TestPropertySource(value = { "classpath:application-test.yml" })
public class AbstractTest {

	@Autowired
	protected BankGenerator bankGenerator;

	@Autowired
	protected QuotaGenerator quotaGenerator;

	@Autowired
	protected ContractGenerator contractGenerator;

	@Autowired
	protected Fixture fixture;

	@Before
	public void setUp() {
		fixture.clearDatabase();
	}

	public static void assertEqualsBigDecimal(final BigDecimal expected, final BigDecimal actual) {
		if (expected.compareTo(actual) != 0) {
			Assert.fail(String.format("Expected [%.3f] but received [%.3f]", expected, actual));
		}
	}

	protected CompletableFuture<Void> runAsync(final Runnable runnable) {
		return CompletableFuture.runAsync(runnable);
	}

	protected void awaitAllCompletableFutures(final CompletableFuture<?>... completableFutures) {
		CompletableFuture.allOf(completableFutures).join();
	}
}