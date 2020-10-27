package com.companycob.tests.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = { "com.companycob" })
@EntityScan(basePackages = { "com.companycob.infrastructure" })
@EnableJpaRepositories(basePackages = { "com.companycob.infrastructure" })
@EnableAutoConfiguration
@Profile("test")
public class AppConfig {

}
