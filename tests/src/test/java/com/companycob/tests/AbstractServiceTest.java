package com.companycob.tests;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.companycob.tests.config.AppConfig;

@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = { "test" })
@ContextConfiguration(classes = { AppConfig.class })
public abstract class AbstractServiceTest {

}
