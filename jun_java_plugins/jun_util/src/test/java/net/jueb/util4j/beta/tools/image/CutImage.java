package net.jueb.util4j.beta.tools.image;
import java.awt.Rectangle;   
import java.awt.image.BufferedImage;   
import java.io.File;   
import java.io.FileInputStream;   
import java.io.IOException;   
import java.util.Iterator;   
  
import javax.imageio.ImageIO;   
import javax.imageio.ImageReadParam;   
import javax.imageio.ImageReader;   
import javax.imageio.stream.ImageInputStream;   
  
//import com.langhua.ImageUtils.ImageUtils;   
  
public class CutImage {   
  
    // 源图片路径名称如:c:\1.jpg   
    private String srcpath;   
  
    // 剪切图片存放路径名称.如:c:\2.jpg   
    private String subpath;   
  
    // 剪切点x坐标   
    private int x;   
  
    private int y;   
  
    // 剪切点宽度   
    private int width;   
  
    private int height;   
  
    public CutImage() {   
  
    }   
  
    public CutImage(int x, int y, int width, int height) {   
        this.x = x;   
        this.y = y;   
        this.width = width;   
        this.height = height;   
    }   
  
    /**  
     * 对图片裁剪，并把裁剪完蛋新图片保存 。  
     */  
    public void cut() throws IOException {   
  
        FileInputStream is = null;   
        ImageInputStream iis = null;   
  
        try {   
            // 读取图片文件   
            is = new FileInputStream(srcpath);   
  
            /**  
             * 返回包含所有当前已注册 ImageReader 的 Iterator，这些 ImageReader 声称能够解码指定格式。  
             * 参数：formatName - 包含非正式格式名称 . （例如 "jpeg" 或 "tiff"）等 。  
             */  
            Iterator<ImageReader> it = ImageIO   
                    .getImageReadersByFormatName("jpg");   
            ImageReader reader = it.next();   
            // 获取图片流   
            iis = ImageIO.createImageInputStream(is);   
  
            /**  
             * iis:读取源.true:只向前搜索.将它标记为 ‘只向前搜索’。 此设置意味着包含在输入源中的图像将只按顺序读取，可能允许  
             * reader 避免缓存包含与以前已经读取的图像关联的数据的那些输入部分。  
             */  
            reader.setInput(iis, true);   
  
            /**  
             * <p>  
             * 描述如何对流进行解码的类  
             * <p>  
             * .用于指定如何在输入时从 Java Image I/O 框架的上下文中的流转换一幅图像或一组图像。用于特定图像格式的插件 将从其  
             * ImageReader 实现的 getDefaultReadParam 方法中返回 ImageReadParam 的实例。  
             */  
            ImageReadParam param = reader.getDefaultReadParam();   
  
            /**  
             * 图片裁剪区域。Rectangle 指定了坐标空间中的一个区域，通过 Rectangle 对象  
             * 的左上顶点的坐标（x，y）、宽度和高度可以定义这个区域。  
             */  
            Rectangle rect = new Rectangle(x, y, width, height);   
  
            // 提供一个 BufferedImage，将其用作解码像素数据的目标。   
            param.setSourceRegion(rect);   
  
            /**  
             * 使用所提供的 ImageReadParam 读取通过索引 imageIndex 指定的对象，并将 它作为一个完整的  
             * BufferedImage 返回。  
             */  
            BufferedImage bi = reader.read(0, param);   
  
            // 保存新图片   
            ImageIO.write(bi, "jpg", new File(subpath));   
        } finally {   
            if (is != null)   
                is.close();   
            if (iis != null)   
                iis.close();   
        }   
    }   
  
    /**  
     * 图像切割  
     *   
     * @param srcImageFile  
     *            源图像地址  
     * @param descDir  
     *            切片目标文件夹  
     * @param destWidth  
     *            目标切片宽度  
     * @param destHeight  
     *            目标切片高度 返回一个List,保存九张图片的图片名  
     */  
    public static java.util.List<String> cutImg(String srcImageFile,   
            String descDir, int destWidth, int destHeight) {   
        java.util.List<String> list = new java.util.ArrayList<String>(9);   
        try {   
            String dir = null;   
            // 读取源图像   
            BufferedImage bi = ImageIO.read(new File(srcImageFile));   
            int srcWidth = bi.getHeight(); // 源图宽度   
            int srcHeight = bi.getWidth(); // 源图高度   
            if (srcWidth > destWidth && srcHeight > destHeight) {   
                destWidth = 300; // 切片宽度   
                destHeight = 300; // 切片高度   
                int cols = 0; // 切片横向数量   
                int rows = 0; // 切片纵向数量   
  
                // 计算切片的横向和纵向数量   
                if (srcWidth % destWidth == 0) {   
                    cols = srcWidth / destWidth;   
                } else {   
                    cols = (int) Math.floor(srcWidth / destWidth) + 1;   
                }   
                if (srcHeight % destHeight == 0) {   
                    rows = srcHeight / destHeight;   
                } else {   
                    rows = (int) Math.floor(srcHeight / destHeight) + 1;   
                }   
                // 循环建立切片   
                for (int i = 0; i < rows; i++) {   
                    for (int j = 0; j < cols; j++) {   
                        CutImage cutImage = new CutImage(j * 300, i * 300, 300,   
                                300);   
                        cutImage.setSrcpath(srcImageFile);   
                        dir = descDir + "cut_image_" + i + "_" + j + ".jpg";   
                        cutImage.setSubpath(dir);   
                        cutImage.cut();   
                        list.add("cut_image_" + i + "_" + j + ".jpg");   
                        //ImageUtils.pressText("水印", dir, "宋体", 1, 1, 25, 10, 10);   
                    }   
                }   
            }   
        } catch (Exception e) {   
            e.printStackTrace();   
        }   
        return list;   
    }   
  
    public int getHeight() {   
        return height;   
    }   
  
    public void setHeight(int height) {   
        this.height = height;   
    }   
  
    public String getSrcpath() {   
        return srcpath;   
    }   
  
    public void setSrcpath(String srcpath) {   
        this.srcpath = srcpath;   
    }   
  
    public String getSubpath() {   
        return subpath;   
    }   
  
    public void setSubpath(String subpath) {   
        this.subpath = subpath;   
    }   
  
    public int getWidth() {   
        return width;   
    }   
  
    public void setWidth(int width) {   
        this.width = width;   
    }   
  
    public int getX() {   
        return x;   
    }   
  
    public void setX(int x) {   
        this.x = x;   
    }   
  
    public int getY() {   
        return y;   
    }   
  
    public void setY(int y) {   
        this.y = y;   
    }   
}  
