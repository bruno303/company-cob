package com.companycob.web.utils.config.security;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class InMemoryWebAppSecurityConfig extends WebSecurityConfigurerAdapter {

	private static final Logger LOGGER = LoggerFactory.getLogger(InMemoryWebAppSecurityConfig.class);
	private static final String AUTHORITY = "ADMIN";

	private static final String[] ACTUATOR_AUTH_URLS = { "/actuator/**", "/api/**" };
	private static final String[] ACTUATOR_FREE_URLS = { "/actuator", "/actuator/health", "/helloteste" };

	@Value("${spring.boot.admin.client.instance.metadata.user.name:}")
	private String user;

	@Value("${spring.boot.admin.client.instance.metadata.user.password:}")
	private String password;

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	// @formatter:off
	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers(ACTUATOR_FREE_URLS).permitAll()
			.antMatchers(ACTUATOR_AUTH_URLS).hasAuthority(AUTHORITY)
			.anyRequest().denyAll()
			.and().csrf().disable()
			.httpBasic();
	}
	// @formatter:on

	@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
		if (StringUtils.isNoneEmpty(user, password)) {
			LOGGER.info("Configuring in memory authentication with user '{}'", user);
			auth.inMemoryAuthentication().withUser(user).password(getPasswordEncoder().encode(password))
					.authorities(AUTHORITY);
		}
	}
}
