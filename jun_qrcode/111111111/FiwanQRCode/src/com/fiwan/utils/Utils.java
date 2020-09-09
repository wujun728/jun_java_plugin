package com.fiwan.utils;

import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;

/**
 * @author frogchou
 * @version 1.0
 * 工具类存放一些常用的方法
 * */
public class Utils {
	
	/**
	 * 返回程序当前被存放的路径
	 * @return String 程序当前被存放的路径
	 * */
	public static String getCurrentPath() {
		return System.getProperty("user.dir")+File.separator+"images";
	}

	/**
	 * 创建一个文件，并返回文件的路径
	 * @param filetype 文件类型（后缀）
	 * @param filename 文件名
	 * @return String 返回文件的路径
	 * */
	public static String CreateFile(String filetype, String filename) {
		File file = null;
		try {
			File filepath = new File(getCurrentPath());
			if (!filepath.exists())
				filepath.mkdirs();
			file = new File(filepath + File.separator + filename + "."
					+ filetype);
			if (!file.exists())
				file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file.getAbsolutePath();
	}
	
	/**
	 * 创建一个目录，并返回目录的路径
	 * @param filePathName 文件路径
	 * @return String 返回文件的路径
	 * */
	public static String CreateFilePathAndFile(String filePathName,String filetype, String filename) {
		File file = null;
		File filePath=null;
		File programPath = new File(getCurrentPath());
		if (!programPath.exists())
			programPath.mkdirs();
		filePath = new File(programPath+ File.separator +filePathName);
		if (!filePath.exists())
			filePath.mkdirs();
		file = new File(filePath + File.separator + filename + "."
				+ filetype);
		if (!file.exists())
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		return file.getAbsolutePath();
	}

	/**
	 * 把转入的图片资源设置到剪切板上
	 * 
	 * @param image
	 *            可以是image 或者是BufferedImage类型
	 */
	// 给剪切板设置图片型内容
	public static void setImageClipboard(Image image) {
		Images imgSel = new Images(image);
		// 设置
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(imgSel,
				null);
	}
	
    /** 
     * 从剪切板获得图片。 
     */  
    public static Image getImageFromClipboard() throws Exception {  
        Clipboard sysc = Toolkit.getDefaultToolkit().getSystemClipboard();  
        Transferable cc = sysc.getContents(null);  
        if (cc == null)  
            return null;  
        else if (cc.isDataFlavorSupported(DataFlavor.imageFlavor))  
            return (Image) cc.getTransferData(DataFlavor.imageFlavor);  
        return null;  
    }  
    
    /**
     * Image 转 BufferedImage
     * */
    public static BufferedImage toBufferedImage(Image image) {  
        if (image instanceof BufferedImage) {  
            return (BufferedImage)image;  
         }  
        
        // This code ensures that all the pixels in the image are loaded  
         image = new ImageIcon(image).getImage();  
        
        // Determine if the image has transparent pixels; for this method's  
        // implementation, see e661 Determining If an Image Has Transparent Pixels  
        //boolean hasAlpha = hasAlpha(image);  
        
        // Create a buffered image with a format that's compatible with the screen  
         BufferedImage bimage = null;  
         GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();  
        try {  
            // Determine the type of transparency of the new buffered image  
            int transparency = Transparency.OPAQUE;  
           /* if (hasAlpha) { 
             transparency = Transparency.BITMASK; 
             }*/  
        
            // Create the buffered image  
             GraphicsDevice gs = ge.getDefaultScreenDevice();  
             GraphicsConfiguration gc = gs.getDefaultConfiguration();  
             bimage = gc.createCompatibleImage(  
             image.getWidth(null), image.getHeight(null), transparency);  
         } catch (HeadlessException e) {  
            // The system does not have a screen  
         }  
        
        if (bimage == null) {  
            // Create a buffered image using the default color model  
            int type = BufferedImage.TYPE_INT_RGB;  
            //int type = BufferedImage.TYPE_3BYTE_BGR;//by wang  
            /*if (hasAlpha) { 
             type = BufferedImage.TYPE_INT_ARGB; 
             }*/  
             bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);  
         }  
        
        // Copy image to buffered image  
         Graphics g = bimage.createGraphics();  
        
        // Paint the image onto the buffered image  
         g.drawImage(image, 0, 0, null);  
         g.dispose();  
        
        return bimage;  
    }  
	
	public static class Images implements Transferable {
		private Image image; // 得到图片或者图片流
		public Images(Image image) {
			this.image = image;
		}
		public DataFlavor[] getTransferDataFlavors() {
			return new DataFlavor[] { DataFlavor.imageFlavor };
		}
		public boolean isDataFlavorSupported(DataFlavor flavor) {
			return DataFlavor.imageFlavor.equals(flavor);
		}
		public Object getTransferData(DataFlavor flavor)
				throws UnsupportedFlavorException, IOException {
			if (!DataFlavor.imageFlavor.equals(flavor)) {
				throw new UnsupportedFlavorException(flavor);
			}
			return image;
		}
	}
}
