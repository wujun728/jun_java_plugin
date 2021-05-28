package com.mall.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * md5密码加密
 * @author Wujun
 * @version 1.0
 * @create_at 2017年4月26日 下午5:19:15
 */
public class Md5Utils {
	
	/**
	 * md5加密
	 * @param Md5key 
	 * @param salt 时间戳
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String getMd5(String Md5key,String salt){
		String key = DigestUtils.md5Hex(Md5key)+"&"+salt;
		return DigestUtils.md5Hex(key);
	}
	
	/**
	 * md5密码加密
	 * @param password
	 * @return
	 */
	public static String getMd5(String password){
		try {
			MessageDigest digest=MessageDigest.getInstance("md5");
		    byte[] bytes=digest.digest(password.getBytes());
		    return Hex.encodeHexString(bytes);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * md5加密
	 * @param inStr
	 * @return
	 */
	public static String stringMD5(String inStr){  
        MessageDigest md5 = null;  
        try{  
            md5 = MessageDigest.getInstance("MD5");  
        }catch (Exception e){  
            System.out.println(e.toString());  
            e.printStackTrace();  
            return "";  
        }  
        char[] charArray = inStr.toCharArray();  
        byte[] byteArray = new byte[charArray.length];  
  
        for (int i = 0; i < charArray.length; i++)  
            byteArray[i] = (byte) charArray[i];  
        byte[] md5Bytes = md5.digest(byteArray);  
        StringBuffer hexValue = new StringBuffer();  
        for (int i = 0; i < md5Bytes.length; i++){  
            int val = ((int) md5Bytes[i]) & 0xff;  
            if (val < 16)  
                hexValue.append("0");  
            hexValue.append(Integer.toHexString(val));  
        }  
        return hexValue.toString();  
    }  
	
	 /** 
     * 加密解密算法 执行一次加密，两次解密 
     */   
    public static String convertMD5(String inStr){  
        char[] a = inStr.toCharArray();  
        for (int i = 0; i < a.length; i++){  
            a[i] = (char) (a[i] ^ 't');  
        }  
        String s = new String(a);  
        return s;  
    }  
    
    public static String generatorKey(){
    	return getMd5(UUID.randomUUID().toString());
    }
    
    public static void main(String[] args) throws Exception {
    	String password="123456";
		String encrypt = EncryptUtil.encodeSalt(Md5Utils.generatorKey());
		String str=Md5Utils.getMd5(password, encrypt);
		System.out.println(encrypt);
		System.err.println(str);
		boolean b=Md5Utils.getMd5(password,encrypt).equals(str);
		System.out.println(b);
		
    	String s = new String("taoge");  
    	String s1=getMd5(s, encrypt);
        System.out.println("MD5后：" + s1);  
       // System.out.println("解密的：" + convertMD5(convertMD5("d4edbee8aae46ab95387691a74008082")));  
	}
}
