package org.springrain.frame.util;

import java.math.BigDecimal;

/**
 * 经纬度地理位置工具类
 * 
 * @author caomei
 *
 */
public class LocationUtils {

    // 以下为 获得 两点之间最短距离
    private static final BigDecimal EARTH_RADIUS = BigDecimal.valueOf(6378137);// 定义 地球半径 米
    private static final BigDecimal MATH_PI = BigDecimal.valueOf(Math.PI);// 定义Math.PI

    private LocationUtils() {
        throw new IllegalAccessError("工具类不能实例化");
    }

    /** 计算弧长 **/
    private static BigDecimal getRed(BigDecimal big) {
        return big.multiply(MATH_PI).divide(BigDecimal.valueOf(180.0), BigDecimal.ROUND_DOWN);
    }

    private static BigDecimal format(BigDecimal big, int scale) {
        scale = 0 > scale ? 0 : scale;
        big = big.setScale(scale, BigDecimal.ROUND_HALF_UP);
        return big;
    }

    /**
     * 计算 两点 之间 的 最短距离 <br/>
     * 返回 数据 为 两点之间的 米 数
     */
    public static BigDecimal getMinDistance(BigDecimal lat1, BigDecimal lng1, BigDecimal lat2, BigDecimal lng2) {

        BigDecimal radLat1 = getRed(lat1);
        BigDecimal radLat2 = getRed(lat2);
        BigDecimal a = radLat1.subtract(radLat2);
        BigDecimal b = getRed(lng1).subtract(getRed(lng2));

        Double sinA = Math.sin(a.doubleValue() / 2);
        Double sinB = Math.sin(b.doubleValue() / 2);
        Double cosA = radLat1.doubleValue();
        Double cosB = radLat2.doubleValue();

        Double distance = 2
                * Math.asin(Math.sqrt(Math.pow(sinA, 2) + Math.cos(cosA) * Math.cos(cosB) * Math.pow(sinB, 2)));
        BigDecimal s = BigDecimal.valueOf(distance);
        s = s.multiply(EARTH_RADIUS);
        return format(s, 3);
    }

}
