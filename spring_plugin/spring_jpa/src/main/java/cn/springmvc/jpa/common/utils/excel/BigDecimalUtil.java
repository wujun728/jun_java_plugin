package cn.springmvc.jpa.common.utils.excel;

import java.math.BigDecimal;

/**
 * BigDecimal运算工具类
 * 
 * @author Wujun
 * 
 */
public class BigDecimalUtil {

    /**
     * BigDecimal加法运算
     * 
     * @param aVal
     *            加数
     * @param bVal
     *            被加数
     * @return 结果集
     */
    public static BigDecimal add(BigDecimal aVal, BigDecimal bVal) {
        aVal = aVal == null ? BigDecimal.ZERO : aVal;
        bVal = bVal == null ? BigDecimal.ZERO : bVal;
        return aVal.add(bVal);
    }

    /**
     * BigDecimal减法运算
     * 
     * @param aVal
     *            减数
     * @param bVal
     *            被减数
     * @return 结果集
     */
    public static BigDecimal subtract(BigDecimal aVal, BigDecimal bVal) {
        aVal = aVal == null ? BigDecimal.ZERO : aVal;
        bVal = bVal == null ? BigDecimal.ZERO : bVal;
        return aVal.subtract(bVal);
    }

    /**
     * BigDecimal乘法运算
     * 
     * @param aVal
     *            乘数
     * @param bVal
     *            被乘数
     * @return 结果集
     */
    public static BigDecimal multiply(BigDecimal aVal, BigDecimal bVal) {
        if (aVal == null || (aVal.compareTo(BigDecimal.ZERO) == 0))
            return aVal;
        if (bVal == null || (bVal.compareTo(BigDecimal.ZERO) == 0))
            return bVal;
        return aVal.multiply(bVal);
    }

    /**
     * BigDecimal 除法运算
     * 
     * @param aVal
     *            除数
     * @param bVal
     *            被除法
     * @return 结果集
     */
    public static BigDecimal divide(BigDecimal aVal, BigDecimal bVal) {
        if (aVal == null || (aVal.compareTo(BigDecimal.ZERO) == 0))
            return aVal;
        if (bVal == null || (bVal.compareTo(BigDecimal.ZERO) == 0))
            return bVal;
        return aVal.divide(bVal, 5, BigDecimal.ROUND_HALF_EVEN);
    }

    /**
     * Integer转换成BigDecimal
     * 
     * @param val
     * @return
     */
    public static BigDecimal integerToBigDecimal(Integer val) {
        if (val == null)
            return null;
        return new BigDecimal(val);
    }

    /**
     * 去除前后空格
     * 
     * @param str
     *            字符串
     * @return 处理后的结果
     */
    public static String parse(Object str) {
        if (str == null)
            return null;

        String string = str.toString().trim();
        while (string.startsWith("  ")) {
            string = string.substring(1, string.length()).trim();
        }
        return string;
    }

}
