package com.companycob.web.config.security;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;

import com.companycob.tests.AbstractUnitTest;

import io.restassured.RestAssured;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class WebAppSecurityConfigTest extends AbstractUnitTest {

	private static final int STATUS_OK = 200;
	private static final int STATUS_UNAUTHORIZED = 401;

	@LocalServerPort
	private int localPort;

	@Override
	@Before
	public void setUp() {
		RestAssured.port = localPort;
	}

	// @formatter:off
	@Test
	public void testActuatorRouteIsFree() {
		RestAssured
			.given()
			.when()
			.get("/actuator")
			.then()
			.log().status()
			.assertThat().statusCode(Matchers.is(STATUS_OK));
	}
	// @formatter:on

	// @formatter:off
	@Test
	public void testActuatorRouteIsFreeEvenWithBasicAuth() {
		RestAssured
		.given()
		.auth()
		.preemptive()
		.basic("companycob", "companycob")
		.when()
		.get("/actuator")
		.then()
		.log().status()
		.assertThat().statusCode(Matchers.is(STATUS_OK));
	}
	// @formatter:on

	// @formatter:off
	@Test
	public void testActuatorRouteIsUnauthorizedWithWrongAuth() {
		RestAssured
			.given()
			.auth()
			.preemptive()
			.basic("xxx", "yyy")
			.when()
			.get("/actuator")
			.then()
			.log().status()
			.assertThat().statusCode(Matchers.is(STATUS_UNAUTHORIZED));
	}
	// @formatter:on

	// @formatter:off
	@Test
	public void testActuatorHealthRouteIsFree() {
		RestAssured
			.given()
			.when()
			.get("/actuator/health")
			.then()
			.log().status()
			.assertThat().statusCode(Matchers.is(STATUS_OK));
	}
	// @formatter:on

	// @formatter:off
	@Test
	public void testActuatorBeansRouteRequiresAuth() {
		RestAssured
			.given()
			.when()
			.get("/actuator/beans")
			.then()
			.log().status()
			.assertThat().statusCode(Matchers.is(STATUS_UNAUTHORIZED));
	}
	// @formatter:on

	// @formatter:off
	@Test
	public void testActuatorBeansRouteWithCorrectAuthIsFree() {
		RestAssured
			.given()
			.auth()
			.preemptive()
			.basic("companycob", "companycob")
			.when()
			.get("/actuator/beans")
			.then()
			.log().status()
			.assertThat().statusCode(Matchers.is(STATUS_OK));
	}
	// @formatter:on

	// @formatter:off
	@Test
	public void testActuatorBeansRouteWithWrongAuthIsUnauthorized() {
		RestAssured
			.given()
			.auth()
			.preemptive()
			.basic("xxx", "yyy")
			.when()
			.get("/actuator/beans")
			.then()
			.log().status()
			.assertThat().statusCode(Matchers.is(STATUS_UNAUTHORIZED));
	}
	// @formatter:on
}
