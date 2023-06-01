package com.cosmoplat.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Aes加密解密， 前端加密
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@Slf4j
public class AesUtil {
    private AesUtil() { throw new IllegalStateException("Utility class");}


    /**
     * 使用AES-128-CBC加密模式，key需要为16位,key和iv可以相同
     */
    private static String key = "uuC201807-17Cent";
    private static String iv = "UuC20180717Cente";

    /**
     * 加密方法
     *
     * @param data 要加密的数据
     * @param k  加密key
     * @param v   加密iv
     * @return 加密的结果
     */
    public static String encrypt(String data, String k, String v) {
        try {
            //"算法/模式/补码方式"
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            int blockSize = cipher.getBlockSize();

            byte[] dataBytes = data.getBytes();
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }

            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);

            SecretKeySpec keyspec = new SecretKeySpec(k.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(v.getBytes());

            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            byte[] encrypted = cipher.doFinal(plaintext);

            return new Base64().encodeToString(encrypted);

        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 使用默认的key和iv加密
     */
    public static String encrypt(String data) {
        return encrypt(data, key, iv);
    }

    /**
     * base 64 decode
     *
     * @param base64Code 待解码的base 64 code
     * @return 解码后的byte[]
     */
    public static byte[] base64Decode(String base64Code) {
        /**sun.misc.BASE64Decoder是java内部类，有时候会报错，
         * 用org.apache.commons.codec.binary.Base64替代，效果一样。
         */
        return (StringUtils.isBlank(base64Code)) ? null : Base64.decodeBase64(base64Code.getBytes());
    }

    /**
     * AES解密
     *
     * @param encryptBytes 待解密的byte[]
     * @return 解密后的String
     */
    public static String aesDecryptByBytes(byte[] encryptBytes) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            byte[] temp = iv.getBytes(Charsets.UTF_8);
            IvParameterSpec iv = new IvParameterSpec(temp);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes(), "AES"), iv);
            byte[] decryptBytes = cipher.doFinal(encryptBytes);
            return new String(decryptBytes);
        } catch (Exception e) {
            log.error("AES解密失败:{}", e.getMessage(), e);
            return "";
        }

    }

    /**
     * 将base 64 code AES解密
     *
     * @param encryptStr 待解密的base 64 code
     * @return 解密后的string
     */
    public static String aesDecrypt(String encryptStr) {
        try {
            return (encryptStr == null || "".equals(encryptStr)) ? null : aesDecryptByBytes(base64Decode(encryptStr));
        } catch (Exception e) {
            log.error("AES解密失败:{}", e.getMessage(), e);
            return "";
        }
    }
}