package com.companycob.tests;

import com.companycob.tests.config.AppConfig;
import com.companycob.tests.fixture.Fixture;
import com.companycob.tests.fixture.generator.BankGenerator;
import com.companycob.tests.fixture.generator.ContractGenerator;
import com.companycob.tests.fixture.generator.QuotaGenerator;
import com.companycob.tests.testcontainers.CompanyCobRedisContainer;
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
	public static final CompanyCobRedisContainer redis = CompanyCobRedisContainer.getInstance();

	@Before
	public void setUp() {
		fixture.clearDatabase();
	}
}