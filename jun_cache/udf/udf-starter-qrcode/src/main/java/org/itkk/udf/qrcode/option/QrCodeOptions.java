package org.itkk.udf.qrcode.option;

import com.google.zxing.EncodeHintType;
import lombok.Data;

import java.util.Map;

/**
 * QrCodeOptions
 */
@Data
public class QrCodeOptions {

    /**
     * 默认size
     */
    public static final int DEF_SIZE = 200;

    /**
     * 默认文件类型
     */
    public static final String DEF_PIC_TYPE = "png";

    /**
     * 塞入二维码的信息
     */
    private String msg;

    /**
     * 生成二维码的宽
     */
    private Integer w;


    /**
     * 生成二维码的高
     */
    private Integer h;

    /**
     * 二维码信息(即传统二维码中的黑色方块) 绘制选项
     */
    private DrawOptions drawOptions;

    /**
     * logo 样式选项
     */
    private LogoOptions logoOptions;

    /**
     * 三个探测图形的样式选项
     */
    private DetectOptions detectOptions;

    /**
     * 背景图样式选项
     */
    private BgImgOptions bgImgOptions;

    /**
     * 编码提示类型
     */
    private Map<EncodeHintType, Object> hints;

}
