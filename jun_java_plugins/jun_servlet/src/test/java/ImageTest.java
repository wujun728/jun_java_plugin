

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class ImageTest {
	public static void XX(String[] args) throws IOException {
		
	}
	public static void main(String[] args) throws IOException {
		BufferedImage  image1= ImageIO.read(new File("E:/test3.png"));
		BufferedImage  image2= ImageIO.read(new File("E:/test33.png"));
//	    ImageIO.write(ImageUtils.getGrayImage(image), "png", o);
//        image=ImageUtils.getCleanImage(image);
//        List<BufferedImage> lists=ImageUtils.getImagePies(image);
        int width = image1.getWidth();  
        int height = image1.getHeight();  
        int minx = image1.getMinX();  
        int miny = image1.getMinY();
        int point=0;
        int total=0;
        for (int i = minx; i < width; i++) {  
            for (int j = miny; j < height; j++) {  
                int pixel = image1.getRGB(i, j); // �������д��뽫һ������ת��ΪRGB����  
                int r1   = (pixel & 0xff0000) >> 16;  
                int g1 = (pixel & 0xff00) >> 8;  
                int b1= (pixel & 0xff);  
                int pixel2 = image2.getRGB(i, j); // �������д��뽫һ������ת��ΪRGB����  
                int r2   = (pixel2 & 0xff0000) >> 16;  
                int g2 = (pixel2 & 0xff00) >> 8;  
                int b2= (pixel2 & 0xff);
                
                	if(intLike(r1,255)&&intLike(g1,255)&&intLike(b1,255)){
                		continue;
                	}else{
                		if(intLike(r1,r2)&&intLike(g1,g2)&&intLike(b1,b2)){
                			point++;
                		}
                		total++;
                	}
                	
                }      
        	}
       System.out.println("*************************ͼƬ���ƶ�Ϊ ��"+1.0f*point/total);
       
	}
	static boolean intLike(int x,int y){
		if(x-y>-10&&x-y<10){
			return true;
		}else {
			return false;
		}
	}
}
