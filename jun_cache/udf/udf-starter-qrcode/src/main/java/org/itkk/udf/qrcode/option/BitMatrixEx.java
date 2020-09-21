package org.itkk.udf.qrcode.option;

import com.google.zxing.qrcode.encoder.ByteMatrix;
import lombok.Data;

/**
 * 扩展的二维码矩阵信息， 主要新增了三个位置探测图形的判定
 */
@Data
public class BitMatrixEx {
    /**
     * 实际生成二维码的宽
     */
    private int width;


    /**
     * 实际生成二维码的高
     */
    private int height;
    
    /**
     * 左白边大小
     */
    private int leftPadding;

    /**
     * 上白边大小
     */
    private int topPadding;

    /**
     * 矩阵信息缩放比例
     */
    private int multiple;

    /**
     * 字节矩阵
     */
    private ByteMatrix byteMatrix;
}
