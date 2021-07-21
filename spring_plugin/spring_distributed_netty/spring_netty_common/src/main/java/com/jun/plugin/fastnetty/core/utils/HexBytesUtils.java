package com.jun.plugin.fastnetty.core.utils;

/**
 * @author Wujun
 */
public final class HexBytesUtils {

    private HexBytesUtils() {
    }

    /**
     * 这是将16进形式的byte[]要转化为字符串,就是要以两位的形式读取
     *
     * @param b 字节数组
     */
    public static final String byteArrayToHexString(byte[] b) {
        String ret = "";
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            ret += hex.toUpperCase() + " ";
        }
        return ret.substring(0, ret.length() - 1);
    }

    public static final String byteToHexString(byte b) {
        return byteArrayToHexString(new byte[]{b});
    }

    public static final String charToHexString(char c) {
        String hex = Integer.toHexString(c & 0xFFFF);
        int length = hex.length();
        for(int i = length; i < 4; i++) {
            hex = "0" + hex;
        }
        return hex.toUpperCase();
    }
}
