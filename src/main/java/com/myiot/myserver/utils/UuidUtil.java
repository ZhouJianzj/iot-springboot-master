package com.myiot.myserver.utils;

import java.math.BigInteger;
import java.util.UUID;

/**
 * @author origin
 */
public final class UuidUtil {

    public static String createDecimalId() {
        Integer orderId=UUID.randomUUID().toString().hashCode();
        orderId = orderId < 0 ? -orderId : orderId;
        return String.valueOf(orderId);
    }

    public static String getUniqueId() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    private UuidUtil() {

    }

}
