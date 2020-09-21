/*
 * Copyright 2007-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.ymate.platform.core.util;

import net.ymate.platform.core.lang.PairObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * DES/AES/PBE/RSA加密/解密工具类
 *
 * @author 刘镇  (suninformation@163.com) on 2011-6-14 下午12:24:17
 * @version 1.0
 */
public class CodecUtils {

    public static final CodecHelper DES;

    public static final CodecHelper AES;

    public static final CodecHelper PBE;

    public static final RSACodecHelper RSA;

    static {
        DES = new CodecHelper(56, "DES", "DES/ECB/PKCS5Padding");
        AES = new AESCodecHelper(128, 128);
        PBE = new PBECodecHelper(128);
        RSA = new RSACodecHelper(1024);
    }

    public static class CodecHelper {

        /**
         * 密钥算法
         */
        protected final String KEY_ALGORITHM;

        /**
         * 加密/解密算法/工作模式/填充方式
         */
        protected final String CIPHER_ALGORITHM;

        protected final int KEY_SIZE;

        public CodecHelper(int keySize, String keyAlgorithm, String cipherAlgorithm) {
            KEY_SIZE = keySize;
            KEY_ALGORITHM = keyAlgorithm;
            CIPHER_ALGORITHM = cipherAlgorithm;
        }

        /**
         * 生成密钥
         *
         * @return byte[] 二进制密钥
         * @throws Exception if an error occurs.
         */
        public byte[] initKey() throws Exception {
            // 实例化密钥生成器
            KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
            // 初始化密钥生成器
            kg.init(KEY_SIZE);
            // 生成密钥
            SecretKey secretKey = kg.generateKey();
            // 获取二进制密钥编码形式
            return secretKey.getEncoded();
        }

        public String initKeyToString() throws Exception {
            return Base64.encodeBase64String(initKey());
        }

        /**
         * 转换密钥
         *
         * @param key 二进制密钥
         * @return Key 密钥
         * @throws Exception if an error occurs.
         */
        public Key toKey(byte[] key) throws Exception {
            // 实例化密钥工厂
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
            // 生成密钥
            return keyFactory.generateSecret(new SecretKeySpec(key, KEY_ALGORITHM));
        }

        /**
         * 加密数据
         *
         * @param data 待加密数据
         * @param key  密钥
         * @return byte[] 加密后的数据
         * @throws Exception if an error occurs.
         */
        public byte[] encrypt(byte[] data, byte[] key) throws Exception {
            // 实例化
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            // 初始化，设置为加密模式
            cipher.init(Cipher.ENCRYPT_MODE, toKey(key));
            // 执行操作
            return cipher.doFinal(data);
        }

        public String encrypt(String data, String key) throws Exception {
            return Base64.encodeBase64String(encrypt(data.getBytes(), key.getBytes()));
        }

        /**
         * 解密数据
         *
         * @param data 待解密数据
         * @param key  密钥
         * @return byte[] 解密后的数据
         * @throws Exception if an error occurs.
         */
        public byte[] decrypt(byte[] data, byte[] key) throws Exception {
            // 实例化
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            // 初始化，设置为解密模式
            cipher.init(Cipher.DECRYPT_MODE, toKey(key));
            // 执行操作
            return cipher.doFinal(data);
        }

        public String decrypt(String data, String key) throws Exception {
            return StringUtils.newStringUtf8(decrypt(Base64.decodeBase64(data), key.getBytes()));
        }
    }

    public static class AESCodecHelper extends CodecHelper {

        private final int ITERATION_COUNT;

        public AESCodecHelper(int keySize, int iterationCount) {
            super(keySize, "AES", "AES");
            ITERATION_COUNT = iterationCount <= 0 ? 128 : iterationCount;
        }

        @Override
        public Key toKey(byte[] key) throws Exception {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            KeySpec spec = new PBEKeySpec(StringUtils.newStringUtf8(key).toCharArray(), key, ITERATION_COUNT, KEY_SIZE);
            SecretKey tmp = factory.generateSecret(spec);
            return new SecretKeySpec(tmp.getEncoded(), CIPHER_ALGORITHM);
        }
    }

