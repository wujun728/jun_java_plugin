package com.jun.plugin.security.util.secure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * AESUtil工具类，使用Builder模式构造
 *
 * @version  2021-09-09-9:43
 **/
public class AESUtil {
    final static Logger logger = LoggerFactory.getLogger(AESUtil.class);

    /**
     * 算法/加密模式/填充方式
     */
    private final static String ALGORITHMSTR = "AES/CBC/PKCS5Padding";

    /**
     * 密钥key（可以16或32字节）（128位密钥/256位密钥，设置256位密钥更难破解）
     */
    private String secretKey;

    /**
     * 偏移量（16字节）
     */
    private String ivParameter;

    /**
     * 是否使用base64编码（默认false，默认使用hex）
     */
    private boolean ifBase64;


    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getIvParameter() {
        return ivParameter;
    }

    public void setIvParameter(String ivParameter) {
        this.ivParameter = ivParameter;
    }

    public boolean isIfBase64() {
        return ifBase64;
    }

    public void setIfBase64(boolean ifBase64) {
        this.ifBase64 = ifBase64;
    }


    private AESUtil(AESUtilBuilder builder) {
        this.secretKey = builder.secretKey;
        this.ivParameter = builder.ivParameter;
        this.ifBase64 = builder.ifBase64;
    }


    public static AESUtilBuilder builder() {
        return new AESUtilBuilder();
    }



    /**
     * 2020-08-13-14:07--liuxingyu01
     * AES加密
     *
     * @param content 要加密的字符串
     * @return 430301102072 / d086af4d68888097504e536e5cfe351c / Hex字符串展示
     */
    public String encrypt(String content) {
        try {
            byte[] raw = secretKey.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
            // 使用CBC模式，需要一个向量iv，可增加加密算法的强度
            IvParameterSpec ips = new IvParameterSpec(ivParameter.getBytes(StandardCharsets.UTF_8));
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ips);
            byte[] encrypted = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));

            if (ifBase64) { // 此处使用BASE64做编码
                return Base64.getEncoder().encodeToString(encrypted);
            } else { // 此处使用Hex做编码
                return HexUtil.bytesToHex(encrypted);
            }
        } catch (Exception e) {
            logger.error("AESUtils -- encrypt -- Exception=", e);
            return null;
        }
    }


    /**
     * AES解密
     *
     * @param content 要解密的字符串
     * @return 解密后的结果
     */
    public String decrypt(String content) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
            byte[] raw = secretKey.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            // 使用CBC模式，需要一个向量iv，可增加加密算法的强度
            IvParameterSpec ips = new IvParameterSpec(ivParameter.getBytes(StandardCharsets.UTF_8));
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, ips);
            byte[] result;
            if (ifBase64) { // 此处使用BASE64做编码
                result = cipher.doFinal(Base64.getDecoder().decode(content));
            } else { // 此处使用Hex做编码
                result = cipher.doFinal(HexUtil.hexToBytes(content));
            }
            return new String(result, StandardCharsets.UTF_8);
        } catch (Exception e) {
            logger.error("AESUtils -- decrypt -- Exception=", e);
            return null;
        }
    }


    /**
     * AESUtilsBuilder
     */
    public static final class AESUtilBuilder {
        private String secretKey = "1G78Av#yej%WZJ3uiSZRz9oy%UAv4!EG";
        private String ivParameter = "E%BJuDUTvXfwSuGQ";
        private boolean ifBase64 = false;

        public AESUtilBuilder() {
            this.secretKey = secretKey;
            this.ivParameter = ivParameter;
            this.ifBase64 = ifBase64;
        }

        public AESUtilBuilder secretKey(String secretKey) {
            this.secretKey = secretKey;
            return this;
        }

        public AESUtilBuilder ivParameter(String ivParameter) {
            this.ivParameter = ivParameter;
            return this;
        }

        public AESUtilBuilder ifBase64(boolean ifBase64) {
            this.ifBase64 = ifBase64;
            return this;
        }

        public AESUtil build() {
            AESUtil aesUtil = new AESUtil(this);
            return aesUtil;
        }
    }


    /**
     * 测试
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        // 原文:
        String message = "Helloworld!";
        System.out.println("Message: " + message);

        // 使用方法
        AESUtil aesUtils = AESUtil.builder().secretKey("1G78Av#yej%WZJ3uiSZRz9oy%UAv4AAA").ivParameter("E%BAAAUTvXfwSuGQ").build();

        // 加密:
        String encrypted = aesUtils.encrypt(message);
        System.out.println("加密: " + encrypted);

        // 解密:
        String decrypted = aesUtils.decrypt(encrypted);
        System.out.println("解密: " + decrypted);
    }


}
