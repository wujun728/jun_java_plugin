package com.jun.plugin.common.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AESUtil {

    private static final String AES_ALGORITHM = "AES/ECB/PKCS5Padding";

    /**
     * 加密字符串
     *
     * @param plaintext 明文字符串
     * @param key       密钥
     * @return 加密后的字符串
     * @throws Exception
     */
    public static String encrypt(String plaintext, String key) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /**
     * 解密字符串
     *
     * @param ciphertext 加密后的字符串
     * @param key        密钥
     * @return 解密后的字符串
     * @throws Exception
     */
    public static String decrypt(String ciphertext, String key) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(ciphertext));
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    public static void main(String[] args) throws Exception {
        String plaintext = "hello world";
        String key = "0123456789abcdef";

        String ciphertext = AESUtil.encrypt(plaintext, key);
        System.out.println("加密后的字符串：" + ciphertext);

        String decryptedText = AESUtil.decrypt(ciphertext, key);
        System.out.println("解密后的字符串：" + decryptedText);
    }



}


