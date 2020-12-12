package com.companycob.utils.bigdecimal;

import java.math.BigDecimal;

public final class BigDecimalUtils {

    private BigDecimalUtils() { }

    public static boolean lessThanZero(BigDecimal value) {
        return value.compareTo(BigDecimal.ZERO) < 0;
    }

    public static boolean greaterThanZero(BigDecimal value) {
        return value.compareTo(BigDecimal.ZERO) > 0;
    }

    public static boolean equalsZero(BigDecimal value) {
        return value.compareTo(BigDecimal.ZERO) == 0;
    }

    public static boolean equals(BigDecimal value, BigDecimal value2) {
        return value.compareTo(value2) == 0;
    }
}
