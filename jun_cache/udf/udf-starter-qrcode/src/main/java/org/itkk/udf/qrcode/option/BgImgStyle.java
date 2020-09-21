package org.itkk.udf.qrcode.option;

/**
 * 背景图样式
 */
public enum BgImgStyle {
    /**
     * 设置二维码透明度，然后全覆盖背景图
     */
    OVERRIDE,
    /**
     * 将二维码填充在背景图的指定位置
     */
    FILL;


    /**
     * 获得样式
     *
     * @param name name
     * @return 样式
     */
    public static BgImgStyle getStyle(String name) {
        return "fill".equalsIgnoreCase(name) ? FILL : OVERRIDE;
    }
}
