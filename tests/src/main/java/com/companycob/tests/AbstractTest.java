package com.companycob.tests;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;

import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.companycob.tests.config.AppConfig;
import com.companycob.tests.generator.BankGenerator;
import com.companycob.tests.generator.ContractGenerator;
import com.companycob.tests.generator.QuotaGenerator;

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

	public static void assertEqualsBigDecimal(final BigDecimal expected, final BigDecimal actual) {
		if (expected.compareTo(actual) != 0) {
			Assert.fail(String.format("Expected [%.3f] but received [%.3f]", expected, actual));
		}
	}

	protected CompletableFuture<Void> runAsync(final Runnable runnable) {
		return CompletableFuture.runAsync(runnable);
	}

	protected void awaitAllCompletableFutures(CompletableFuture<?>... completableFutures) {
		CompletableFuture.allOf(completableFutures).join();
	}
}