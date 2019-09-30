package com.lq.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * Description: UUID  内存中无例
 *
 * @author: liqian
 * @Date: 2019-09-27
 * @Time: 17:18
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UUIDUtil {

    public static String uuid() {
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString();
        return str.replaceAll("-", "");
    }

    public static String[] uuids(int number) {
        if (number < 1) {
            return null;
        }
        String[] ss = new String[number];
        for (int i = 0; i < number; i++) {
            ss[i] = uuid();
        }
        return ss;
    }

    public static String salt() {
        String uuid = uuid();
        String longSn = new BigInteger(uuid, 16).toString();
        return longSn.substring(0, 12);
    }

    public static String salt(int num) {
        String uuid = uuid();
        String longSn = new BigInteger(uuid, 16).toString();
        return longSn.substring(0, num);
    }

}