    public static class PBECodecHelper extends CodecHelper {

        private final int ITERATION_COUNT;

        public PBECodecHelper(int iterationCount) {
            super(0, "PBE", "PBEWithMD5AndDES");
            ITERATION_COUNT = iterationCount <= 0 ? 128 : iterationCount;
        }

        @Override
        public byte[] initKey() throws Exception {
            throw new UnsupportedOperationException();
        }

        @Override
        public String initKeyToString() throws Exception {
            throw new UnsupportedOperationException();
        }

        @Override
        public Key toKey(byte[] key) throws Exception {
            SecretKeyFactory factory = SecretKeyFactory.getInstance(CIPHER_ALGORITHM);
            KeySpec spec = new PBEKeySpec(StringUtils.newStringUtf8(key).toCharArray());
            return factory.generateSecret(spec);
        }

        @Override
        public byte[] encrypt(byte[] data, byte[] key) throws Exception {
            return encrypt(data, key, DigestUtils.md5Hex(key).substring(0, 8).getBytes());
        }

        public byte[] encrypt(byte[] data, byte[] key, byte[] salt) throws Exception {
            // 实例化
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            // 初始化，设置为加密模式
            PBEParameterSpec parameterSpec = new PBEParameterSpec(salt, ITERATION_COUNT);
            cipher.init(Cipher.ENCRYPT_MODE, toKey(key), parameterSpec);
            // 执行操作
            return cipher.doFinal(data);
        }

        public String encrypt(String data, String key, String salt) throws Exception {
            return Base64.encodeBase64String(encrypt(data.getBytes(), key.getBytes(), salt.getBytes()));
        }

        @Override
        public byte[] decrypt(byte[] data, byte[] key) throws Exception {
            return decrypt(data, key, DigestUtils.md5Hex(key).substring(0, 8).getBytes());
        }

        public byte[] decrypt(byte[] data, byte[] key, byte[] salt) throws Exception {
            // 实例化
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            // 初始化，设置为解密模式
            PBEParameterSpec parameterSpec = new PBEParameterSpec(salt, ITERATION_COUNT);
            cipher.init(Cipher.DECRYPT_MODE, toKey(key), parameterSpec);
            // 执行操作
            return cipher.doFinal(data);
        }

        public String decrypt(String data, String key, String salt) throws Exception {
            return StringUtils.newStringUtf8(decrypt(Base64.decodeBase64(data), key.getBytes(), salt.getBytes()));
        }
    }

    public static class RSACodecHelper extends CodecHelper {

        public RSACodecHelper(int keySize) {
            super(keySize, "RSA", "MD5withRSA");
        }

        public RSACodecHelper(int keySize, String cipherAlgorithm) {
            super(keySize, "RSA", cipherAlgorithm);
        }

        public byte[] initKey() throws Exception {
            throw new UnsupportedOperationException();
        }

        public String initKeyToString() throws Exception {
            throw new UnsupportedOperationException();
        }

        public PairObject<RSAPublicKey, RSAPrivateKey> initRSAKey() throws Exception {
            KeyPairGenerator _keyPG = KeyPairGenerator.getInstance(KEY_ALGORITHM);
            _keyPG.initialize(KEY_SIZE);
            //
            KeyPair _keyPair = _keyPG.generateKeyPair();
            //
            return new PairObject<RSAPublicKey, RSAPrivateKey>((RSAPublicKey) _keyPair.getPublic(), (RSAPrivateKey) _keyPair.getPrivate());
        }

        public String getRSAKey(Key rsaKey) throws Exception {
            return Base64.encodeBase64String(rsaKey.getEncoded());
        }

        public String sign(byte[] data, String privateKey) throws Exception {
            //
            PKCS8EncodedKeySpec _keySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
            KeyFactory _keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            PrivateKey _privKey = _keyFactory.generatePrivate(_keySpec);
            //
            Signature _sign = Signature.getInstance(CIPHER_ALGORITHM);
            _sign.initSign(_privKey);
            _sign.update(data);
            //
            return Base64.encodeBase64String(_sign.sign());
        }

