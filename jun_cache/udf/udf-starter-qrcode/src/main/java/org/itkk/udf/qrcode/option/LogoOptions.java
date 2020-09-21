package org.itkk.udf.qrcode.option;


import lombok.Data;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * logo 的配置信息
 */
@Data
public class LogoOptions {

    /**
     * logo 图片
     */
    private BufferedImage logo;

    /**
     * logo 样式
     */
    private LogoStyle logoStyle;

    /**
     * logo 占二维码的比例
     */
    private int rate;

    /**
     * true 表示有边框，
     * false 表示无边框
     */
    private boolean border;

    /**
     * 边框颜色
     */
    private Color borderColor;

}
