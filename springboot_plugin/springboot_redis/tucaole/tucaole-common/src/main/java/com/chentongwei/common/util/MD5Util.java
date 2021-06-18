package com.chentongwei.common.util;

import java.security.MessageDigest;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: MD5工具类
 */
public final class MD5Util {

    private MD5Util() {}

    private static final String HEX_DIGITS[] = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    /**
     * byte数组 转16进制
     *
     * @param b：字节数组
     * @return java.lang.String
     */
    private static final String byteArrayToHexString(byte b[]) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    /**
     *  byte 转16进制
     *
     * @param b 字节
     * @return java.lang.String
     */
    private static final String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n += 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return HEX_DIGITS[d1] + HEX_DIGITS[d2];
    }

    /**
     *  MD5加密
     *
     * @param origin 需要转换的源字符串
     * @param charsetname 字符集
     * @return java.lang.String
     */
    private static final String encode(String origin, String charsetname) {

        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charsetname == null || "".equals(charsetname)) {
                resultString = byteArrayToHexString(md.digest(resultString
                        .getBytes()));
            } else {
                resultString = byteArrayToHexString(md.digest(resultString
                        .getBytes(charsetname)));
            }
        } catch (Exception exception) {}
        return resultString;
    }

    /**
     * 最终加密方法
     *
     * @param origin：源字符串
     * @return
     */
    public static final String encode(String origin) {
        //加密32次
        String encodeStr = origin;
        for (int i = 0; i < 32; i ++) {
            encodeStr = encode(encodeStr, "utf-8");
        }
        return encodeStr;
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        System.out.println(encode("1234"));
        System.out.println(System.currentTimeMillis() - start);
    }
}
