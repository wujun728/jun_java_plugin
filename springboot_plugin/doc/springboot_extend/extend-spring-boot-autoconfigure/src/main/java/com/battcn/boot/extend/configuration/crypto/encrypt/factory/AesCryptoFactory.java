package com.battcn.boot.extend.configuration.crypto.encrypt.factory;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * 高级加密标准，是下一代的加密算法标准，速度快，安全级别高；
 * AES是一个使用128为分组块的分组加密算法，分组块和128、192或256位的密钥一起作为输入，
 * 对4×4的字节数组上进行操作。众所周之AES是种十分高效的算法，尤其在8位架构中，这源于它面向字节的设计。
 * AES 适用于8位的小型单片机或者普通的32位微处理器,并且适合用专门的硬件实现，硬件实现能够使其吞吐量（每秒可以到达的加密/解密bit数）
 * 达到十亿量级
 *
 * @author Levin
 * @since 2018/12/27 0027
 */
@Slf4j
public class AesCryptoFactory implements CryptoFactory {

    /**
     * 加密
     *
     * @param key     密钥
     * @param content 需要加密的内容
     */
    @Override
    public byte[] encrypt(String key, byte[] content) {
        try {
            return encryptCipher(key).doFinal(content);
        } catch (Exception e) {
            log.error("[AES 加密失败]", e);
        }
        return null;
    }


    /**
     * 解密
     *
     * @param key     密钥
     * @param content 需要解密的内容
     */
    @Override
    public byte[] decrypt(String key, byte[] content) {
        try {
            return decryptCipher(key).doFinal(content);
        } catch (IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
            log.error("[AES 解密失败]", e);

        }
        return null;
    }


    private volatile Cipher encryptCipher = null;
    private volatile Cipher decryptCipher = null;

    private static final Charset DEFAULT_KEY_CHARSET = Charset.forName("UTF-8");
    private static final String AES = "AES";
    private static final String ALGORITHM = "SHA1PRNG";

    private Cipher encryptCipher(String key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        if (encryptCipher == null) {
            synchronized (AesCryptoFactory.class) {
                if (encryptCipher == null) {
                    KeyGenerator generator = KeyGenerator.getInstance(AES);
                    SecureRandom random = SecureRandom.getInstance(ALGORITHM);
                    random.setSeed(key.getBytes(DEFAULT_KEY_CHARSET));
                    generator.init(128, random);
                    SecretKey secretKey = generator.generateKey();
                    byte[] enCodeFormat = secretKey.getEncoded();
                    SecretKeySpec spec = new SecretKeySpec(enCodeFormat, AES);
                    Cipher cipher = Cipher.getInstance(AES);
                    cipher.init(Cipher.ENCRYPT_MODE, spec);
                    this.encryptCipher = cipher;
                }
            }
        }
        return encryptCipher;
    }

    private Cipher decryptCipher(String key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        if (decryptCipher == null) {
            synchronized (AesCryptoFactory.class) {
                if (decryptCipher == null) {
                    KeyGenerator generator = KeyGenerator.getInstance(AES);
                    SecureRandom random = SecureRandom.getInstance(ALGORITHM);
                    random.setSeed(key.getBytes(DEFAULT_KEY_CHARSET));
                    generator.init(128, random);
                    SecretKey secretKey = generator.generateKey();
                    byte[] enCodeFormat = secretKey.getEncoded();
                    SecretKeySpec spec = new SecretKeySpec(enCodeFormat, AES);
                    Cipher cipher = Cipher.getInstance(AES);
                    cipher.init(Cipher.DECRYPT_MODE, spec);
                    this.decryptCipher = cipher;
                }
            }
        }
        return decryptCipher;
    }


}
