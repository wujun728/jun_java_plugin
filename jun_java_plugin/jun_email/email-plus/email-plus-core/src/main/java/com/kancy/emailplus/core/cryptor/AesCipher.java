package com.kancy.emailplus.core.cryptor;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * 密码持有类
 *
 * @author Wujun
 */
public abstract class AesCipher {

    private static final String CIPHER_INSTANCE_NAME = "AES/GCM/NoPadding";
    private static final String SECRET_KEY_ALGORITHM = "AES";
    private static final int GCM_TAG_LENGTH = 128;
    private static final int KEYGEN_INIT_LENGTH = 128;
    private static final String PROVIDER = "SunJCE";

    /**
     * key is Tenant
     * value is Tenant's secret key
     */
    private Map<String, byte[]> keys = new HashMap<>();

    public final boolean randomSecretKey;

    public AesCipher() {
        this.randomSecretKey = false;
    }

    public AesCipher(boolean randomSecretKey) {
        this.randomSecretKey = randomSecretKey;
    }

    public byte[] encrypt(byte[] dataToEncrypt, String keyId) {
        if (dataToEncrypt == null || dataToEncrypt.length == 0) {
            return dataToEncrypt;
        }
        Cipher cipher = getCipher(Cipher.ENCRYPT_MODE, keyId);
        try {
            return cipher.doFinal(dataToEncrypt);
        } catch (BadPaddingException | IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        }
    }

    public String encryptAndEncode(byte[] dataToEncrypt, String keyId) {
        byte[] encryptedData = encrypt(dataToEncrypt, keyId);
        return Base64.getEncoder().encodeToString(encryptedData);
    }

    public byte[] encryptString(String dataToEncrypt, String keyId) {
        if (dataToEncrypt == null) {
            return new byte[0];
        }
        return encrypt(dataToEncrypt.getBytes(), keyId);
    }

    public byte[] decrypt(byte[] encryptedData, String keyId) {
        if (encryptedData == null || encryptedData.length == 0) {
            return encryptedData;
        }
        try {
            final Cipher cipher = getCipher(Cipher.DECRYPT_MODE, keyId);
            return cipher.doFinal(encryptedData);
        } catch (BadPaddingException | IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] decodeAndDecrypt(String encryptedData, String keyId) {
        if (encryptedData == null) {
            return new byte[0];
        }
        if (encryptedData.length() == 0) {
            return new byte[0];
        }
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedData);
        return decrypt(encryptedBytes, keyId);
    }

    public String decryptString(byte[] encryptedData, String keyId) {
        if (encryptedData == null) {
            return null;
        }
        return new String(decrypt(encryptedData, keyId));
    }

    /**
     * 添加密钥，并生成密码对
     *
     * @param id        密钥ID
     * @param key       密钥
     */
    synchronized void addKey(String id, byte[] key) {
        keys.put(id, key);
    }


    private Cipher getCipher(int operationMode, String keyId) {
        byte[] key = keys.get(keyId);
        assert key != null : "key shouldn't be null!";
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_INSTANCE_NAME, PROVIDER);
            byte[] iv = generateIv(cipher, keyId);
            cipher.init(operationMode, randomSecretKey ? getRandomSecretKey(key) : getFixedSecretKey(key),
                    new GCMParameterSpec(GCM_TAG_LENGTH, iv));
            return cipher;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | NoSuchProviderException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取固定的密码
     * @param key
     * @return
     */
    private SecretKey getFixedSecretKey(byte[] key)  {
        return new SecretKeySpec(key, SECRET_KEY_ALGORITHM);
    }

    /**
     * 获取随机密码
     * @param key
     * @return
     * @throws NoSuchAlgorithmException
     */
    private SecretKey getRandomSecretKey(byte[] key) throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance(SECRET_KEY_ALGORITHM);
        keyGen.init(KEYGEN_INIT_LENGTH, new SecureRandom(key));
        SecretKey secretKey = keyGen.generateKey();
        return new SecretKeySpec(secretKey.getEncoded(), SECRET_KEY_ALGORITHM);
    }

    /**
     * 算法向量，增加算法强度
     * @param cipher
     * @param keyId
     * @return
     */
    private byte[] generateIv(Cipher cipher, String keyId) {
        byte[] iv = new byte[cipher.getBlockSize()];
        byte[] keyIdBytes = keyId.getBytes();
        for (int i = 0; i < Integer.min(keyIdBytes.length, iv.length); i++) {
            iv[i] = (byte) ((keyIdBytes[i] + i * 0xF) % 0xFF);
        }
        return iv;
    }
}
