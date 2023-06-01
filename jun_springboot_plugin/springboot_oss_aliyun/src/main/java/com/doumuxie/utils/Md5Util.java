package com.doumuxie.utils;

import java.net.URLEncoder;
import java.security.MessageDigest;

public class Md5Util {
    private static final String KEY_MD5 = "MD5";
    /**
     * 全局数组
     */
    private static final String[] strDigits = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    /**
     * 返回形式为数字跟字符串
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
     * 转换字节数组为16进制字串
     */
    private static String byteToString(byte[] bByte) {
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < bByte.length; i++) {
            sBuffer.append(byteToArrayString(bByte[i]));
        }
        return sBuffer.toString();
    }

    /**
     * MD5加密
     *
     * @param strObj
     * @return
     * @throws Exception
     */
    public static String getMD5Code(String strObj) throws Exception {
        MessageDigest md = MessageDigest.getInstance(KEY_MD5);
        // md.digest() 该函数返回值为存放哈希值结果的byte数组
        return byteToString(md.digest(strObj.getBytes()));
    }

    public static void main(String[] arge) throws Exception {
        String result = Md5Util.getMD5Code(URLEncoder.encode("18156119872"+"liang", "utf-8"));
        result = Md5Util.getMD5Code(result);
        result = Md5Util.getMD5Code(result);
        result = Md5Util.getMD5Code(result);
        result = Md5Util.getMD5Code(result);
        System.out.println(result);
    }

}
