package com.jun.plugin.file.fileupload;
/**
 * ????????
 */

/**
 * @author advance
 *
 */
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;



public class UploadHelper {
	
	public final static String separator = "/";
	public final static String split = "_";
	

	class FilenameFilterImpl implements FilenameFilter 
	{
		private String filter = ".";
		
		public FilenameFilterImpl(String aFilter)
		{
			filter = aFilter;
		}
		
    	public boolean accept(File dir, String name) 
    	{
        	return name.startsWith(filter);
    	}
	};
	/**
	 * ��õ�ǰ���ļ�·����ͨ����ǰ�������ɣ�?
	 * @param basePath
	 * @return
	 */
	public static String getNowFilePath(String basePath){
		SimpleDateFormat formater =new SimpleDateFormat("yyyy-MM-dd");
		String pathName = formater.format(new Date());
		File dir = new File(basePath + separator + pathName);
		if(!dir.exists())
			dir.mkdir();
		return pathName;
	}
	
	public static String getNewFileName(String oldFileName){
		oldFileName = oldFileName.replaceAll("'", "").replaceAll("\"", "");
		Calendar date = Calendar.getInstance();
		int hour = date.get(Calendar.HOUR_OF_DAY);
		int minute = date.get(Calendar.MINUTE);
		int second = date.get(Calendar.SECOND);
		if(oldFileName.length()>30)
			oldFileName = oldFileName.substring(oldFileName.length()-30);
		return (new Integer(hour*3600 + minute*60 + second).toString()) 
					+ split + oldFileName;
	}
	
	public static String getThumbFileName(String fileName){
		int pos = fileName.lastIndexOf(".");
		if(pos>=0)
			return fileName.substring(0, pos) + "s" + fileName.substring(pos);
		else
			return	fileName + "s";
	}
	
   	/**
   	 * This method checks if the given file exists on disk. If it does it's ignored because
   	 * that means that the file is allready cached on the server. If not we dump
   	 * the text on it.
   	 */
	public void dumpAttributeToFile(String attributeValue, String fileName, String filePath) throws Exception
	{
		File outputFile = new File(filePath + separator + fileName);
		PrintWriter pw = new PrintWriter(new FileWriter(outputFile));
        pw.println(attributeValue);    
        pw.close();
	}
	
 	/**
 	 * �����ļ�
   	 * This method checks if the given file exists on disk. If it does it's ignored because
   	 * that means that the file is allready cached on the server. If not we take out the stream from the 
   	 * digitalAsset-object and dumps it.
   	 */
	public void dumpAsset(File file, String fileName, String filePath) throws Exception
	{
		long timer = System.currentTimeMillis();
		
		File outputFile = new File(filePath + separator + fileName);
		if(outputFile.exists())
		{
			return;
		}
		
		FileOutputStream fis = new FileOutputStream(outputFile);
		BufferedOutputStream bos = new BufferedOutputStream(fis);
		
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
		
		int character;
        while ((character = bis.read()) != -1)
        {
			bos.write(character);
        }
		bos.flush();
		
        bis.close();
		fis.close();
		bos.close();
	}

	/**
	 * ��������ͼ
	 * This method checks if the given file exists on disk. If it does it's ignored because
	 * that means that the file is allready cached on the server. If not we take out the stream from the 
	 * digitalAsset-object and dumps a thumbnail to it.
	 */
   	
	public void dumpAssetThumbnail(File file, String fileName, String thumbnailFile, String filePath, int width, int height, int quality) throws Exception
	{
		long timer = System.currentTimeMillis();
		
		File outputFile = new File(filePath + separator + thumbnailFile);
		if(outputFile.exists())
		{
			return;
		}
		
//		ThumbnailGenerator tg = new ThumbnailGenerator();
		this.transform(filePath + separator + fileName, filePath + separator + thumbnailFile, width, height, quality);
		
//		log.info("Time for dumping file " + fileName + ":" + (System.currentTimeMillis() - timer));
	}
	
	/**
	 * This method removes all images in the digitalAsset directory which belongs to a certain digital asset.
	 */
	public void deleteDigitalAssets(String filePath, String filePrefix) throws Exception
	{ 
		try
		{
			File assetDirectory = new File(filePath);
			File[] files = assetDirectory.listFiles(new FilenameFilterImpl(filePrefix)); 	
			for(int i=0; i<files.length; i++)
			{
				File file = files[i];
				file.delete();
			}
		}
		catch(Exception e)
		{
		}
	}
	
	
	
	public void transform(String originalFile, String thumbnailFile, int thumbWidth, int thumbHeight, int quality) throws Exception 
	{
		Image image = javax.imageio.ImageIO.read(new File(originalFile));
	    
	    double thumbRatio = (double)thumbWidth / (double)thumbHeight;
	    int imageWidth    = image.getWidth(null);
	    int imageHeight   = image.getHeight(null);
	    double imageRatio = (double)imageWidth / (double)imageHeight;
	    if (thumbRatio < imageRatio) 
	    {
	    	thumbHeight = (int)(thumbWidth / imageRatio);
	    } 
	    else 
	    {
	      	thumbWidth = (int)(thumbHeight * imageRatio);
	    }
	    
		if(imageWidth < thumbWidth && imageHeight < thumbHeight)
		{
			thumbWidth = imageWidth;
			thumbHeight = imageHeight;
		}
		else if(imageWidth < thumbWidth)
			thumbWidth = imageWidth;
		else if(imageHeight < thumbHeight)
			thumbHeight = imageHeight;

	    BufferedImage thumbImage = new BufferedImage(thumbWidth, thumbHeight, BufferedImage.TYPE_INT_RGB);
	    Graphics2D graphics2D = thumbImage.createGraphics();
	    graphics2D.setBackground(Color.WHITE);
    	graphics2D.setPaint(Color.WHITE); 
    	graphics2D.fillRect(0, 0, thumbWidth, thumbHeight);
	    graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    graphics2D.drawImage(image, 0, 0, thumbWidth, thumbHeight, null);
	    
		javax.imageio.ImageIO.write(thumbImage, "JPG", new File(thumbnailFile));
	}

}
