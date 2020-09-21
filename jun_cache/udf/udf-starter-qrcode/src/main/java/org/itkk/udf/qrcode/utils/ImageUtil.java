package org.itkk.udf.qrcode.utils;

import com.google.zxing.qrcode.encoder.ByteMatrix;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.itkk.udf.qrcode.option.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * ImageUtil
 */
@Slf4j
public class ImageUtil {

    /**
     * 私有化构造函数
     */
    private ImageUtil() {

    }

    /**
     * 绘制背景图
     *
     * @param source       二维码图
     * @param bgImgOptions 背景图信息
     * @return BufferedImage
     */
    public static BufferedImage drawBackground(BufferedImage source, BgImgOptions bgImgOptions) {
        int sw = source.getWidth();
        int sh = source.getHeight();
        // 背景的图宽高不应该小于原图
        int bgw = bgImgOptions.getBgw() < sw ? sw : bgImgOptions.getBgw();
        int bgh = bgImgOptions.getBgh() < sh ? sh : bgImgOptions.getBgh();
        // 背景图缩放
        BufferedImage bg = bgImgOptions.getBgImg();
        if (bg.getWidth() != bgw || bg.getHeight() != bgh) {
            BufferedImage temp = new BufferedImage(bgw, bgh, BufferedImage.TYPE_INT_ARGB);
            temp.getGraphics().drawImage(bg.getScaledInstance(bgw, bgh, Image.SCALE_SMOOTH), 0, 0, null);
            bg = temp;
        }
        Graphics2D g2d = bg.createGraphics();
        if (bgImgOptions.getBgImgStyle() == BgImgStyle.FILL) {
            // 选择一块区域进行填充
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 1.0f));
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.drawImage(source, bgImgOptions.getStartx(), bgImgOptions.getStarty(), sw, sh, null);
        } else {
            // 覆盖方式
            int x = (bgw - sw) >> 1;
            int y = (bgh - sh) >> 1;
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, bgImgOptions.getOpacity())); // 透明度， 避免看不到背景
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.drawImage(source, x, y, sw, sh, null);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 1.0f));
        }
        g2d.dispose();
        bg.flush();
        return bg;
    }

    /**
     * 绘制logo图片
     *
     * @param source      source
     * @param logoOptions logoOptions
     * @return BufferedImage
     */
    public static BufferedImage drawLogo(BufferedImage source, LogoOptions logoOptions) {
        //常量
        final int num2 = 2;
        //获得宽度和高度
        final int qrcodeWidth = source.getWidth();
        final int qrcodeHeight = source.getHeight();
        // 获取logo图片
        BufferedImage bf = logoOptions.getLogo();
        // 绘制圆角图片
        int radius = 0;
        if (logoOptions.getLogoStyle() == LogoStyle.ROUND) {
            radius = bf.getWidth() >> num2;
            bf = makeRoundedCorner(bf, radius);
        }
        // 绘制边框
        if (logoOptions.isBorder()) {
            bf = makeRoundBorder(bf, radius, logoOptions.getBorderColor());
        }
        // logo的宽高
        int logoRate = logoOptions.getRate();
        int w = bf.getWidth() > (qrcodeWidth << 1) / logoRate ? (qrcodeWidth << 1) / logoRate : bf.getWidth();
        int h = bf.getHeight() > (qrcodeHeight << 1) / logoRate ? (qrcodeHeight << 1) / logoRate : bf.getHeight();
        int x = (qrcodeWidth - w) >> 1;
        int y = (qrcodeHeight - h) >> 1;
        // 插入LOGO
        Graphics2D g2 = source.createGraphics();
        g2.setComposite(AlphaComposite.SrcAtop);
        g2.drawImage(bf, x, y, w, h, null);
        g2.dispose();
        bf.flush();
        return source;
    }

    /**
     * 生成边框
     *
     * @param image        原图
     * @param cornerRadius 角度（根据实测效果，一般建议为图片宽度的1/4）, 0表示直角
     * @param color        边框颜色
     * @return BufferedImage
     */
    public static BufferedImage makeRoundBorder(BufferedImage image, int cornerRadius, Color color) {
        final int num15 = 15;
        int size = image.getWidth() / num15;
        int w = image.getWidth() + size;
        int h = image.getHeight() + size;
        BufferedImage output = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = output.createGraphics();
        g2.setComposite(AlphaComposite.Src);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(color == null ? Color.WHITE : color);
        g2.fill(new RoundRectangle2D.Float(0, 0, w, h, cornerRadius, cornerRadius));
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 1.0f));
        g2.drawImage(image, size >> 1, size >> 1, null);
        g2.dispose();
        return output;
    }

    /**
     * 生成圆角图片
     *
     * @param image        原始图片
     * @param cornerRadius 圆角的弧度（根据实测效果，一般建议为图片宽度的1/4）, 0表示直角
     * @return 返回圆角图
     */
    public static BufferedImage makeRoundedCorner(BufferedImage image, int cornerRadius) {
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage output = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = output.createGraphics();
        g2.setComposite(AlphaComposite.Src);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        g2.fill(new RoundRectangle2D.Float(0, 0, w, h, cornerRadius, cornerRadius));
        g2.setComposite(AlphaComposite.SrcAtop);
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
        return output;
    }

    /**
     * 绘制二维码信息
     *
     * @param qrCodeConfig qrCodeConfig
     * @param bitMatrix    bitMatrix
     * @return BufferedImage
     */
    public static BufferedImage drawQrInfo(QrCodeOptions qrCodeConfig, BitMatrixEx bitMatrix) { //NOSONAR
        final int num5 = 5;
        final int num7 = 7;
        int qrCodeWidth = bitMatrix.getWidth();
        int qrCodeHeight = bitMatrix.getHeight();
        int infoSize = bitMatrix.getMultiple();
        BufferedImage qrCode = new BufferedImage(qrCodeWidth, qrCodeHeight, BufferedImage.TYPE_INT_RGB);
        // 绘制的背景色
        Color bgColor = qrCodeConfig.getDrawOptions().getBgColor();
        // 绘制前置色
        Color preColor = qrCodeConfig.getDrawOptions().getPreColor();
        // 探测图形外圈的颜色
        Color detectOutColor = qrCodeConfig.getDetectOptions().getOutColor();
        // 探测图形内圈的颜色
        Color detectInnerColor = qrCodeConfig.getDetectOptions().getInColor();
        //边框
        int leftPadding = bitMatrix.getLeftPadding();
        int topPadding = bitMatrix.getTopPadding();
        //设置画笔
        Graphics2D g2 = qrCode.createGraphics();
        g2.setComposite(AlphaComposite.Src);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // 直接背景铺满整个图
        g2.setColor(bgColor);
        g2.fillRect(0, 0, qrCodeWidth, qrCodeHeight);
        // 探测图形的大小
        int detectCornerSize = bitMatrix.getByteMatrix().get(0, num5) == 1 ? num7 : num5;
        //获得宽度和高度
        int bytew = bitMatrix.getByteMatrix().getWidth();
        int byteh = bitMatrix.getByteMatrix().getHeight();
        //支持拓展时
        boolean row2;
        boolean col2;
        DrawStyle drawStyle = qrCodeConfig.getDrawOptions().getDrawStyle();
        for (int x = 0; x < bytew; x++) {
            for (int y = 0; y < byteh; y++) { //NOSONAR
                if (bitMatrix.getByteMatrix().get(x, y) == 0) {
                    continue;
                }
                // 设置三个位置探测图形
                if (x < detectCornerSize && y < detectCornerSize // 左上角
                        || (x < detectCornerSize && y >= byteh - detectCornerSize) // 左下脚
                        || (x >= bytew - detectCornerSize && y < detectCornerSize)) { // 右上角
                    if (qrCodeConfig.getDetectOptions().getDetectImg() != null) {
                        g2.drawImage(qrCodeConfig.getDetectOptions().getDetectImg(),
                                leftPadding + x * infoSize, topPadding + y * infoSize,
                                infoSize * detectCornerSize, infoSize * detectCornerSize, null);
                        for (int addx = 0; addx < detectCornerSize; addx++) {
                            for (int addy = 0; addy < detectCornerSize; addy++) {
                                bitMatrix.getByteMatrix().set(x + addx, y + addy, 0);
                            }
                        }
                        continue;
                    }
                    if (x == 0 || x == detectCornerSize - 1 || x == bytew - 1 || x == bytew - detectCornerSize
                            || y == 0 || y == detectCornerSize - 1 || y == byteh - 1 || y == byteh - detectCornerSize) {
                        // 外层的框
                        g2.setColor(detectOutColor);
                    } else {
                        // 内层的框
                        g2.setColor(detectInnerColor);
                    }
                    g2.fillRect(leftPadding + x * infoSize, topPadding + y * infoSize, infoSize, infoSize);
                } else { // 着色二维码主题
                    g2.setColor(preColor);
                    if (!qrCodeConfig.getDrawOptions().isEnableScale()) {
                        drawStyle.draw(g2,
                                leftPadding + x * infoSize,
                                topPadding + y * infoSize,
                                infoSize,
                                infoSize,
                                qrCodeConfig.getDrawOptions().getImg());
                        continue;
                    }
                    // 支持拓展时
                    row2 = rightTrue(bitMatrix.getByteMatrix(), x, y);
                    col2 = belowTrue(bitMatrix.getByteMatrix(), x, y);

                    if (row2 && col2 && diagonalTrue(bitMatrix.getByteMatrix(), x, y) &&
                            qrCodeConfig.getDrawOptions().enableScale(ExpandType.SIZE4)) {
                        // 四个相等
                        bitMatrix.getByteMatrix().set(x + 1, y, 0);
                        bitMatrix.getByteMatrix().set(x + 1, y + 1, 0);
                        bitMatrix.getByteMatrix().set(x, y + 1, 0);
                        drawStyle.draw(g2,
                                leftPadding + x * infoSize,
                                topPadding + y * infoSize,
                                infoSize << 1,
                                infoSize << 1,
                                qrCodeConfig.getDrawOptions().getSize4Img());
                    } else if (row2 && qrCodeConfig.getDrawOptions().enableScale(ExpandType.ROW2)) { // 横向相同
                        bitMatrix.getByteMatrix().set(x + 1, y, 0);
                        drawStyle.draw(g2,
                                leftPadding + x * infoSize,
                                topPadding + y * infoSize,
                                infoSize << 1,
                                infoSize,
                                qrCodeConfig.getDrawOptions().getRow2Img());
                    } else if (col2 && qrCodeConfig.getDrawOptions().enableScale(ExpandType.COL2)) { // 列的两个
                        bitMatrix.getByteMatrix().set(x, y + 1, 0);
                        drawStyle.draw(g2,
                                leftPadding + x * infoSize,
                                topPadding + y * infoSize,
                                infoSize,
                                infoSize << 1,
                                qrCodeConfig.getDrawOptions().getCol2img());
                    } else {
                        drawStyle.draw(g2,
                                leftPadding + x * infoSize,
                                topPadding + y * infoSize,
                                infoSize,
                                infoSize,
                                qrCodeConfig.getDrawOptions().getImg());
                    }
                }
            }
        }
        g2.dispose();
        return qrCode;
    }

    /**
     * 根据path获取文件流
     *
     * @param path path
     * @return 流
     * @throws IOException IOException
     */
    public static BufferedImage getByPath(String path) throws IOException {
        //判空
        if (StringUtils.isBlank(path)) {
            return null;
        }
        //网络资源
        if (path.startsWith("http") || path.startsWith("https")) {
            return ImageIO.read(HttpUtil.load(path, "GET"));
        }
        //本地资源
        File imageFile = new File(path);
        if (imageFile.exists()) {
            return ImageIO.read(imageFile);
        }
        //项目资源
        return ImageIO.read(ImageUtil.class.getClassLoader().getResourceAsStream(path));
    }

    /**
     * rightTrue
     *
     * @param byteMatrix byteMatrix
     * @param x          x
     * @param y          y
     * @return boolean
     */
    private static boolean rightTrue(ByteMatrix byteMatrix, int x, int y) {
        return x + 1 < byteMatrix.getWidth() && byteMatrix.get(x + 1, y) == 1;
    }

    /**
     * belowTrue
     *
     * @param byteMatrix byteMatrix
     * @param x          x
     * @param y          y
     * @return boolean
     */
    private static boolean belowTrue(ByteMatrix byteMatrix, int x, int y) {
        return y + 1 < byteMatrix.getHeight() && byteMatrix.get(x, y + 1) == 1;
    }

    /**
     * 对角是否相等
     *
     * @param byteMatrix byteMatrix
     * @param x          x
     * @param y          y
     * @return boolean
     */
    private static boolean diagonalTrue(ByteMatrix byteMatrix, int x, int y) {
        return byteMatrix.get(x + 1, y + 1) == 1;
    }
}
