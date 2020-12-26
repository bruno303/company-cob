package com.companycob.config.security;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import de.codecentric.boot.admin.server.config.AdminServerProperties;

@Configuration(proxyBeanMethods = false)
public class SpringBootAdminSecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String ROLE = "USER";

	@Value("${spring.boot.admin.login.username}")
	private String username;

	@Value("${spring.boot.admin.login.password}")
	private String password;

	private final AdminServerProperties adminServer;
	private final PasswordEncoder passwordEncoder;

	public SpringBootAdminSecurityConfig(final AdminServerProperties adminServer,
			final PasswordEncoder passwordEncoder) {
		this.adminServer = adminServer;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		final SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
		successHandler.setTargetUrlParameter("redirectTo");
		successHandler.setDefaultTargetUrl(adminServer.path("/"));

		http.authorizeRequests(authorizeRequests -> authorizeRequests.antMatchers(adminServer.path("/assets/**"))
				.permitAll().antMatchers(adminServer.path("/login")).permitAll().anyRequest().authenticated())
				.formLogin(formLogin -> formLogin.loginPage(adminServer.path("/login")).successHandler(successHandler)
						.and())
				.logout(logout -> logout.logoutUrl(adminServer.path("/logout"))).httpBasic(Customizer.withDefaults())
				.csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
						.ignoringRequestMatchers(
								new AntPathRequestMatcher(adminServer.path("/instances"), HttpMethod.POST.toString()),
								new AntPathRequestMatcher(adminServer.path("/instances/*"),
										HttpMethod.DELETE.toString()),
								new AntPathRequestMatcher(adminServer.path("/actuator/**"))))
				.rememberMe(rememberMe -> rememberMe.key(UUID.randomUUID().toString()).tokenValiditySeconds(1209600));
	}

	@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser(username).password(passwordEncoder.encode(password)).roles(ROLE);
	}

}
