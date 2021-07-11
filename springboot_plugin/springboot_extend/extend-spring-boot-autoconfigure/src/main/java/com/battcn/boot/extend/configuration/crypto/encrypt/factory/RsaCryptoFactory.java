package com.battcn.boot.extend.configuration.crypto.encrypt.factory;

import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Decoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * RSA 加密解密算法
 *
 * @author Levin
 * @since 2018/12/28 0028
 */
@Slf4j
public class RsaCryptoFactory implements CryptoFactory {

    /**
     * 加密
     *
     * @param key     密钥
     * @param content 需要加密的内容
     * @return 加密结果
     * @throws RuntimeException RuntimeException
     */
    @Override
    public byte[] encrypt(String key, byte[] content) throws RuntimeException {
        try {
            return encryptCipher(key).doFinal(content);
        } catch (InvalidKeySpecException | IOException | IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException e) {
            log.error("[RSA 加密失败]", e);
        }
        return null;
    }

    /**
     * 解密
     *
     * @param key     密钥
     * @param content 需要解密的内容
     * @return 解密结果
     * @throws RuntimeException RuntimeException
     */
    @Override
    public byte[] decrypt(String key, byte[] content) throws RuntimeException {
        try {
            return decryptCipher(key).doFinal(content);
        } catch (InvalidKeySpecException | IOException | IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
            log.error("[RSA 解密失败]", e);
        }
        return null;
    }


    private volatile Cipher encryptCipher = null;
    private volatile Cipher decryptCipher = null;

    private static final String RSA = "RSA";

    private PublicKey getPublicKey(String key) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        return keyFactory.generatePublic(keySpec);
    }

    private PrivateKey getPrivateKey(String key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException, InvalidKeySpecException {
        byte[] keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        return keyFactory.generatePrivate(keySpec);
    }

    private Cipher encryptCipher(String key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException, InvalidKeySpecException {
        if (encryptCipher == null) {
            synchronized (RsaCryptoFactory.class) {
                if (encryptCipher == null) {
                    Cipher cipher = Cipher.getInstance(RSA);
                    cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(key));
                    this.encryptCipher = cipher;
                }
            }
        }
        return encryptCipher;
    }

    private Cipher decryptCipher(String key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException, InvalidKeySpecException {
        if (decryptCipher == null) {
            synchronized (RsaCryptoFactory.class) {
                if (decryptCipher == null) {
                    Cipher cipher = Cipher.getInstance(RSA);
                    cipher.init(Cipher.DECRYPT_MODE, getPrivateKey(key));
                    this.decryptCipher = cipher;
                }
            }
        }
        return decryptCipher;
    }


}
