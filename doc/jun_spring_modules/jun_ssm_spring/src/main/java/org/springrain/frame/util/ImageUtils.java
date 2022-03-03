package org.springrain.frame.util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 图片工具类
 * 
 * @author caomei
 *
 */
public final class ImageUtils {

    private ImageUtils() {
        throw new IllegalAccessError("工具类不能实例化");
    }

    private static final Logger logger = LoggerFactory.getLogger(ImageUtils.class);

    /**
     * 图片水印
     * 
     * @param pressImg
     *            水印图片
     * @param targetImg
     *            目标图片
     * @param x
     *            修正值 默认在中间
     * @param y
     *            修正值 默认在中间
     * @param alpha
     *            透明度
     */
    public final static void pressImage(String pressImg, String targetImg, int x, int y, float alpha) {
        try {
            File img = new File(targetImg);
            Image src = ImageIO.read(img);
            int wideth = src.getWidth(null);
            int height = src.getHeight(null);
            BufferedImage image = new BufferedImage(wideth, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            g.drawImage(src, 0, 0, wideth, height, null);
            // 水印文件
            Image src_biao = ImageIO.read(new File(pressImg));
            int wideth_biao = src_biao.getWidth(null);
            int height_biao = src_biao.getHeight(null);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            g.drawImage(src_biao, (wideth - wideth_biao) / 2, (height - height_biao) / 2, wideth_biao, height_biao,
                    null);
            // 水印文件结束
            g.dispose();
            ImageIO.write((BufferedImage) image, "jpg", img);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 文字水印
     * 
     * @param pressText
     *            水印文字
     * @param targetImg
     *            目标图片
     * @param fontName
     *            字体名称
     * @param fontStyle
     *            字体样式
     * @param color
     *            字体颜色
     * @param fontSize
     *            字体大小
     * @param x
     *            修正值
     * @param y
     *            修正值
     * @param alpha
     *            透明度
     */
    public static void pressText(String pressText, String targetImg, String fontName, int fontStyle, Color color,
            int fontSize, int x, int y, float alpha) {
        try {
            File img = new File(targetImg);
            Image src = ImageIO.read(img);
            int width = src.getWidth(null);
            int height = src.getHeight(null);
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            g.drawImage(src, 0, 0, width, height, null);
            g.setColor(color);
            g.setFont(new Font(fontName, fontStyle, fontSize));
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            g.drawString(pressText, (width - (getLength(pressText) * fontSize)) / 2 + x, (height - fontSize) / 2 + y);
            g.dispose();
            ImageIO.write((BufferedImage) image, "jpg", img);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 缩放
     * 
     * @param filePath
     *            图片路径
     * @param height
     *            高度
     * @param width
     *            宽度
     * @param bb
     *            比例不对时是否需要补白
     */
    @SuppressWarnings("static-access")
    public static void resize(String filePath, int height, int width, boolean bb) {
        try {
            double ratio = 0.0; // 缩放比例
            File f = new File(filePath);
            // 获取图片后缀
            String fileName = f.getName();
            String prefix = f.getName().substring(fileName.lastIndexOf(".") + 1);

            BufferedImage bi = ImageIO.read(f);
            Image itemp = bi.getScaledInstance(width, height, bi.SCALE_SMOOTH);
            // 计算比例
            if ((bi.getHeight() > height) || (bi.getWidth() > width)) {
                if (bi.getHeight() > bi.getWidth()) {
                    ratio = (new Integer(height)).doubleValue() / bi.getHeight();
                } else {
                    ratio = (new Integer(width)).doubleValue() / bi.getWidth();
                }
                AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);
                itemp = op.filter(bi, null);
            }
            if (bb) {
                BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                Graphics2D g = image.createGraphics();
                g.setColor(Color.white);
                g.fillRect(0, 0, width, height);
                if (width == itemp.getWidth(null))
                    g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2, itemp.getWidth(null),
                            itemp.getHeight(null), Color.white, null);
                else
                    g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0, itemp.getWidth(null),
                            itemp.getHeight(null), Color.white, null);
                g.dispose();
                itemp = image;
            }
            // ImageIO.write((BufferedImage) itemp, "jpg", f);
            ImageIO.write((BufferedImage) itemp, prefix, f);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

   // public static void main(String[] args) throws IOException {
        // pressImage("G:\\imgtest\\sy.jpg", "G:\\imgtest\\test1.jpg", 0, 0, 0.5f);
        // pressText("我是文字水印", "D:/error.jpg", "黑体", 36, Color.white, 80, -10, 0,
        // 0.3f);
        // resize("G:\\imgtest\\test1.jpg", 500, 500, true);

        /*
         * int top=102; int left=175; int width=100; int height=50; cut(left, top,
         * width, height, "g:/1.jpg", "g:/100.jpg");
         */
   // }

    public static int getLength(String text) {
        int length = 0;
        for (int i = 0; i < text.length(); i++) {
            if (String.valueOf(text.charAt(i)).getBytes().length > 1) {
                length += 2;
            } else {
                length += 1;
            }
        }
        return length / 2;
    }

    /**
     * 图片裁切
     * 
     * @param x1
     *            选择区域左上角的x坐标
     * @param y1
     *            选择区域左上角的y坐标
     * @param width
     *            选择区域的宽度
     * @param height
     *            选择区域的高度
     * @param sourcePath
     *            源图片路径
     * @param descpath
     *            裁切后图片的保存路径
     */
    public static void cut(int x1, int y1, int width, int height, String sourcePath, String descpath) {
        FileInputStream is = null;
        ImageInputStream iis = null;
        try {
            is = new FileInputStream(GlobalStatic.rootDir + sourcePath);
            String fileSuffix = sourcePath.substring(sourcePath.lastIndexOf(".") + 1);
            Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName(fileSuffix);
            ImageReader reader = it.next();
            iis = ImageIO.createImageInputStream(is);
            reader.setInput(iis, true);
            ImageReadParam param = reader.getDefaultReadParam();
            Rectangle rect = new Rectangle(x1, y1, width, height);
            param.setSourceRegion(rect);
            BufferedImage bi = reader.read(0, param);
            ImageIO.write(bi, fileSuffix, new File(GlobalStatic.rootDir + descpath));
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
                is = null;
            }
            if (iis != null) {
                try {
                    iis.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
                iis = null;
            }
        }
    }
}
