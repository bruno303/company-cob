package com.companycob.utils.bigdecimal;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.Test;

public class BigDecimalUtilsTest {

	@Test
	public void testBigDecimalLesserThanZero() {
		final BigDecimal num = BigDecimal.valueOf(-20);
		assertThat(BigDecimalUtils.lessThanZero(num)).isTrue();
		assertThat(BigDecimalUtils.greaterThanZero(num)).isFalse();
	}

	@Test
	public void testBigDecimalGreaterThanZero() {
		final BigDecimal num = BigDecimal.valueOf(20);
		assertThat(BigDecimalUtils.greaterThanZero(num)).isTrue();
		assertThat(BigDecimalUtils.lessThanZero(num)).isFalse();
	}

	@Test
	public void testBigDecimalEqualsZero() {
		final BigDecimal num = BigDecimal.ZERO;
		assertThat(BigDecimalUtils.greaterThanZero(num)).isFalse();
		assertThat(BigDecimalUtils.lessThanZero(num)).isFalse();
		assertThat(BigDecimalUtils.equalsZero(num)).isTrue();
	}

	@Test
	public void testBigDecimalCompareTwoEqualNumbers() {
		final BigDecimal num1 = BigDecimal.valueOf(20);
		final BigDecimal num2 = BigDecimal.valueOf(20);

		assertThat(BigDecimalUtils.equals(num1, num2)).isTrue();
	}

	@Test
	public void testBigDecimalCompareTwoDifferentNumbers() {
		final BigDecimal num1 = BigDecimal.valueOf(20);
		final BigDecimal num2 = BigDecimal.valueOf(25);

		assertThat(BigDecimalUtils.equals(num1, num2)).isFalse();
	}

	@Test
	public void testBigDecimalCompareTwoZerosWithEquals() {
		final BigDecimal num1 = BigDecimal.valueOf(0);
		final BigDecimal num2 = BigDecimal.valueOf(0);

		assertThat(BigDecimalUtils.equals(num1, num2)).isTrue();
	}
}
