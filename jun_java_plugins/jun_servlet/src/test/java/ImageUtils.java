

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class ImageUtils {


	/**
	 * ��ȡ��ֵ��
	 * */
	public static	BufferedImage getBinImage(BufferedImage image){
		int h=image.getHeight();
		int w=image.getWidth();
		BufferedImage grayImage = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_BINARY);
	    for(int i= 0 ; i < w ; i++){  
	        for(int j = 0 ; j < h; j++){  
	        int rgb = image.getRGB(i, j);  
	        grayImage.setRGB(i, j, rgb);  
	        }  
	    }
		return grayImage;
	}
	/**
	 * ��ȡ�ҶȻ�
	 * */
	public 	static BufferedImage getGrayImage(BufferedImage image){
		int h=image.getHeight();
		int w=image.getWidth();
		BufferedImage grayImage = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_GRAY);
	    for(int i= 0 ; i < w ; i++){  
	        for(int j = 0 ; j < h; j++){  
	        int rgb = image.getRGB(i, j);  
	        grayImage.setRGB(i, j, rgb);  
	        }  
	    }
		return grayImage;
	}
	/**
	 * ��ȡ��ɫ
	 * */
	public 	static BufferedImage getFanImage(BufferedImage image){
		int h=image.getHeight();
		int w=image.getWidth();
		BufferedImage grayImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		int[][] arr=new int[w][h];
	    for(int i= 0 ; i < w ; i++){  
	        for(int j = 0 ; j < h; j++){  
			arr[i][j]=image.getRGB(i, j);
			grayImage.setRGB(i, j, 255-arr[i][j]);
	        }  
	    }
		return grayImage;
	}
	/**
	 * ����
	 * */
	public static BufferedImage getFloatImage(BufferedImage image,float x){
		int h=image.getHeight();
		int w=image.getWidth();
        BufferedImage tag = new BufferedImage((int)(w * x), (int)(h * x), BufferedImage.TYPE_INT_RGB);     
        Graphics2D g = tag.createGraphics();   
        
        g.drawImage(image, 0, 0, null); // ������С���ͼ   
        g.dispose();
        return tag;
	}
	
	/**
     * ��תͼƬΪָ���Ƕ�
     * 
     * @param bufferedimage
     *            Ŀ��ͼ��
     * @param degree
     *            ��ת�Ƕ�
     * @return
     */
    public static BufferedImage rotateImage(final BufferedImage bufferedimage,
            final int degree) {
        int w = bufferedimage.getWidth();
        int h = bufferedimage.getHeight();
        int type = bufferedimage.getColorModel().getTransparency();
        
        BufferedImage img;
        Graphics2D graphics2d;
        (graphics2d = (img = new BufferedImage(w, h, type))
                .createGraphics()).setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2d.rotate(Math.toRadians(degree), w / 2, h / 2);
        graphics2d.drawImage(bufferedimage, 0, 0, null);
        graphics2d.dispose();
        return img;
    }

    /**
     * ���ͼ��Ϊָ����С
     * 
     * @param bufferedimage
     *            Ŀ��ͼ��
     * @param w
     *            ��
     * @param h
     *            ��
     * @return
     */
    public static BufferedImage resizeImage(final BufferedImage bufferedimage,
            final int w, final int h) {
        int type = bufferedimage.getColorModel().getTransparency();
        BufferedImage img;
        Graphics2D graphics2d;
        (graphics2d = (img = createImage(w, h, type))
                .createGraphics()).setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2d.drawImage(bufferedimage, 0, 0, w, h, 0, 0, bufferedimage
                .getWidth(), bufferedimage.getHeight(), null);
        graphics2d.dispose();
        return img;
    }

    /**
     * ˮƽ��תͼ��
     * 
     * @param bufferedimage Ŀ��ͼ��
     * @return
     */
    public static BufferedImage flipImage(final BufferedImage bufferedimage) {
        int w = bufferedimage.getWidth();
        int h = bufferedimage.getHeight();
        BufferedImage img;
        Graphics2D graphics2d;
        (graphics2d = (img = createImage(w, h, bufferedimage
                .getColorModel().getTransparency())).createGraphics())
                .drawImage(bufferedimage, 0, 0, w, h, w, 0, 0, h, null);
        graphics2d.dispose();
        return img;
    }
    private static  BufferedImage createImage(int w,int h,int type){
    	return new BufferedImage(w,h, type);
    }
    /**
     * 
     * �ķ�ͼƬ ������֤����ͼ
     * 
     * 
     * */
    public static List<BufferedImage> getImagePies(final BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();
        List<BufferedImage> imgs = new ArrayList<BufferedImage>();
        int w1= w/4;
        BufferedImage grayImage = new BufferedImage(w1, h, BufferedImage.TYPE_INT_RGB);
		int rgb=0;
	    for(int i= 0 ; i < w1 ; i++){  
	        for(int j = 0 ; j < h; j++){  
	        	rgb=image.getRGB(i, j);
			grayImage.setRGB(i, j,rgb);
	        }  
	    }
	    imgs.add(grayImage);
        BufferedImage grayImage2 = new BufferedImage(w1, h, BufferedImage.TYPE_INT_RGB);
	    for(int i= 0 ; i < w1 ; i++){  
	        for(int j = 0 ; j < h; j++){  
	        	rgb=image.getRGB(i+w1, j);
			grayImage2.setRGB(i, j,rgb);
	        }  
	    }
	    imgs.add(grayImage2);
        BufferedImage grayImage3 = new BufferedImage(w1, h, BufferedImage.TYPE_INT_RGB);
	    for(int i= 0 ; i < w1 ; i++){  
	        for(int j = 0 ; j < h; j++){  
	        	rgb=image.getRGB(i+w1+w1, j);
			grayImage3.setRGB(i, j,rgb);
	        }  
	    }
	    imgs.add(grayImage3);
        BufferedImage grayImage4 = new BufferedImage(w1, h, BufferedImage.TYPE_INT_RGB);
	    for(int i= 0 ; i < w1 ; i++){  
	        for(int j = 0 ; j < h; j++){  
	        	rgb=image.getRGB(i+w1+w1+w1, j);
			grayImage4.setRGB(i, j,rgb);
	        }  
	    }
	    imgs.add(grayImage4);
        return imgs;
    }
    /**
     * ȥ���� + ��ֵ��
     * 
     * */
    public static BufferedImage getCleanImage(BufferedImage image){
    	 BufferedImage img=createImage(image.getWidth(), image.getHeight(),image.getType()); 
         int width = image.getWidth();  
         int height = image.getHeight();  
         int minx = image.getMinX();  
         int miny = image.getMinY();

         for (int i = minx; i < width; i++) {  
             for (int j = miny; j < height; j++) {  
                 int pixel = image.getRGB(i, j); // �������д��뽫һ������ת��ΪRGB����  
                 int r   = (pixel & 0xff0000) >> 16;  
                 int g = (pixel & 0xff00) >> 8;  
                 int b= (pixel & 0xff);  
                 if(-45<r-g&&r-g<45&&r-b>-45&&r-b<45){
                	 img.setRGB(i, j, RGB(255,255,255));	
                 }else{
                	 img.setRGB(i, j, RGB(0,0,0));
                 }
             }  
         }
    	 return img;
    }
	static int RGB(int r,int g,int b)
	{
	return r << 16 | g << 8 | b;
	}
}
