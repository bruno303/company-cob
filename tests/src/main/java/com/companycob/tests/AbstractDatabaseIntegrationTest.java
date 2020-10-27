package com.companycob.tests;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.ClassRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.companycob.tests.database.testcontainers.CompanyCobPostgresContainer;
import com.companycob.tests.exception.SetupDatabaseException;

import liquibase.Liquibase;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;

@Testcontainers
public abstract class AbstractDatabaseIntegrationTest extends AbstractTest {

	private static final String CHANGE_LOG = "db/db.changelog-master.xml";

	@ClassRule
	@Container
	public static final PostgreSQLContainer<CompanyCobPostgresContainer> postgreSQLContainer = CompanyCobPostgresContainer
			.getInstance();

	@Autowired
	private DataSource dataSource;

	@Before
	public void setUpDatabase() {

		try {

			final var database = DatabaseFactory.getInstance()
					.findCorrectDatabaseImplementation(new JdbcConnection(dataSource.getConnection()));

			try (final var liquibase = new Liquibase(CHANGE_LOG, new ClassLoaderResourceAccessor(), database)) {
				liquibase.update("test");
			}

		} catch (final Exception ex) {
			throw new SetupDatabaseException("Error in database configuration with liquibase", ex);
		}
	}
}
