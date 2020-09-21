package org.itkk.udf.qrcode.utils;

import lombok.extern.slf4j.Slf4j;

/**
 * NumUtil
 */
@Slf4j
public class NumUtil {

    /**
     * 私有化构造函数
     */
    private NumUtil() {

    }

    /**
     * 将(十进制,八进制,十六进制) 字符串格式数字,转换为int
     *
     * @param str          str
     * @param defaultValue defaultValue
     * @return 结果
     */
    public static Integer decode2int(String str, Integer defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        try {
            //xxx 用Long转,可以避免 0xffee0011 这种解析的逸出 Integer.decode 只能转整数,负数会报错
            return Long.decode(str).intValue();
        } catch (Exception e) {
            return defaultValue;
        }
    }


}
