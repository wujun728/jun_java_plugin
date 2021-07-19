package com.jun.plugin.picturemanage.util;


import java.util.UUID;

/**
 * Created by PuaChen
 *
 * @date 2018-11-12
 */
public class UUIDUtils {
    public static String[] chars = new String[]{"a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"};


    public static String generateShortUuid() {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 0x3E]);
        }
        return shortBuffer.toString();
    }

    /**
     * 生成 UUID 添加前缀
     *
     * @param prefix 前缀
     * @return
     * @Author Pua 陈
     */
    public static String generatePrefixUuid(String prefix) {
        if (prefix.endsWith("-") || prefix.endsWith("_")) {
            prefix = prefix.substring(0, prefix.length() - 1);
        }
        return prefix + "-" + generateShortUuid();
    }


    /**
     * 创建不连续的订单号
     *
     * @return 唯一的、不连续订单号
     */
    public static synchronized String getOrderNoByUUID() {
        Integer uuidHashCode = UUID.randomUUID().toString().hashCode();
        if (uuidHashCode < 0) {
            uuidHashCode = uuidHashCode * (-1);
        }
        return uuidHashCode + "";
    }

}
