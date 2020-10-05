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

	@Value("${application.security.user:}")
	private String user;

	@Value("${application.security.password:}")
	private String password;

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/actuator/**").permitAll()
				.antMatchers("/api/**").hasAuthority(AUTHORITY)
			.and()
			.csrf().disable()
			.httpBasic();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		if (StringUtils.isNoneEmpty(user, password)) {
			LOGGER.info("Configuring in memory authentication with user '{}'", user);
	    	auth
	          .inMemoryAuthentication()
	          .withUser(user)
	          .password(getPasswordEncoder().encode(password))
	          .authorities(AUTHORITY);
		}
	}
}
