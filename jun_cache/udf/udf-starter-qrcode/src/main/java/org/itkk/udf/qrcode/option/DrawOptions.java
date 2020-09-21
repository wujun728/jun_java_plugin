package org.itkk.udf.qrcode.option;

import lombok.Data;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 绘制二维码的配置信息
 */
@Data
public class DrawOptions {

    /**
     * 着色颜色
     */
    private Color preColor;

    /**
     * 背景颜色
     */
    private Color bgColor;

    /**
     * 绘制样式
     */
    private DrawStyle drawStyle;

    /**
     * true 时表示支持对相邻的着色点进行合并处理 （即用一个大图来绘制相邻的两个着色点）
     * <p>
     * 说明： 三角形样式关闭该选项，因为留白过多，对识别有影响
     */
    private boolean enableScale;

    /**
     * 基础图片
     */
    private BufferedImage img;

    /**
     * 同一行相邻的两个着色点对应绘制的图片
     */
    private BufferedImage row2Img;

    /**
     * 同一列相邻的两个着色点对应绘制的图片
     */
    private BufferedImage col2img;

    /**
     * 以(x,y)为左定点的四个着色点对应绘制的图片
     */
    private BufferedImage size4Img;

    /**
     * 是否启用缩放
     *
     * @param expandType expandType
     * @return 结果
     */
    public boolean enableScale(ExpandType expandType) {
        if (!enableScale || !drawStyle.expand(expandType)) {
            return false;
        }

        if (drawStyle != DrawStyle.IMAGE) {
            return true;
        }

        switch (expandType) {
            case SIZE4:
                return size4Img != null;
            case COL2:
                return col2img != null;
            default:
                return row2Img != null;
        }
    }
}
