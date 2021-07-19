package com.battcn.boot.extend.configuration.crypto.encrypt.factory;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

/**
 * 数据加密标准，速度较快，适用于加密大量数据的场合。
 *
 * @author Levin
 * @since 2018/12/27 0027
 */
@Slf4j
public class DesCryptoFactory implements CryptoFactory {

    /**
     * 加密
     *
     * @param key     密钥
     * @param content 需要加密的内容
     * @return 加密结果
     */
    @Override
    public byte[] encrypt(String key, byte[] content) {
        try {
            return encryptCipher(key).doFinal(content);
        } catch (IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException e) {
            log.error("[DES 加密失败", e);
        } catch (InvalidKeyException e) {
            log.error("[DES 加密失败，请检查 key 的长度是否符合8的倍数]", e);
        }
        return null;
    }

    /**
     * 解密
     *
     * @param key     密钥
     * @param content 需要解密的内容
     * @return 解密结果
     */
    @Override
    public byte[] decrypt(String key, byte[] content) throws RuntimeException {
        try {
            return decryptCipher(key).doFinal(content);
        } catch (IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException e) {
            log.error("[DES 解密失败", e);
        } catch (InvalidKeyException e) {
            log.error("[DES 解密失败，请检查 key 的长度是否符合8的倍数]", e);
        }
        return null;
    }

    private volatile Cipher encryptCipher = null;
    private volatile Cipher decryptCipher = null;
    private static final Charset DEFAULT_KEY_CHARSET = Charset.forName("UTF-8");
    private static final String DES = "DES";

    private Cipher encryptCipher(String key) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException {
        if (encryptCipher == null) {
            synchronized (DesCryptoFactory.class) {
                if (encryptCipher == null) {
                    SecureRandom random = new SecureRandom();
                    DESKeySpec desKey = new DESKeySpec(key.getBytes(DEFAULT_KEY_CHARSET));
                    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
                    Cipher cipher = Cipher.getInstance(DES);
                    cipher.init(Cipher.ENCRYPT_MODE, keyFactory.generateSecret(desKey), random);
                    this.encryptCipher = cipher;
                }
            }
        }
        return encryptCipher;
    }

    private Cipher decryptCipher(String key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException {
        if (decryptCipher == null) {
            synchronized (DesCryptoFactory.class) {
                if (decryptCipher == null) {
                    SecureRandom random = new SecureRandom();
                    DESKeySpec desKey = new DESKeySpec(key.getBytes(DEFAULT_KEY_CHARSET));
                    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
                    Cipher cipher = Cipher.getInstance(DES);
                    cipher.init(Cipher.DECRYPT_MODE, keyFactory.generateSecret(desKey), random);
                    this.decryptCipher = cipher;
                }
            }
        }
        return decryptCipher;
    }


}
