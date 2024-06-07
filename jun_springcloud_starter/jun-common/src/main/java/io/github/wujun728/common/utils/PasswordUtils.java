package io.github.wujun728.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.util.UUID;


/**
 * 密码工具类
 *
 * @author wujun
 * @version V1.0
 * @date 2020年3月18日
 */
public class PasswordUtils {


    /**
     * 算法
     */
    private static final String ALGORITHMSTR = "AES/ECB/PKCS5Padding";

    /**
     * 密钥
     */
    public static final String KEY = "1234567@@7654321";// AES加密要求key必须要128个比特位（这里需要长度为16，否则会报错）

    /**
     * base 64 encode
     * @param bytes 待编码的byte[]
     * @return 编码后的base 64 code
     */
    private static String base64Encode(byte[] bytes){
        return new String(Base64Utils.encode(bytes));
    }

    /**
     * base 64 decode
     * @param base64Code 待解码的base 64 code
     * @return 解码后的byte[]
     * @throws Exception 抛出异常
     */
    private static byte[] base64Decode(String base64Code) throws Exception{
        return StringUtils.isEmpty(base64Code) ? null : Base64Utils.decodeFromString(base64Code);
    }


    /**
     * AES加密
     * @param content 待加密的内容
     * @param encryptKey 加密密钥
     * @return 加密后的byte[]
     */
    private static byte[] aesEncryptToBytes(String content, String encryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), "AES"));

        return cipher.doFinal(content.getBytes("utf-8"));
    }


    /**
     * AES加密为base 64 code
     *
     * @param content 待加密的内容
     * @param encryptKey 加密密钥
     * @return 加密后的base 64 code
     */
    public static String aesEncrypt(String content, String encryptKey) throws Exception {
        return base64Encode(aesEncryptToBytes(content, encryptKey));
    }

    /**
     * AES解密
     *
     * @param encryptBytes 待解密的byte[]
     * @param decryptKey 解密密钥
     * @return 解密后的String
     */
    private static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);

        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), "AES"));
        byte[] decryptBytes = cipher.doFinal(encryptBytes);

        return new String(decryptBytes);
    }


    /**
     * 将base 64 code AES解密
     *
     * @param encryptStr 待解密的base 64 code
     * @param decryptKey 解密密钥
     * @return 解密后的string
     */
    public static String aesDecrypt(String encryptStr, String decryptKey) throws Exception {
        return StringUtils.isEmpty(encryptStr) ? null : aesDecryptByBytes(Base64Utils.decodeFromString(encryptStr), decryptKey);
    }
    

    /**
     * 匹配密码
     *
     * @param salt    盐
     * @param rawPass 明文
     * @param encPass 密文
     * @return 是否匹配
     */
    public static boolean matches(String salt, String rawPass, String encPass) {
//        return new PasswordEncoderAES(salt).matches(encPass, rawPass);
        String pass1 = "" + encPass;
        String pass2 = encode(rawPass,"");

        return pass1.equals(pass2);
    }

    /**
     * 明文密码加密
     *
     * @param rawPass 明文
     * @param salt    盐
     * @reture 加密后
     */
    public static String encode(String rawPass, String salt) {
//        return new PasswordEncoderAES(salt).encode(rawPass);
        String result;
        try {
            result = PasswordUtils.aesEncrypt(rawPass, PasswordUtils.KEY);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 获取加密盐
     *
     * @return 盐值
     */
    public static String getSalt() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 20);
    }
    
    public static void main(String[] args) throws Exception {
    	String pasword = "123456";
//    	String passwd = PasswordUtils.encode(pasword, getSalt());
        String passwd = PasswordUtils.encode(pasword, "324ce32d86224b00a02b");
		System.out.println("passwd="+passwd);
		System.out.println(PasswordUtils.matches(getSalt(), pasword, passwd));
		//System.out.println("pasword="+PasswordUtils.aesDecrypt(passwd, PasswordUtils.KEY));


        String content = "url：findNames.action11111111111111111111111111111111111111111111111111111111111111111111";
        System.out.println("加密前：" + content);

        System.out.println("加密密钥和解密密钥：" + KEY);

        String encrypt = PasswordUtils.aesEncrypt(content, KEY);
        System.out.println("加密后：" + encrypt);

        String decrypt = PasswordUtils.aesDecrypt(encrypt, KEY);
        System.out.println("解密后：" + decrypt);
    }

}
