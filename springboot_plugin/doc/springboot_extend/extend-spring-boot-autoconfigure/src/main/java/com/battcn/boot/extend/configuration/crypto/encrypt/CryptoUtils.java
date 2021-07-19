package com.battcn.boot.extend.configuration.crypto.encrypt;


import com.battcn.boot.extend.configuration.crypto.HexUtils;
import com.battcn.boot.extend.configuration.crypto.encrypt.factory.AesCryptoFactory;
import com.battcn.boot.extend.configuration.crypto.encrypt.factory.CryptoFactory;
import com.battcn.boot.extend.configuration.crypto.encrypt.factory.DesCryptoFactory;
import com.battcn.boot.extend.configuration.crypto.encrypt.factory.RsaCryptoFactory;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * @author Levin
 * @since 2018/12/27 0027
 */
public class CryptoUtils {

    private static class LazyCryptoHolder {
        /**
         * Aes 加密工厂
         */
        private final static CryptoFactory AES_CRYPTO_FACTORY = new AesCryptoFactory();
        private final static CryptoFactory DES_CRYPTO_FACTORY = new DesCryptoFactory();
        private final static CryptoFactory RSA_CRYPTO_FACTORY = new RsaCryptoFactory();
    }

    private static CryptoFactory getFactory(CryptoType type) {
        switch (type) {
            case AES:
                return LazyCryptoHolder.AES_CRYPTO_FACTORY;
            case DES:
                return LazyCryptoHolder.DES_CRYPTO_FACTORY;
            case RSA:
                return LazyCryptoHolder.RSA_CRYPTO_FACTORY;
            default:
                throw new NullPointerException("未检测到加密");
        }
    }

    public static InputStream encrypt(CryptoType type, String key, InputStream inputStream) throws IOException {
        final CryptoFactory factory = getFactory(type);
        final byte[] bytes = IOUtils.toByteArray(inputStream);
        return new ByteArrayInputStream(factory.encrypt(key, bytes));
    }

    public static InputStream decrypt(CryptoType type, String key, InputStream inputStream) throws IOException {
        final CryptoFactory factory = getFactory(type);
        final byte[] bytes = IOUtils.toByteArray(inputStream);
        final byte[] decrypt = factory.decrypt(key, bytes);
        return new ByteArrayInputStream(decrypt);
    }

    public static byte[] encrypt(CryptoType type, String key, byte[] content) {
        final CryptoFactory factory = getFactory(type);
        return factory.encrypt(key, content);
    }

    public static byte[] decrypt(CryptoType type, String key, byte[] content) {
        final CryptoFactory factory = getFactory(type);
        return factory.decrypt(key, content);
    }


    public static String encryptToString(CryptoType type, String key, String content, Charset charset) {
        return HexUtils.toHexString(encrypt(type, key, content.getBytes(charset)));
    }


    public static String decryptToString(CryptoType type, String key, String content, Charset charset) {
        final byte[] bytes = HexUtils.fromHexString(content);
        final byte[] decrypt = decrypt(type, key, bytes);
        return new String(decrypt, charset);
    }

}
