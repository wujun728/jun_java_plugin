package com.jun.plugin.security.util.secure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;


/**
 * MD5-Hsah算法加密
 *
 * @version 2020-05-28-23:34
 **/
public class MD5Hash {
    final static Logger log = LoggerFactory.getLogger(MD5Hash.class);

    /**
     * 原始值
     */
    private String source;

    /**
     * 颜值
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

    public MD5Hash(String source, String salt, int iterations) {
        this.source = source;
        this.salt = salt;
        this.iterations = iterations;
    }

    public MD5Hash(String source, String salt) {
        this.source = source;
        this.salt = salt;
    }

    public MD5Hash(String source) {
        this.source = source;
    }

    public MD5Hash(String source, int iterations) {
        this.source = source;
        this.iterations = iterations;
    }


    /**
     * 返回小写MD5
     *
     * @param source 原始值字符串
     * @return hash后的字节数组
     */
    private byte[] MD5Encode(String source, String salt, int hashIterations) {
        MessageDigest messageDigest = null;
        byte[] hashedBytes = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            // 先加盐
            if (salt != null) {
                messageDigest.reset();
                messageDigest.update(salt.getBytes(StandardCharsets.UTF_8));
            }
            // 再放需要被hash的数据
            hashedBytes = messageDigest.digest(source.getBytes(StandardCharsets.UTF_8));
            // 最少迭代一次
            if (hashIterations < 1) {
                hashIterations = 1;
            }
            // 迭代继续hash
            for (int i = 0; i < hashIterations - 1; i++) {
                messageDigest.reset();
                hashedBytes = messageDigest.digest(hashedBytes);
            }
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("MD5Utils -- MD5Encode -- Exception= {e}", e);
            }
        }
        return hashedBytes;
    }


    /**
     * 将将byte[]转为16进制字符串
     *
     * @return 16进制字符串
     */
    public String toHex() {
        byte[] resultBytes = this.MD5Encode(this.source, this.salt, this.iterations);
        return HexUtil.bytesToHex(resultBytes);
    }


    /**
     * 将将byte[]转为Base64字符串
     *
     * @return Base64字符串
     */
    public String toBase64() {
        byte[] resultBytes = this.MD5Encode(this.source, this.salt, this.iterations);
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
        System.out.println(new MD5Hash("123456", "323@#@$1234da", 1).toHex());
        System.out.println(new MD5Hash("123456", "323@#@$1234da").toHex());
        System.out.println(new MD5Hash("123456").toHex());

        System.out.println(new MD5Hash("123456", "323@#@$1234da", 2).toHex());

        System.out.println(new MD5Hash("123456", "323@#@$1234da", 3).toHex());

        System.out.println(new MD5Hash("123456", "323@#@$1234da", 3).toBase64());
    }
}
