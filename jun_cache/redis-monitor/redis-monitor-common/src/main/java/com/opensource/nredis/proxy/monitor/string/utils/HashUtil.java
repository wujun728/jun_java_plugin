package com.opensource.nredis.proxy.monitor.string.utils;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;



/**
 * hash 算法类
 * @author liubing
 *
 */
public class HashUtil {
	
	
	
	public static String encryptMD5(String str){
		return digest("MD5", str);
	}
	
	public static String encryptSHA(String str){
		return digest("SHA", str);
	}
	
	private static String digest(String code,String str) {  
        MessageDigest messageDigest = null;  
  
        try {  
            messageDigest = MessageDigest.getInstance(code);  
  
            messageDigest.reset();  
  
            messageDigest.update(str.getBytes());  
        } catch (NoSuchAlgorithmException e) {  
        	//Logger.error(e);  
            System.exit(-1);  
        } 
        byte[] byteArray = messageDigest.digest();  
  
        StringBuffer md5StrBuff = new StringBuffer();  
  
        for (int i = 0; i < byteArray.length; i++) {              
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)  
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));  
            else  
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));  
        }  
  
        return md5StrBuff.toString();  
    }  

	public static int hash_time33(String source) {
		int hash = 0;
		for (int i = 0; i < source.length(); i++) {
			hash = hash * 33 + Integer.valueOf(source.charAt(i));
		}
		return hash;
	}
}
