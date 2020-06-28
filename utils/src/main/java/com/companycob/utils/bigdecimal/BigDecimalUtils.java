package com.companycob.utils.bigdecimal;

import java.math.BigDecimal;

public final class BigDecimalUtils {

    public static boolean lessThanZero(BigDecimal value) {
        return value.compareTo(BigDecimal.ZERO) < 0;
    }

    public static boolean greatherThanZero(BigDecimal value) {
        return value.compareTo(BigDecimal.ZERO) > 0;
    }

    public static boolean equalsZero(BigDecimal value) {
        return value.compareTo(BigDecimal.ZERO) == 0;
    }

    public static boolean equalsTo(BigDecimal value, BigDecimal value2) {
        return value.compareTo(value2) == 0;
    }
}
