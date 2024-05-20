package com.jun.plugin.security.util.secure;

import com.jun.plugin.security.util.secure.sm3.SM3Digest;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class SM3Hash {

    /**
     * 原始值
     */
    private String source;

    /**
     * 盐值
     */
    private String salt = "";

    /**
     * 迭代次数（默认1次）
     */
    private int iterations = 1;

    public String getSource() {
        return this.source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSalt() {
        return this.salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public int getIterations() {
        return this.iterations;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public SM3Hash(String source, String salt, int iterations) {
        this.source = source;
        this.salt = salt;
        this.iterations = iterations;
    }

    public SM3Hash(String source, String salt) {
        this.source = source;
        this.salt = salt;
    }

    public SM3Hash(String source) {
        this.source = source;
    }

    public SM3Hash(String source, int iterations) {
        this.source = source;
        this.iterations = iterations;
    }

    /**
     * 返回小写SM3 hash串
     *
     * @param source 原始值字符串
     * @return hash后的字节数组
     */
    private byte[] SM3Encode(String source, String salt) {
        byte[] hashedBytes = new byte[32];
        try {
            byte[] msgBytes = (source + salt).getBytes(StandardCharsets.UTF_8);
            SM3Digest sm3Digest = new SM3Digest();
            sm3Digest.update(msgBytes, 0, msgBytes.length);
            sm3Digest.doFinal(hashedBytes, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hashedBytes;
    }


    /**
     * 返回小写SM3 hash串
     *
     * @param msgBytes 原始值-字节数组
     * @return hash后的字节数组
     */
    private byte[] SM3Encode(byte[] msgBytes) {
        byte[] hashedBytes = new byte[32];
        try {
            SM3Digest sm3Digest = new SM3Digest();
            sm3Digest.update(msgBytes, 0, msgBytes.length);
            sm3Digest.doFinal(hashedBytes, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hashedBytes;
    }


    /**
     * 返回小写SM3 hash串
     *
     * @param source 原始值字符串
     * @return hash后的字节数组
     */
    private byte[] SM3Encode(String source, String salt, int hashIterations) {
        byte[] hashedBytes;
        // 最少迭代一次
        if (hashIterations < 1) {
            hashIterations = 1;
        }
        hashedBytes = this.SM3Encode(source, salt);
        for (int i = 0; i < hashIterations - 1; i++) {
            hashedBytes = this.SM3Encode(hashedBytes);
        }
        return hashedBytes;
    }


    /**
     * 将将byte[]转为16进制字符串
     *
     * @return 16进制字符串
     */
    public String toHex() {
        byte[] resultBytes = this.SM3Encode(this.source, this.salt, this.iterations);
        return HexUtil.bytesToHex(resultBytes);
    }


    /**
     * 将将byte[]转为Base64字符串
     *
     * @return Base64字符串
     */
    public String toBase64() {
        byte[] resultBytes = this.SM3Encode(this.source, this.salt, this.iterations);
        return Base64.getEncoder().encodeToString(resultBytes);
    }


    /**
     * 将将byte[]转为16进制字符串
     *
     * @return 16进制字符串
     */
    @Override
    public String toString() {
        return this.toHex();
    }


    public static void main(String[] args) {
        System.out.println(new SM3Hash("123456", "323@#@$1234da", 1).toHex());
        System.out.println(new SM3Hash("123456", "323@#@$1234da").toHex());
        System.out.println(new SM3Hash("123456").toHex());

        System.out.println(new SM3Hash("123456", "323@#@$1234da", 2).toHex());

        System.out.println(new SM3Hash("123456", "323@#@$1234da", 4).toHex());

        System.out.println(new SM3Hash("123456", "323@#@$1234da").toBase64());
    }

}
