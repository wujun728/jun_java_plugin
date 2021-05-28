package com.mall.common.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;




/**
 * 摘要算法工具类
 * @author Wujun
 * 2017年3月17号上午11:40
 */
@SuppressWarnings("restriction")
public class EncryptUtil {

	/**
	 * DES加密算法
	 */
	public static String encodeDES(String des){
		//生成key
		try {
			KeyGenerator keygenerator = KeyGenerator.getInstance("DES");
			keygenerator.init(56);
			SecretKey secretkey = keygenerator.generateKey();
			byte[] bytes=secretkey.getEncoded();

			//key转换
		    DESKeySpec desedekeyspec = new DESKeySpec(bytes);
			SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");
		    Key key=factory.generateSecret(desedekeyspec);
				   
		    //加密
		    Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5padding");
		    cipher.init(Cipher.ENCRYPT_MODE,key);
		    byte[] result= cipher.doFinal(des.getBytes());
		    return Hex.encodeHexString(result);
		    
//		    //解密
//		    cipher.init(Cipher.DECRYPT_MODE, key);
//		    result=cipher.doFinal(result);
//		    System.out.println("解密"+new String(result));
		   // return  Hex.encodeHexString(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * des算法解密
	 * @param key
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 * @throws InvalidKeySpecException 
	 */
	public static void decodeDES(String des) throws Exception{
		KeyGenerator keygenerator = KeyGenerator.getInstance("DES");
		keygenerator.init(56);
		SecretKey secretkey = keygenerator.generateKey();
		byte[] bytes = secretkey.getEncoded();

		//key转换
	    DESKeySpec desedekeyspec = new DESKeySpec(bytes);
		SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");
	    Key key = factory.generateSecret(desedekeyspec);

	    Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5padding");
	    byte[] result= cipher.doFinal(des.getBytes());
	    //解密
	    cipher.init(Cipher.DECRYPT_MODE, key);
	    result = cipher.doFinal(result);
	    System.out.println("解密"+new String(result));
	   // return  Hex.encodeHexString(result);
	}
	
	/**
	 * 3重des加密算法
	 */
	
	public static void getJDK3des(String des){
		//生成key
		try {
			KeyGenerator keygenerator=KeyGenerator.getInstance("DESede");
			keygenerator.init(168);
			SecretKey secretkey = keygenerator.generateKey();
			byte[] bytes=secretkey.getEncoded();

			//key转换
		    DESedeKeySpec desedekeyspec=new DESedeKeySpec(bytes);
			SecretKeyFactory factory=SecretKeyFactory.getInstance("DESede");
		    Key key=factory.generateSecret(desedekeyspec);
				   
		    //加密
		    Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5padding");
		    cipher.init(Cipher.ENCRYPT_MODE,key);
		    byte[] result= cipher.doFinal(des.getBytes());
		    System.out.println(Hex.encodeHexString(result));
		    
		    //解密
		    cipher.init(Cipher.DECRYPT_MODE, key);
		    result=cipher.doFinal(result);
		    System.out.println("解密"+new String(result));
		   // return  Hex.encodeHexString(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/***
	 * AES加密算法
	 */
	public static void getAES(String aes){
		//生成key
		try {
			KeyGenerator keygenerator=KeyGenerator.getInstance("AES");
			keygenerator.init(128);
			SecretKey secretkey=keygenerator.generateKey();
			byte[] bytes=secretkey.getEncoded();
		
		//key转换
			Key key=new SecretKeySpec(bytes, "AES");
		//加密
			Cipher cipher=	Cipher.getInstance("AES/ECB/PKCS5padding");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] result=cipher.doFinal(aes.getBytes());
			 System.out.println(Hex.encodeHexString(result));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * PBE加密算法
	 */
	
	public static void getPBE(String pbe){
		SecureRandom securerandom =new SecureRandom();
	    byte[] bytes=securerandom.generateSeed(8);
	    
	    //口令密钥
	    String password="hello";
	    PBEKeySpec pbekeyspec =new PBEKeySpec(password.toCharArray());
	    try {
			SecretKeyFactory factory=SecretKeyFactory.getInstance("PBEWITHMD5andDES");
			Key key=factory.generateSecret(pbekeyspec);
			
			//加密
			PBEParameterSpec  pbeparameterspec=new PBEParameterSpec(bytes,100);
			Cipher cipher=	Cipher.getInstance("PBEWITHMD5andDES");
			cipher.init(Cipher.ENCRYPT_MODE, key,pbeparameterspec);
			byte[] result=cipher.doFinal(pbe.getBytes());
			System.out.println(Hex.encodeHexString(result));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	/**
	 * SHA摘要算法
	 * @throws NoSuchAlgorithmException 
	 */
	public static String getSHA(String key) throws NoSuchAlgorithmException{
		MessageDigest sha=MessageDigest.getInstance("SHA");
		byte[] by=sha.digest(key.getBytes());
	    return Hex.encodeHexString(by);
	}
	
	/**
	 * 
	 * @param salt 可以是时间戳
	 * @return
	 */
	public static String encodeSalt(String salt){
		return DigestUtils.sha1Hex(salt);
	}
	
	/**
	 * mac消息摘要算法
	 */
	public static String getMac(String macStr){
		try {
			KeyGenerator keygenerator=KeyGenerator.getInstance("HmacMD5");//初始化密钥
			SecretKey secretkey=keygenerator.generateKey();
			byte[] key= secretkey.getEncoded();//获得密钥
			
			SecretKey secretkeySpec=new SecretKeySpec(key, "HmacMD5");
			Mac mac=Mac.getInstance(secretkeySpec.getAlgorithm());
			mac.init(secretkeySpec);
			byte[] hmac=mac.doFinal(macStr.getBytes());
			return Hex.encodeHexString(hmac);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * SHA加密算法
	 * @param key
	 * @return
	 */
	public static String getSHA1(String key){
		try {
	      MessageDigest digest=MessageDigest.getInstance("SHA");
	      byte[] bytes=digest.digest(key.getBytes());
	      //将字节数组转换成16进制字符串
	      return Hex.encodeHexString(bytes);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * md5加密算法
	 * @param key
	 * @return
	 */
	public static String getMD5(String key){
		try {
		    MessageDigest digest=MessageDigest.getInstance("MD5");
		    byte[] bytes=digest.digest(key.getBytes());
		      //将字节数组转换成16进制字符串
	        return Hex.encodeHexString(bytes);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 将用户密码加密
	 * @param userName
	 * @param password
	 * @return
	 */
	public static String encryptUserInfo(String userName,String password){
		String userInfo=userName+password;
		String md5Info=Md5Utils.stringMD5(userInfo);
		String dBase64=getBase64(md5Info)+","+userName;
        return getBase64(dBase64);
	}

	@SuppressWarnings("restriction")
	private static String getBase64(String str) {
		 if(StringUtils.isBlank(str)){
	            return null;
	        }
	        byte[] b = null;
	        try {
	            b = str.getBytes("utf-8");
	        } catch (UnsupportedEncodingException e) {
	            e.printStackTrace();
	        }
	     return new BASE64Encoder().encode(b);
	}
	
	// base64解密
    @SuppressWarnings("restriction")
	public static final String getStrFromBase64(String str) {
        byte[] b = null;
        String result = null;
        if (str != null&&!"".equals(str.trim())) {
            BASE64Decoder decoder = new BASE64Decoder();
            try {
                b = decoder.decodeBuffer(str);
                result = new String(b, "utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    
    
    /**
     * 从base64中解密获取用户名
     * @return
     */
    public static final String getBase64UserName(String encryptionInfo){
        //解密身份信息；
        String userName=null;
        String str=    getStrFromBase64(encryptionInfo);
        if(str==null||"".equals(str.trim())){
            return null;
        }
        if(str.indexOf(",")>0){
            String []arr=str.split(",");
            userName=arr[1];
        }
        return userName;
    }
}
