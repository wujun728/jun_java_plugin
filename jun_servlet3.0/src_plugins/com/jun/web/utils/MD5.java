package com.jun.web.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5����
 * @author jiang.li
 * @date 2013-12-17 14:09
 */
public class MD5 {

	 /**ȫ������**/
    private final static String[] strDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };

    /**
     * ������ʽΪ���ָ��ַ���
     * @param bByte
     * @return
     */
    private static String byteToArrayString(byte bByte) {
        int iRet = bByte;
        if (iRet < 0) {
            iRet += 256;
        }
        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return strDigits[iD1] + strDigits[iD2];
    }

    /**
     * ת���ֽ�����Ϊ16�����ִ�
     * @param bByte
     * @return
     */
    private static String byteToString(byte[] bByte) {
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < bByte.length; i++) {
            sBuffer.append(byteToArrayString(bByte[i]));
        }
        return sBuffer.toString();
    }

    /**
     * MD5����
     * @param str �����ܵ��ַ���
     * @return
     */
    public static String GetMD5Code(String str) {
        String result = null;
        try {
        	result = new String(str);
            MessageDigest md = MessageDigest.getInstance("MD5");
            result = byteToString(md.digest(str.getBytes()));
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return result;
    }
    
    /**
     * MD5����
     * @param str �����ܵ��ַ���
     * @param lowerCase ��Сд
     * @return
     */
    public static String GetMD5Code(String str,boolean lowerCase) {
        String result = null;
        try {
        	result = new String(str);
            MessageDigest md = MessageDigest.getInstance("MD5");
            result = byteToString(md.digest(str.getBytes()));
            if(lowerCase){
            	result = result.toLowerCase();	
            }
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return result;
    }

}
