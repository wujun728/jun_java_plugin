package org.itkk.udf.qrcode.option;

import lombok.Data;

import java.awt.image.BufferedImage;

/**
 * 背景图的配置信息
 */
@Data
public class BgImgOptions {

    /**
     * 背景图
     */
    private BufferedImage bgImg;

    /**
     * 背景图宽
     */
    private int bgw;

    /**
     * 背景图高
     */
    private int bgh;

    /**
     * 背景图样式
     */
    private BgImgStyle bgImgStyle;

    /**
     * if {@link #bgImgStyle} ==  QrCodeOptions.BgImgStyle.OVERRIDE，
     * 用于设置二维码的透明度
     */
    private float opacity;


    /**
     * if {@link #bgImgStyle} ==  QrCodeOptions.BgImgStyle.FILL
     * <p>
     * 用于设置二维码的绘制在背景图上的x坐标
     */
    private int startx;


    /**
     * if {@link #bgImgStyle} ==  QrCodeOptions.BgImgStyle.FILL
     * <p>
     * 用于设置二维码的绘制在背景图上的y坐标
     */
    private int starty;


    /**
     * 获得背景宽度
     *
     * @return 宽度
     */
    public int getBgw() {
        if (bgw == 0 && bgImg != null && bgImgStyle == BgImgStyle.FILL) {
            return bgImg.getWidth();
        }
        return bgw;
    }

    /**
     * 获得背景高度
     *
     * @return 高度
     */
    public int getBgh() {
        if (bgh == 0 && bgImg != null && bgImgStyle == BgImgStyle.FILL) {
            return bgImg.getHeight();
        }
        return bgh;
    }
}
