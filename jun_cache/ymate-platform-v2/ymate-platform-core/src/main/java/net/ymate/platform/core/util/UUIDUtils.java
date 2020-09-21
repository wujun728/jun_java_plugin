/*
 * Copyright 2007-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.ymate.platform.core.util;

import java.util.Random;
import java.util.UUID;

/**
 * UUID生成器
 *
 * @author 刘镇 (suninformation@163.com) on 2010-10-20 下午02:02:40
 * @version 1.0
 */
public class UUIDUtils {

    private final static String __randChars = "0123456789abcdefghigklmnopqrstuvtxyzABCDEFGHIGKLMNOPQRSTUVWXYZ";

    private final static String __chars64 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789^~abcdefghijklmnopqrstuvwxyz";

    private final static Random __random;

    static {
        __random = new Random(System.currentTimeMillis());
    }

    /**
     * @param o 预加密字符串
     * @return 返回唯一的16位字符串(基于32位当前时间和32位对象的identityHashCode和32位随机数)
     */
    public static String generateCharUUID(Object o) {
        long id1 = System.currentTimeMillis() & 0xFFFFFFFFL;
        long id2 = System.identityHashCode(o);
        long id3 = randomLong(-0x80000000L, 0x80000000L) & 0xFFFFFFFFL;
        id1 <<= 16;
        id1 += (id2 & 0xFFFF0000L) >> 16;
        id3 += (id2 & 0x0000FFFFL) << 32;
        return __convert(id1, 6, __chars64) + __convert(id3, 6, __chars64).replaceAll(" ", "o");
    }

    public static String generateNumberUUID(Object o) {
        long id1 = System.currentTimeMillis() & 0xFFFFFFFFL;
        long id2 = System.identityHashCode(o);
        long id3 = randomLong(-0x80000000L, 0x80000000L) & 0xFFFFFFFFL;
        id1 <<= 16;
        id1 += (id2 & 0xFFFF0000L) >> 16;
        id3 += (id2 & 0x0000FFFFL) << 32;
        return "" + id1 + id3;
    }

    public static String generatePrefixHostUUID(Object o) {
        long id1 = System.currentTimeMillis() & 0xFFFFFFFFL;
        long id2 = System.identityHashCode(o);
        long id3 = randomLong(-0x80000000L, 0x80000000L) & 0xFFFFFFFFL;
        id1 <<= 16;
        id1 += (id2 & 0xFFFF0000L) >> 16;
        id3 += (id2 & 0x0000FFFFL) << 32;
        return NetworkUtils.IP.getHostName() + "@" + id1 + id3;
    }

    /**
     * @return 返回10个随机字符(基于当前时间和一个随机字符串)
     */
    public static String generateRandomUUID() {
        long id1 = System.currentTimeMillis() & 0x3FFFFFFFL;
        long id3 = randomLong(-0x80000000L, 0x80000000L) & 0x3FFFFFFFL;
        return __convert(id1, 6, __chars64) + __convert(id3, 6, __chars64).replaceAll(" ", "o");
    }

    /**
     * @return 返回采用JDK自身UUID生成器生成主键并替换'-'字符
     */
    public static String UUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * Converts number to string
     *
     * @param x value to convert
     * @param n conversion base
     * @param d string with characters for conversion.
     * @return converted number as string
     */
    private static String __convert(long x, int n, String d) {
        if (x == 0) {
            return "0";
        }
        String r = "";
        int m = 1 << n;
        m--;
        while (x != 0) {
            r = d.charAt((int) (x & m)) + r;
            x = x >>> n;
        }
        return r;
    }

    /**
     * @param length    长度
     * @param isOnlyNum 是否仅使用数字
     * @return 生成随机字符串
     */
    public static String randomStr(int length, boolean isOnlyNum) {
        int size = isOnlyNum ? 10 : 62;
        StringBuilder _hash = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            _hash.append(__randChars.charAt(__random.nextInt(size)));
        }
        return _hash.toString();
    }

    /**
     * Generates pseudo-random long from specific range. Generated number is
     * great or equals to min parameter value and less then max parameter value.
     *
     * @param min lower (inclusive) boundary
     * @param max higher (exclusive) boundary
     * @return pseudo-random value
     */
    public static long randomLong(long min, long max) {
        return min + (long) (Math.random() * (max - min));
    }

    /**
     * Generates pseudo-random integer from specific range. Generated number is
     * great or equals to min parameter value and less then max parameter value.
     *
     * @param min lower (inclusive) boundary
     * @param max higher (exclusive) boundary
     * @return pseudo-random value
     */
    public static int randomInt(int min, int max) {
        return min + (int) (Math.random() * (max - min));
    }
}
