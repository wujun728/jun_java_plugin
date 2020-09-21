package org.itkk.udf.qrcode.option;

/**
 * logo的样式
 */
public enum LogoStyle {
    /**
     * ROUND
     */
    ROUND,
    /**
     * NORMAL
     */
    NORMAL;

    /**
     * 获得样式
     *
     * @param name name
     * @return 样式
     */
    public static LogoStyle getStyle(String name) {
        return "ROUND".equalsIgnoreCase(name) ? ROUND : NORMAL;
    }
}