package com.companycob.utils.bigdecimal;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class BigDecimalUtilsTest {

    @Test
    public void testBigDecimalLesserThanZero() {
        BigDecimal num = BigDecimal.valueOf(-20);
        Assert.assertTrue(BigDecimalUtils.lessThanZero(num));
        Assert.assertFalse(BigDecimalUtils.greaterThanZero(num));
    }

    @Test
    public void testBigDecimalGreaterThanZero() {
        BigDecimal num = BigDecimal.valueOf(20);
        Assert.assertTrue(BigDecimalUtils.greaterThanZero(num));
        Assert.assertFalse(BigDecimalUtils.lessThanZero(num));
    }

    @Test
    public void testBigDecimalEqualsZero() {
        BigDecimal num = BigDecimal.ZERO;
        Assert.assertFalse(BigDecimalUtils.greaterThanZero(num));
        Assert.assertFalse(BigDecimalUtils.lessThanZero(num));
        Assert.assertTrue(BigDecimalUtils.equalsZero(num));
    }

    @Test
    public void testBigDecimalCompareTwoEqualNumbers() {
        BigDecimal num1 = BigDecimal.valueOf(20);
        BigDecimal num2 = BigDecimal.valueOf(20);

        Assert.assertTrue(BigDecimalUtils.equals(num1, num2));
    }

    @Test
    public void testBigDecimalCompareTwoDifferentNumbers() {
        BigDecimal num1 = BigDecimal.valueOf(20);
        BigDecimal num2 = BigDecimal.valueOf(25);

        Assert.assertFalse(BigDecimalUtils.equals(num1, num2));
    }

    @Test
    public void testBigDecimalCompareTwoZerosWithEquals() {
        BigDecimal num1 = BigDecimal.valueOf(0);
        BigDecimal num2 = BigDecimal.valueOf(0);

        Assert.assertTrue(BigDecimalUtils.equals(num1, num2));
    }
}
