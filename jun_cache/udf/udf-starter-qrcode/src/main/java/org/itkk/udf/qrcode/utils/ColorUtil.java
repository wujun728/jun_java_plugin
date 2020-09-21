package org.itkk.udf.qrcode.utils;

import lombok.extern.slf4j.Slf4j;

import java.awt.*;

/**
 * ColorUtil
 */
@Slf4j
public class ColorUtil {

    /**
     * 私有化构造函数
     */
    private ColorUtil() {

    }

    /**
     * int格式的颜色转为 awt 的Color对象
     *
     * @param color 0xffffffff  前两位为透明读， 三四位 R， 五六位 G， 七八位 B
     * @return 颜色
     */
    public static Color int2color(int color) {
        final int numA = 0x7f000000;
        final int numB = 0x00000080;
        final int numC = 0x00ff0000;
        final int numD = 0x0000ff00;
        final int numE = 0x000000ff;
        final int num24 = 24;
        final int num16 = 16;
        final int num8 = 8;
        int a = ((numA & color) >> num24) | numB;
        int r = (numC & color) >> num16;
        int g = (numD & color) >> num8;
        int b = (numE & color);
        return new Color(r, g, b, a);
    }

}
