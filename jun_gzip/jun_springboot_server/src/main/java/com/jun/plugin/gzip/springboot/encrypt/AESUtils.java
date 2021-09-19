package com.jun.plugin.gzip.springboot.encrypt;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;


import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

/**
 * @Author:Yolanda
 * @Date: 2020/5/9 9:51
 */
public class AESUtils {
    private static final String GZIP_ENCODE_UTF_8 = "UTF-8";

    private static byte[] keyValue = new byte[] {       //用户密钥
        22,25,-35,-45,25,98,-55,-45,10,35,-45,25,
        26,-95,25,-65,-78,-99,85,45,-62,10,-0,11,
        -35,48,-98,65,-32,14,-78,25,36,-56,-45,-45,
        12,15,-35,-75,15,-14,62,-25,33,-45,55,68,-88
    };
    private static byte[] iv = new byte[] {             //算法参数
        -12,35,-25,65,45,-87,95,-22,-15,45,55,-66,32,5-4,84,55
    };
    private static SecretKey key;                       //加密密钥
    private static AlgorithmParameterSpec paramSpec;    //算法参数
    private static Cipher ecipher;                      //加密算法

    static{
        KeyGenerator kgen;
        try {
            //为指定算法生成一个密钥生成器对象。
            kgen = KeyGenerator.getInstance("AES");
            //使用用户提供的随机源初始化此密钥生成器，使其具有确定的密钥长度。
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(keyValue);
            kgen.init(128, secureRandom);
            //使用KeyGenerator生成（对称）密钥。
            key = kgen.generateKey();
            //使用iv中的字节作为IV来构造一个 算法参数。
            paramSpec = new IvParameterSpec(iv);
            //生成一个实现指定转换的 Cipher 对象
            ecipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 加密，使用指定数据源生成密钥，使用用户数据作为算法参数进行AES加密
     * @param msg 加密的数据
     * @return
     */
    public static String encrypt(String msg) {
        String str = "";
        try {
            //用密钥和一组算法参数初始化此 cipher
            ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
            //加密并转换成16进制字符串
            str = asHex(ecipher.doFinal(msg.getBytes(GZIP_ENCODE_UTF_8)));
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 解密，对生成的16进制的字符串进行解密
     * @param value 解密的数据
     * @return
     */
    public static String decrypt(String value) {
        try {
            ecipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
            return new String(ecipher.doFinal(asBin(value)),GZIP_ENCODE_UTF_8);
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 将字节数组转换成16进制字符串
     * @param buf
     * @return
     */
    private static String asHex(byte buf[]) {
        StringBuffer strbuf = new StringBuffer(buf.length * 2);
        int i;
        for (i = 0; i < buf.length; i++) {
            if (((int) buf[i] & 0xff) < 0x10)//小于十前面补零
                strbuf.append("0");
            strbuf.append(Long.toString((int) buf[i] & 0xff, 16));
        }
        return strbuf.toString();
    }

    /**
     * 将16进制字符串转换成字节数组
     * @param src
     * @return
     */
    private static byte[] asBin(String src) {
        if (src.length() < 1)
            return null;
        byte[] encrypted = new byte[src.length() / 2];
        for (int i = 0; i < src.length() / 2; i++) {
            String str1 = src.substring(i * 2, i * 2 + 1);
            int high = Integer.parseInt(src.substring(i * 2, i * 2 + 1), 16);//取高位字节
            int low = Integer.parseInt(src.substring(i * 2 + 1, i * 2 + 2), 16);//取低位字节
            encrypted[i] = (byte) (high * 16 + low);
        }
        return encrypted;
    }

}
