package org.itkk.udf.qrcode.option;

import org.apache.commons.lang.StringUtils;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * 绘制二维码信息的样式
 */
public enum DrawStyle {
    /**
     * 矩形
     */
    RECT {
        @Override
        public void draw(Graphics2D g2d, int x, int y, int w, int h, BufferedImage img) {
            g2d.fillRect(x, y, w, h);
        }

        @Override
        public boolean expand(ExpandType expandType) {
            return true;
        }
    },
    /**
     * 圆点
     */
    CIRCLE {
        @Override
        public void draw(Graphics2D g2d, int x, int y, int w, int h, BufferedImage img) {
            g2d.fill(new Ellipse2D.Float(x, y, w, h));
        }

        @Override
        public boolean expand(ExpandType expandType) {
            return expandType == ExpandType.SIZE4;
        }
    },
    /**
     * 三角形
     */
    TRIANGLE {
        @Override
        public void draw(Graphics2D g2d, int x, int y, int w, int h, BufferedImage img) {
            final int nPoints = 3;
            int[] px = {x, x + (w >> 1), x + w};
            int[] py = {y + w, y, y + w};
            g2d.fillPolygon(px, py, nPoints);
        }

        @Override
        public boolean expand(ExpandType expandType) {
            return false;
        }
    },
    /**
     * 五边形-钻石
     */
    DIAMOND {
        @Override
        public void draw(Graphics2D g2d, int x, int y, int size, int h, BufferedImage img) {
            final int one = 1;
            final int two = 2;
            final int nPoints = 5;
            int cell4 = size >> two;
            int cell2 = size >> one;
            int[] px = {x + cell4, x + size - cell4, x + size, x + cell2, x};
            int[] py = {y, y, y + cell2, y + size, y + cell2};
            g2d.fillPolygon(px, py, nPoints);
        }

        @Override
        public boolean expand(ExpandType expandType) {
            return expandType == ExpandType.SIZE4;
        }
    },
    /**
     * 六边形
     */
    SEXANGLE {
        @Override
        public void draw(Graphics2D g2d, int x, int y, int size, int h, BufferedImage img) {
            final int two = 2;
            int add = size >> two;
            final int nPoints = 6;
            int[] px = {x + add, x + size - add, x + size, x + size - add, x + add, x};
            int[] py = {y, y, y + add + add, y + size, y + size, y + add + add};
            g2d.fillPolygon(px, py, nPoints);
        }

        @Override
        public boolean expand(ExpandType expandType) {
            return expandType == ExpandType.SIZE4;
        }
    },
    /**
     * 八边形
     */
    OCTAGON {
        @Override
        public void draw(Graphics2D g2d, int x, int y, int size, int h, BufferedImage img) {
            final int three = 3;
            final int nPoints = 8;
            int add = size / three;
            int[] px = {x + add, x + size - add, x + size, x + size, x + size - add, x + add, x, x};
            int[] py = {y, y, y + add, y + size - add, y + size, y + size, y + size - add, y + add};
            g2d.fillPolygon(px, py, nPoints);
        }

        @Override
        public boolean expand(ExpandType expandType) {
            return expandType == ExpandType.SIZE4;
        }
    },
    /**
     * 自定义图片
     */
    IMAGE {
        @Override
        public void draw(Graphics2D g2d, int x, int y, int w, int h, BufferedImage img) {
            g2d.drawImage(img, x, y, w, h, null);
        }

        @Override
        public boolean expand(ExpandType expandType) {
            return true;
        }
    };

    /**
     * map
     */
    private static Map<String, DrawStyle> map;

    static {
        final int initialCapacity = 10;
        map = new HashMap<>(initialCapacity);
        for (DrawStyle style : DrawStyle.values()) {
            map.put(style.name(), style);
        }
    }

    /**
     * 返回样式
     *
     * @param name 样式名称
     * @return 样式
     */
    public static DrawStyle getDrawStyle(String name) {
        if (StringUtils.isBlank(name)) { // 默认返回矩形
            return RECT;
        }
        DrawStyle style = map.get(name.toUpperCase());
        return style == null ? RECT : style;
    }

    /**
     * 绘制
     *
     * @param g2d 绘制对象
     * @param x   x
     * @param y   y
     * @param w   w
     * @param h   h
     * @param img img
     */
    public abstract void draw(Graphics2D g2d, int x, int y, int w, int h, BufferedImage img);


    /**
     * 返回是否支持绘制图形的扩展
     *
     * @param expandType expandType
     * @return boolean
     */
    abstract boolean expand(ExpandType expandType);
}
