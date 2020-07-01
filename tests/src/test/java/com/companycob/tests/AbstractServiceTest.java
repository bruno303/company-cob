package com.companycob.tests;

import com.companycob.tests.generator.BankGenerator;
import com.companycob.tests.generator.ContractGenerator;
import com.companycob.tests.generator.QuotaGenerator;
import com.companycob.utils.bigdecimal.BigDecimalUtils;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.companycob.tests.config.AppConfig;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = { "test" })
@ContextConfiguration(classes = { AppConfig.class })
public abstract class AbstractServiceTest {

    @Autowired
    protected BankGenerator bankGenerator;

    @Autowired
    protected QuotaGenerator quotaGenerator;

    @Autowired
    protected ContractGenerator contractGenerator;

    public static void assertEqualsBigDecimal(BigDecimal expected, BigDecimal actual) {
        if (!BigDecimalUtils.equals(expected, actual)) {
            Assert.fail(String.format("Expected [%.3f] but received [%.3f]", expected, actual));
        }
    }
}
