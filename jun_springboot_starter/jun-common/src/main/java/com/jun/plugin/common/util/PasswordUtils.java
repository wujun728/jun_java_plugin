package com.jun.plugin.common.util;

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
            result = EncryptUtils.aesEncrypt(rawPass, EncryptUtils.KEY);
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
		//System.out.println("pasword="+EncryptUtils.aesDecrypt(passwd, EncryptUtils.KEY));
	}

}
