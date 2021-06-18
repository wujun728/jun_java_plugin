package com.jun.plugin.dfs.utils;

import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;

@Slf4j
public abstract class MD5Utils {

    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * 加密
     *
     * @param seq
     * @return
     */
    public static String md5(String seq) {
        String digest;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] md = md5.digest(seq.getBytes());
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;
            for (byte byte0 : md) {
                str[k++] = HEX_DIGITS[byte0 >>> 4 & 0xf];
                str[k++] = HEX_DIGITS[byte0 & 0xf];
            }
            digest = new String(str);
        } catch (Exception e) {
            log.error("md5 digest error", e);
            digest = null;
        }

        return digest;
    }

    public static void main(String[] args) {
        String pass = "123456";
        System.out.println(MD5Utils.md5(pass));
    }
}
