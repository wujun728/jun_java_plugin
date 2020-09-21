package org.itkk.udf.qrcode.option;

import lombok.Data;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 探测图形的配置信息
 */
@Data
public class DetectOptions {

    /**
     * 探测图形外边框颜色
     */
    private Color outColor;

    /**
     * 探测图形内边框颜色
     */
    private Color inColor;

    /**
     * 探测图形，优先级高于颜色的定制（即存在图片时，用图片绘制探测图形）
     */
    private BufferedImage detectImg;
}
