package net.jueb.util4j.beta.tools.image;
import java.io.*;      
import java.awt.*;      
import java.awt.image.*;      
import java.awt.Graphics;      
import java.awt.color.ColorSpace;      
import javax.imageio.ImageIO;      
  
//import com.langhua.ImageUtils.ImageUtils;   
     
public class ChangeImageSize      
{      
    /** *//**    
     * 缩放图像    
     * @param srcImageFile 源图像文件地址    
     * @param result       缩放后的图像地址    
     * @param scale        缩放比例    
     * @param flag         缩放选择:true 放大; false 缩小;    
     */     
    public static void scale(String srcImageFile, String result, int scale, boolean flag)      
    {      
        try     
        {      
            BufferedImage src = ImageIO.read(new File(srcImageFile)); // 读入文件      
            int width = src.getWidth(); // 得到源图宽      
            int height = src.getHeight(); // 得到源图长      
            if (flag)      
            {      
                // 放大      
                width = width * scale;      
                height = height * scale;      
            }      
            else     
            {      
                // 缩小      
                width = width / scale;      
                height = height / scale;      
            }      
            Image image = src.getScaledInstance(width, height, Image.SCALE_DEFAULT);      
            //新建一个图像用于保存缩放后的图像
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);      
            Graphics g = tag.getGraphics();      
            g.drawImage(image, 0, 0, null); // 绘制缩小后的图      
            g.dispose();//释放系统资源    
            ImageIO.write(tag, "JPEG", new File(result));// 输出到文件流      
           
        }      
        catch (IOException e)      
        {      
            e.printStackTrace();      
        }      
    }      
     
    /**  
     * 图像切割    
     * @param srcImageFile 源图像地址    
     * @param descDir      切片目标文件夹    
     * @param destWidth    目标切片宽度    
     * @param destHeight   目标切片高度    
     */     
    public static void cut(String srcImageFile, String descDir, int destWidth, int destHeight)      
    {      
        try     
        {      
            Image img;      
            ImageFilter cropFilter;    
            String dir = null;   
            // 读取源图像      
            BufferedImage bi = ImageIO.read(new File(srcImageFile));      
            int srcWidth = bi.getHeight(); // 源图宽度      
            int srcHeight = bi.getWidth(); // 源图高度   
            System.out.println("srcWidth:"+srcWidth);   
            System.out.println("srcHeight:"+srcHeight);   
            if (srcWidth > destWidth && srcHeight > destHeight)      
            {      
                Image image = bi.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);      
                destWidth = 300; // 切片宽度      
                destHeight = 300; // 切片高度      
                int cols = 0; // 切片横向数量      
                int rows = 0; // 切片纵向数量      
                // 计算切片的横向和纵向数量      
                if (srcWidth % destWidth == 0)      
                {      
                    cols = srcWidth / destWidth;      
                }      
                else     
                {      
                    cols = (int) Math.floor(srcWidth / destWidth) + 1;      
                }      
                if (srcHeight % destHeight == 0)      
                {      
                    rows = srcHeight / destHeight;      
                }      
                else     
                {      
                    rows = (int) Math.floor(srcHeight / destHeight) + 1;      
                }      
                // 循环建立切片                  
                for (int i = 0; i < rows; i++)      
                {      
                    for (int j = 0; j < cols; j++)      
                    {      
                        // 四个参数分别为图像起点坐标和宽高      
                        // 即: CropImageFilter(int x,int y,int width,int height)      
                        cropFilter = new CropImageFilter(j * 300, i * 300, destWidth, destHeight);      
                        img = Toolkit.getDefaultToolkit().createImage(      
                                        new FilteredImageSource(image.getSource(), cropFilter));      
                        BufferedImage tag = new BufferedImage(destWidth, destHeight, BufferedImage.TYPE_INT_RGB);      
                        Graphics g = tag.getGraphics();      
                        g.drawImage(img, 0, 0, null); // 绘制缩小后的图      
                        g.dispose();      
                        // 输出为文件     
                        dir = descDir + "cut_image_" + i + "_" + j + ".jpg";   
                        File f = new File(dir);   
                        ImageIO.write(tag, "JPEG",f);   
                        System.out.println(dir);   
                       // ImageUtils.pressText("水印",dir,"宋体",1,1,25,10,10);   
                    }      
                }      
            }      
        }      
        catch (Exception e)      
        {      
            e.printStackTrace();      
        }      
    }      
     
    /**   
     * 图像类型转换 GIF->JPG GIF->PNG PNG->JPG PNG->GIF(X)    
     */     
    public static void convert(String source, String result)      
    {      
        try     
        {      
            File f = new File(source);      
            f.canRead();      
            f.canWrite();      
            BufferedImage src = ImageIO.read(f);      
            ImageIO.write(src, "JPG", new File(result));      
        }      
        catch (Exception e)      
        {      
            // TODO Auto-generated catch block      
            e.printStackTrace();      
        }      
    }      
     
    /**  
     * 彩色转为黑白    
     * @param source    
     * @param result    
     */     
    public static void gray(String source, String result)      
    {      
        try     
        {      
            BufferedImage src = ImageIO.read(new File(source));      
            ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);      
            ColorConvertOp op = new ColorConvertOp(cs, null);      
            src = op.filter(src, null);      
            ImageIO.write(src, "JPEG", new File(result));      
        }      
        catch (IOException e)      
        {      
            e.printStackTrace();      
        }      
    }      
     
    /**  
     * @param args    
     */     
    public static void main(String[] args)      
    {      
        //scale("c:\\test\\456.jpg","C:\\test\\image1.jpg",2,false);      
        //cut("c:\\1.jpg","C:\\2.jpg",64,64);      
        //gray("c:\\test\\456.jpg","C:\\test\\image4.jpg");      
    }      
     
}    