        public boolean verify(byte[] data, String publicKey, String sign) throws Exception {
            //
            X509EncodedKeySpec _keySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKey));
            KeyFactory _keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            PublicKey _pubKey = _keyFactory.generatePublic(_keySpec);
            //
            Signature _sign = Signature.getInstance(CIPHER_ALGORITHM);
            _sign.initVerify(_pubKey);
            _sign.update(data);
            //
            return _sign.verify(Base64.decodeBase64(sign.getBytes()));
        }

        private byte[] __doDataSegment(byte[] data, Cipher cipher, int inputLen) throws Exception {
            ByteArrayOutputStream _outStream = null;
            try {
                _outStream = new ByteArrayOutputStream();
                int _offSet = 0;
                byte[] _buffer;
                int _idx = 0;
                int _dataLen = data.length;
                while (_dataLen - _offSet > 0) {
                    if (_dataLen - _offSet > inputLen) {
                        _buffer = cipher.doFinal(data, _offSet, inputLen);
                    } else {
                        _buffer = cipher.doFinal(data, _offSet, _dataLen - _offSet);
                    }
                    _outStream.write(_buffer, 0, _buffer.length);
                    _idx++;
                    _offSet = _idx * inputLen;
                }
                return _outStream.toByteArray();
            } finally {
                if (_outStream != null) {
                    try {
                        _outStream.close();
                    } catch (Exception e) {
                        // Nothing...
                    }
                }
            }
        }

        @Override
        public byte[] encrypt(byte[] data, byte[] key) throws Exception {
            PKCS8EncodedKeySpec _keySpec = new PKCS8EncodedKeySpec(key);
            KeyFactory _keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            Key _privKey = _keyFactory.generatePrivate(_keySpec);
            Cipher _cipher = Cipher.getInstance(_keyFactory.getAlgorithm());
            _cipher.init(Cipher.ENCRYPT_MODE, _privKey);
            //
            return __doDataSegment(data, _cipher, 117);
        }

        @Override
        public String encrypt(String data, String key) throws Exception {
            return Base64.encodeBase64String(encrypt(data.getBytes(), Base64.decodeBase64(key)));
        }

        @Override
        public byte[] decrypt(byte[] data, byte[] key) throws Exception {
            PKCS8EncodedKeySpec _keySpec = new PKCS8EncodedKeySpec(key);
            KeyFactory _keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            Key _privKey = _keyFactory.generatePrivate(_keySpec);
            Cipher _cipher = Cipher.getInstance(_keyFactory.getAlgorithm());
            _cipher.init(Cipher.DECRYPT_MODE, _privKey);
            //
            return __doDataSegment(data, _cipher, 128);
        }

        @Override
        public String decrypt(String data, String key) throws Exception {
            return StringUtils.newStringUtf8(decrypt(Base64.decodeBase64(data), Base64.decodeBase64(key)));
        }

        public byte[] encryptPublicKey(byte[] data, byte[] key) throws Exception {
            X509EncodedKeySpec _keySpec = new X509EncodedKeySpec(key);
            KeyFactory _keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            Key _pubKey = _keyFactory.generatePublic(_keySpec);
            Cipher _cipher = Cipher.getInstance(_keyFactory.getAlgorithm());
            _cipher.init(Cipher.ENCRYPT_MODE, _pubKey);
            //
            return __doDataSegment(data, _cipher, 117);
        }

        public String encryptPublicKey(String data, String key) throws Exception {
            return Base64.encodeBase64String(encryptPublicKey(data.getBytes(), Base64.decodeBase64(key)));
        }

        public byte[] decryptPublicKey(byte[] data, byte[] key) throws Exception {
            X509EncodedKeySpec _keySpec = new X509EncodedKeySpec(key);
            KeyFactory _keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            Key _pubKey = _keyFactory.generatePublic(_keySpec);
            Cipher _cipher = Cipher.getInstance(_keyFactory.getAlgorithm());
            _cipher.init(Cipher.DECRYPT_MODE, _pubKey);
            //
            return __doDataSegment(data, _cipher, 128);
        }

        public String decryptPublicKey(String data, String key) throws Exception {
            return new String(decryptPublicKey(Base64.decodeBase64(data), Base64.decodeBase64(key)));
        }
    }
}
