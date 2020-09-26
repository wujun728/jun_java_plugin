package com.ketayao.utils.codec;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * 加解密工具类
 * @author Winter Lau
 */
public class CryptUtils {

	private final static String DES = "DES";
	
    /**
     * 数据加密
     * @param data
     * @param key 密钥
     * @return
     * @throws Exception
     */
    public final static String encrypt(String data, String key){
    	if (data != null) {
    		try {
    			key = fixKey(key);
                return byte2hex(encrypt(data.getBytes(),key.getBytes()));
            }catch(Exception e) {
    			throw new RuntimeException(e);
            }
    	}
        
        return null;
    }
	
	/**
	 * 加密
	 * @param src 数据源
	 * @param key 密钥，长度必须是8的倍数
	 * @return	  返回加密后的数据
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] src, byte[] key) throws RuntimeException {
		//		DES算法要求有一个可信任的随机数源
		try{
			SecureRandom sr = new SecureRandom();
			// 从原始密匙数据创建DESKeySpec对象
			DESKeySpec dks = new DESKeySpec(key);
			// 创建一个密匙工厂，然后用它把DESKeySpec转换成
			// 一个SecretKey对象
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
			SecretKey securekey = keyFactory.generateSecret(dks);
			// Cipher对象实际完成加密操作
			Cipher cipher = Cipher.getInstance(DES);
			// 用密匙初始化Cipher对象
			cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
			// 现在，获取数据并加密
			// 正式执行加密操作
			return cipher.doFinal(src);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}

    /**
     * 数据解密
     * @param data
     * @param key 密钥
     * @return
     * @throws Exception
     */
    public final static String decrypt(String data, String key) throws Exception{
    	key = fixKey(key);
    	return new String(decrypt(hex2byte(data.getBytes()),key.getBytes()));
    }
	
	/**
	 * 解密
	 * @param src	数据源
	 * @param key	密钥，长度必须是8的倍数
	 * @return		返回解密后的原始数据
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] src, byte[] key) throws RuntimeException {
		try{
			//		DES算法要求有一个可信任的随机数源
			SecureRandom sr = new SecureRandom();
			// 从原始密匙数据创建一个DESKeySpec对象
			DESKeySpec dks = new DESKeySpec(key);
			// 创建一个密匙工厂，然后用它把DESKeySpec对象转换成
			// 一个SecretKey对象
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
			SecretKey securekey = keyFactory.generateSecret(dks);
			// Cipher对象实际完成解密操作
			Cipher cipher = Cipher.getInstance(DES);
			// 用密匙初始化Cipher对象
			cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
			// 现在，获取数据并解密
			// 正式执行解密操作
			return cipher.doFinal(src);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * key的长度必须是8的倍数，如果不是则用0补齐。
	 * @param key
	 * @return
	 */
	private static String fixKey(String key) {
		if (key == null || key.length() == 0) {
			return "00000000";
		}
		
		int y = key.length() % 8;
		if (y != 0) {
			for (int i = 0; i < 8 - y; i++) {
				key += '0';
			} 
		}
		
		return key;
	}

	/**
	 * 二行制转字符串
	 * @param b
	 * @return
	 */
    private static String byte2hex(byte[] b) {
		StringBuilder hs = new StringBuilder();
		String stmp;
		for (int n = 0; b!=null && n < b.length; n++) {
			stmp = Integer.toHexString(b[n] & 0XFF);
			if (stmp.length() == 1)
				hs.append('0');
			hs.append(stmp);
		}
		return hs.toString().toUpperCase();
	}
    
    private static byte[] hex2byte(byte[] b) {
        if((b.length%2)!=0)
            throw new IllegalArgumentException();
		byte[] b2 = new byte[b.length/2];
		for (int n = 0; n < b.length; n+=2) {
		    String item = new String(b,n,2);
		    b2[n/2] = (byte)Integer.parseInt(item,16);
		}
        return b2;
    }
    
    public static void main(String[] args) throws Exception {
		String mi = encrypt("123123", "zxcvbnm");
		System.out.println(decrypt(mi, "zxcvbnm"));
    	
    	//System.out.println(fixKey(null));
	}
}
