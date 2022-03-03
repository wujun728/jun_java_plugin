package org.springrain.frame.util;

import java.security.MessageDigest;
import java.util.Base64;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 加密的工具类
 * 
 * @author caomei
 * 
 */
public class SecUtils {

    private static final Logger logger = LoggerFactory.getLogger(SecUtils.class);

    private SecUtils() {
        throw new IllegalAccessError("工具类不能实例化");
    }

    /**
     * MD5加密(32位)
     * 
     * @param instr
     *            要加密的字符串
     * @return 返回加密后的字符串
     */
    public final static String encoderByMd5With32Bit(String instr) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            if (instr != null && !"".equals(instr)) {
                byte[] strTemp = instr.getBytes();
                // MD5计算方法
                MessageDigest mdTemp = MessageDigest.getInstance("MD5");
                mdTemp.update(strTemp);
                byte[] md = mdTemp.digest();
                int j = md.length;
                char str[] = new char[j * 2];
                int k = 0;
                for (int i = 0; i < j; i++) {
                    byte byte0 = md[i];
                    str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                    str[k++] = hexDigits[byte0 & 0xf];
                }
                return new String(str);
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 获取随机的UUID字符串
     * 
     * @return String
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
    
    /**
     * Base64加密
     * @param str 要加密的字符串
     * @return 返回加密后的字符串
     */
    public final static String encoderByBase64(String str) {
        return encoderByBase64(str.getBytes());
    }
    
    /**
     * Base64加密
     * @param str 要加密的字符串
     * @return 返回加密后的字符串
     */
    public final static String encoderByBase64(byte[] data) {
        try {
            Base64.Encoder encoder = Base64.getEncoder();
            String encode = encoder.encodeToString(data);  
            return encode;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
    
    /**
     * Base64解密
     * @param str 要解密的字符串
     * @return 返回解密后的字符串
     */
    public final static byte[] decoderByteByBase64(String str) {
        try {
            Base64.Decoder decoder = Base64.getDecoder();
           byte[] decode = decoder.decode(str);  
            return decode;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
    
    
    /**
     * Base64解密
     * @param str 要解密的字符串
     * @return 返回解密后的字符串
     */
    public final static String decoderByBase64(String str) {
        try {
            byte[] decoderByBase64 = decoderByteByBase64(str);
            if(decoderByBase64==null) {
                return null;
            }
            String decode = new String(decoderByBase64);  
            return decode;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
    
  
  
    
    
    

}
