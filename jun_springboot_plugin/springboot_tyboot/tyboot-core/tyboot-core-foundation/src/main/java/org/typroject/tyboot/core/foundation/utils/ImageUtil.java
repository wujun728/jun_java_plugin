package org.typroject.tyboot.core.foundation.utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.ImageIcon;



public class ImageUtil {
    /**
     * 图片缩放
     *
     * @param originalFile
     *            源文件
     * @param resizedFile
     *            目标文件
     * @param newWidth
     *            新图片的宽度
     * @param quality
     *            成像质量
     * @throws IOException
     */
    public void resize(String originalFile, String resizedFile, int newWidth,int newHeight, float quality) throws IOException {
        BufferedImage bufferedImage = resize(new File(originalFile), newWidth,newHeight, quality);
        savaImage(bufferedImage, resizedFile);
    }

    /**
     * 保存图像
     * @param bufferedImage
     * @param resizedFile
     * @throws IOException
     */
	public void savaImage(BufferedImage bufferedImage, String resizedFile)
            throws IOException {
        String type = resizedFile.substring(resizedFile.lastIndexOf(".")+1);
        ImageIO.write(bufferedImage, type, new File(resizedFile));
       
    }

    /**
     * 截取图片
     * @param filepath
     * @param newFilePath
     * @param x
     * @param y
     * @param width
     * @param height
     * @throws IOException
     */
    public void cutting(String filepath,String newFilePath, int x, int y,
                               int width, int height) throws IOException{
        FileInputStream is = null;
        ImageInputStream iis = null;
        ImageOutputStream out = null;
        try {
            File file = new File(filepath);
            String postFix = file.getName();
            // #1. 获取文件的后缀名
            postFix = postFix.substring(postFix.lastIndexOf(".") + 1);
            Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName(postFix);
            ImageReader reader = (ImageReader) readers.next();
            is = new FileInputStream(file);
            // #2. 获取图片流
            iis = ImageIO.createImageInputStream(is);
            
             // #3. <p>iis:读取源.true:只向前搜索 </p>.将它标记为 ‘只向前搜索’。 此设置意味着包含在输入源中的图像将只按顺序读取，
             // 可能允许 reader 避免缓存包含与以前已经读取的图像关联的数据的那些输入部分。

            reader.setInput(iis, true);

            // #4. <p>描述如何对流进行解码的类<p>.用于指定如何在输入时从 Java Image I/O 框架的上下文中的流转换一幅图像或一组图像。
            // 用于特定图像格式的插件 将从其 ImageReader 实现的 getDefaultReadParam 方法中返回 ImageReadParam 的实例。

            ImageReadParam param = reader.getDefaultReadParam();
            // #5. 图片裁剪区域。Rectangle 指定了坐标空间中的一个区域，通过 Rectangle 对象的左上顶点的坐标（x，y）、宽度和高度可以定义这个区域。

            Rectangle rect = new Rectangle(x, y, width, height);
            // #6. 提供一个 BufferedImage，将其用作解码像素数据的目标。
            param.setSourceRegion(rect);
             // #7.使用所提供的 ImageReadParam 读取通过索引 imageIndex 指定的对象，并将 它作为一个完整的BufferedImage 返回。

            BufferedImage bi = reader.read(0, param);
            bi.flush();
            ImageIO.write(bi, postFix, new File(newFilePath));

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (out != null){
                out.flush();
                out.close();
            }
            if (iis != null){
                iis.flush();
                iis.close();
            }
            if (is != null){
                is.close();
            }


        }

    }

    /**
     * 图片缩放
     * @param originalFile
     * @param newWidth
     * @param quality
     * @return
     * @throws IOException
     */
    public BufferedImage resize(File originalFile, int newWidth,int newHeight, float quality) throws IOException {

        ImageIcon ii = new ImageIcon(originalFile.getCanonicalPath());
        Image i = ii.getImage();
        Image resizedImage = null;

       /* int iWidth = i.getWidth(null);
        int iHeight = i.getHeight(null);

        if (iWidth > iHeight) {
            resizedImage = i.getScaledInstance(newWidth, (newWidth * iHeight)
                    / iWidth, Image.SCALE_AREA_AVERAGING);
        } else {
            resizedImage = i.getScaledInstance((newWidth * iWidth) / iHeight,
                    newWidth, Image.SCALE_AREA_AVERAGING);
        }*/

        resizedImage = i.getScaledInstance(newWidth, newHeight, Image.SCALE_AREA_AVERAGING);
        // #1.确保图片的每个像素被加载
        Image temp = new ImageIcon(resizedImage).getImage();

        // #2.创建buffered image.
        BufferedImage bufferedImage = new BufferedImage(temp.getWidth(null),temp.getHeight(null), BufferedImage.TYPE_INT_RGB);

        // #3.复制图像
        Graphics g = bufferedImage.createGraphics();

        // #4.清除图片背景色
        g.setColor(Color.white);
        g.fillRect(0, 0, temp.getWidth(null), temp.getHeight(null));
        g.drawImage(temp, 0, 0, null);
        g.dispose();

        float softenFactor = 0.05f;
        float[] softenArray = { 0, softenFactor, 0, softenFactor, 1 - (softenFactor * 4), softenFactor, 0, softenFactor, 0 };
        Kernel kernel = new Kernel(3, 3, softenArray);
        ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        bufferedImage = cOp.filter(bufferedImage, null);

        return bufferedImage;
    }
}
