package com.xiruibin.db.util;

/**
 * String 常用操作工具集
 */
public final class StringUtils {

    /**
     * <code>
     * <p>
     * 判断一个String对象是否有长度.
     * </p>
     * NULL return false
     * ''   return false
     * " "  return false
     * </code>
     * 
     * @param s
     * @return
     */
    public static boolean hasLength(String s) {
        return !ObjectUtils.isNull(s) && s.trim().length() > 0;
    }
    
    /**
     * 把数组中的元素连接成一个字符串返回，把分隔符separator也加上。
     * @param array
     * @param separator
     * @return
     */
    public static String join(Object[] array, char separator) {
        if (array == null) {
            return null;
        }
        int arraySize = array.length;
        int bufSize = (arraySize == 0 ? 0 : ((array[0] == null ? 16 : array[0].toString().length()) + 1) * arraySize);
        StringBuffer buf = new StringBuffer(bufSize);

        for (int i = 0; i < arraySize; i++) {
            if (i > 0) {
                buf.append(separator);
            }
            if (array[i] != null) {
                buf.append(array[i]);
            }
        }
        return buf.toString();
    }
}
