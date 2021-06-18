package com.sky.bluesky.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 描述：字符串工具
 * CLASSPATH: util.StringUtil
 * VERSION:   1.0
 * Created by lihao
 * DATE:      2016/12/29
 */
public class StringUtil {

    /**
     * @param list 要进行判断的数据集合
     * @return 不为空返回true，为空返回false
     * 方法说明：判断list集合是否为空
     * date:2016-12-29
     * author:lihao
     */
    public static boolean listIsNotNull(List list) {
        return ((null != list) && (list.size() > 0));
    }

    /**
     * @param src 要进行判断的字符串
     * @return 为空返回true，不为空为false
     * 方法说明：判断字符串是否为空
     * date:2016-12-29
     * author:lihao
     */
    public static boolean strIsNull(String src) {
        return (null == src) || "null".equals(src) || (src.trim().length() <= 0) || "".equals(src);
    }

    /**
     * Date 2017/1/4
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 判断字符串是否不为空
     *
     * @param src 要进行判断的字符串
     * @return 为空返回false，不为空为true
     */
    public static boolean strIsNotNull(String src) {

        return !strIsNull(src);
    }

    /**
     * Date 2017/1/3
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 将字符串转换为指定类型的list集合
     *
     * @param str   要转换的字符串
     * @param clazz 对象类型
     * @param <X>   泛型
     * @return 返回转换后的list集合
     */
    @SuppressWarnings("unchecked")
    public static <X> List<X> parseStrToList(String str, Class<X> clazz) {
        String[] a = str.split(",");
        List<X> b = new ArrayList<>();
        for (String anA : a) {
            Long c = Long.parseLong(anA);
            b.add((X) c);
        }
        return b;
    }


    /**
     * Date 2017/3/25
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 根据日期获取当前年份
     *
     * @param date 日期
     */
    public static String getCurrentYear(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        return sdf.format(date);
    }


}
