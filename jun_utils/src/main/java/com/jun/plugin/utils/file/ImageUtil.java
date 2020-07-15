package com.jun.plugin.utils.file;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.ImageIcon;

//import com.sun.image.codec.jpeg.JPEGCodec;
//import com.sun.image.codec.jpeg.JPEGEncodeParam;
//import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ImageUtil {
	private String srcFile;
	private String destFile;
	private int width;
	private int height;
	private Image img;

	/**
	 * 构造函数
	 * 
	 * 
	 * @param fileName
	 *            String
	 * @throws IOException
	 */
	public ImageUtil(String fileName) throws IOException {  //  a.jpg   a_s.jpg
		File _file = new File(fileName); // 读入文件
		this.srcFile = fileName;
		// 查找最后一个.
		int index = this.srcFile.lastIndexOf(".");
		String ext = this.srcFile.substring(index);
		this.destFile = this.srcFile.substring(0, index) + "_s" + ext;
		img = javax.imageio.ImageIO.read(_file); // 构造Image对象
		width = img.getWidth(null); // 得到源图宽
		height = img.getHeight(null); // 得到源图长
	}

	/**
	 * 强制压缩/放大图片到固定的大小
	 * 
	 * @param w
	 *            int 新宽度
	 * @param h
	 *            int 新高度
	 * @throws IOException
	 */
	public void resize(int w, int h) throws IOException {
		BufferedImage _image = new BufferedImage(w, h,
				BufferedImage.TYPE_INT_RGB);
		_image.getGraphics().drawImage(img, 0, 0, w, h, null); // 绘制缩小后的图
		FileOutputStream out = new FileOutputStream(destFile); // 输出到文件流
//		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
//		FileOutputStream out = new FileOutputStream(destFile);  
		ImageIO.write(_image, "jpg", out); 
//		encoder.encode(_image); // 近JPEG编码
		out.close();
	}

	/**
	 * 按照固定的比例缩放图片
	 * 
	 * @param t
	 *            double 比例
	 * @throws IOException
	 */
	public void resize(double t) throws IOException {
		int w = (int) (width * t);
		int h = (int) (height * t);
		resize(w, h);
	}

	/**
	 * 以宽度为基准，等比例放缩图片
	 * 
	 * @param w
	 *            int 新宽度
	 * @throws IOException
	 */
	public void resizeByWidth(int w) throws IOException {
		int h = (int) (height * w / width);
		resize(w, h);
	}

	/**
	 * 以高度为基准，等比例缩放图片
	 * 
	 * @param h
	 *            int 新高度
	 * @throws IOException
	 */
	public void resizeByHeight(int h) throws IOException {
		int w = (int) (width * h / height);
		resize(w, h);
	}

	/**
	 * 按照最大高度限制，生成最大的等比例缩略图
	 * 
	 * @param w
	 *            int 最大宽度
	 * @param h
	 *            int 最大高度
	 * @throws IOException
	 */
	public void resizeFix(int w, int h) throws IOException {
		if (width / height > w / h) {
			resizeByWidth(w);
		} else {
			resizeByHeight(h);
		}
	}

	/**
	 * 设置目标文件名 setDestFile
	 * 
	 * @param fileName
	 *            String 文件名字符串
	 */
	public void setDestFile(String fileName) throws Exception {
		if (!fileName.endsWith(".jpg")) {
			throw new Exception("Dest File Must end with \".jpg\".");
		}
		destFile = fileName;
	}

	/**
	 * 获取目标文件名 getDestFile
	 */
	public String getDestFile() {
		return destFile;
	}

	/**
	 * 获取图片原始宽度 getSrcWidth
	 */
	public int getSrcWidth() {
		return width;
	}

	/**
	 * 获取图片原始高度 getSrcHeight
	 */
	public int getSrcHeight() {
		return height;
	}
	
	//****************************************************************************************************
	//****************************************************************************************************
	//****************************************************************************************************
	
	
	/**
	 * @param im
	 *            原始图像
	 * @param resizeTimes
	 *            倍数,比如0.5就是缩小一半,0.98等等double类型
	 * @return 返回处理后的图像
	 */
	private static BufferedImage zoomImage(BufferedImage im, float resizeTimes) {
		/* 原始图像的宽度和高度 */
		int width = im.getWidth();
		int height = im.getHeight();
		/* 调整后的图片的宽度和高度 */
		int toWidth = (int) (Float.parseFloat(String.valueOf(width)) * resizeTimes);
		int toHeight = (int) (Float.parseFloat(String.valueOf(height)) * resizeTimes);
		/* 新生成结果图片 */
		BufferedImage result = new BufferedImage(toWidth, toHeight,
				BufferedImage.TYPE_INT_RGB);
		result.getGraphics().drawImage(
				im.getScaledInstance(toWidth, toHeight,
						java.awt.Image.SCALE_SMOOTH), 0, 0, null);
		return result;
	}

	/**
	 * 把图片写到磁盘上
	 * 
	 * @param im
	 * @param path
	 *            eg: C://home// 图片写入的文件夹地址
	 * @param fileName
	 *            DCM1987.jpg 写入图片的名字
	 * @return
	 */
	private static boolean writeNormalQuality(BufferedImage im,
			String fileFullPath) {
		File f = new File(fileFullPath);
		String fileType = getExtension(fileFullPath);
		if (fileType == null) return false;
		try {
			ImageIO.write(im, fileType, f);
			im.flush();
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	public boolean writeHighQuality(BufferedImage im, String fileFullPath) {
		try {
			/* 输出到文件流 */
			FileOutputStream newimage = new FileOutputStream(fileFullPath
					+ System.currentTimeMillis() + ".jpg");
//			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(newimage);
//			JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(im);
			ImageIO.write(im, "jpg", newimage); 
			/* 压缩质量 */
//			jep.setQuality(1f, true);
//			encoder.encode(im, jep);
			/* 近JPEG编码 */
			newimage.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 返回文件的文件后缀名
	 * 
	 * @param fileName
	 * @return
	 */
	private static String getExtension(String fileName) {
		try {
			return fileName.split("\\.")[fileName.split("\\.").length - 1];
		} catch (Exception e) {
			return null;
		}
	}

	public static boolean doThumbnail(String srcImagePaht,
			String destImagePath, int width) {
		boolean doOk = false;
		File srcFile = new File(srcImagePaht);
		if (srcFile.exists()) {
			try {
				BufferedImage im = javax.imageio.ImageIO.read(srcFile);
				int oldWidth = im.getWidth();
				// 如果原图比要求的宽度还小，不进行处理,直接复制
				if (oldWidth < width) {
					doOk = writeNormalQuality(im, destImagePath);
				} else {
					float resizeTimes = (float) (width * 1.0) / oldWidth;
					BufferedImage om = zoomImage(im, resizeTimes);
					doOk = writeNormalQuality(om, destImagePath);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return doOk;
	}

	public static boolean doThumbnail(String srcImagePaht,
			String destImagePath, float resizeTimes) {
		boolean doOk = false;
		File srcFile = new File(srcImagePaht);
		if (srcFile.exists()) {
			try {
				BufferedImage im = javax.imageio.ImageIO.read(srcFile);
				BufferedImage om = zoomImage(im, resizeTimes);
				doOk = writeNormalQuality(om, destImagePath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return doOk;
	}
	
	/**
	 * 图片缩放(图片等比例缩放为指定大小，空白部分以白色填充)
	 * 
	 * @param srcBufferedImage
	 *            源图片
	 * @param destFile
	 *            缩放后的图片文件
	 */
	public static void zoom(BufferedImage srcBufferedImage, File destFile, int destHeight, int destWidth) {
		try {
			int imgWidth = destWidth;
			int imgHeight = destHeight;
			int srcWidth = srcBufferedImage.getWidth();
			int srcHeight = srcBufferedImage.getHeight();
			if (srcHeight >= srcWidth) {
				imgWidth = (int) Math.round(((destHeight * 1.0 / srcHeight) * srcWidth));
			} else {
				imgHeight = (int) Math.round(((destWidth * 1.0 / srcWidth) * srcHeight));
			}
			BufferedImage destBufferedImage = new BufferedImage(destWidth, destHeight, BufferedImage.TYPE_INT_RGB);
			Graphics2D graphics2D = destBufferedImage.createGraphics();
			graphics2D.setBackground(Color.WHITE);
			graphics2D.clearRect(0, 0, destWidth, destHeight);
			graphics2D.drawImage(srcBufferedImage.getScaledInstance(imgWidth, imgHeight, Image.SCALE_SMOOTH), (destWidth / 2) - (imgWidth / 2), (destHeight / 2) - (imgHeight / 2), null);
			graphics2D.dispose();
			ImageIO.write(destBufferedImage, "JPEG", destFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	  
	/**
	 * 获取图片文件的类型.
	 * 
	 * @param imageFile
	 *            图片文件对象.
	 * @return 图片文件类型
	 */
	public static String getImageFormatName(File imageFile) {
		try {
			ImageInputStream imageInputStream = ImageIO.createImageInputStream(imageFile);
			Iterator<ImageReader> iterator = ImageIO.getImageReaders(imageInputStream);
			if (!iterator.hasNext()) {
				return null;
			}
			ImageReader imageReader = (ImageReader) iterator.next();
			imageInputStream.close();
			return imageReader.getFormatName().toLowerCase();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	//*************************************************************************************************************************
	//*************************************************************************************************************************
	  public static final MediaTracker tracker = new MediaTracker(new Component() {
	        private static final long serialVersionUID = 1234162663955668507L;} 
	    );
	    /**
	     * @param originalFile ԭͼ��
	     * @param resizedFile ѹ�����ͼ��
	     * @param width ͼ���
	     * @param format ͼƬ��ʽ jpg, png, gif(�Ƕ���)
	     * @throws IOException
	     */
	    public static void resize(File originalFile, File resizedFile, int width, String format) throws IOException {
	        if(format!=null && "gif".equals(format.toLowerCase())){
	        	resize(originalFile, resizedFile, width, 1);
	        	return;
	        }
	        FileInputStream fis = new FileInputStream(originalFile);
	        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
	        int readLength = -1;
	        int bufferSize = 1024;
	        byte bytes[] = new byte[bufferSize];
	        while ((readLength = fis.read(bytes, 0, bufferSize)) != -1) {
	            byteStream.write(bytes, 0, readLength);
	        }
	        byte[] in = byteStream.toByteArray();
	        fis.close();
	        byteStream.close();
	        
	    	Image inputImage = Toolkit.getDefaultToolkit().createImage( in );
	        waitForImage( inputImage );
	        int imageWidth = inputImage.getWidth( null );
	        if ( imageWidth < 1 ) 
	           throw new IllegalArgumentException( "image width " + imageWidth + " is out of range" );
	        int imageHeight = inputImage.getHeight( null );
	        if ( imageHeight < 1 ) 
	           throw new IllegalArgumentException( "image height " + imageHeight + " is out of range" );
	        
	        // Create output image.
	        int height = -1;
	        double scaleW = (double) imageWidth / (double) width;
	        double scaleY = (double) imageHeight / (double) height;
	        if (scaleW >= 0 && scaleY >=0) {
	            if (scaleW > scaleY) {
	                height = -1;
	            } else {
	                width = -1;
	            }
	        }
	        Image outputImage = inputImage.getScaledInstance( width, height, java.awt.Image.SCALE_DEFAULT);
	        checkImage( outputImage );        
	        encode(new FileOutputStream(resizedFile), outputImage, format);        
	    }    

	    /** Checks the given image for valid width and height. */
	    private static void checkImage( Image image ) {
	       waitForImage( image );
	       int imageWidth = image.getWidth( null );
	       if ( imageWidth < 1 ) 
	          throw new IllegalArgumentException( "image width " + imageWidth + " is out of range" );
	       int imageHeight = image.getHeight( null );
	       if ( imageHeight < 1 ) 
	          throw new IllegalArgumentException( "image height " + imageHeight + " is out of range" );
	    }

	    /** Waits for given image to load. Use before querying image height/width/colors. */
	    private static void waitForImage( Image image ) {
	       try {
	          tracker.addImage( image, 0 );
	          tracker.waitForID( 0 );
	          tracker.removeImage(image, 0);
	       } catch( InterruptedException e ) { e.printStackTrace(); }
	    } 

	    /** Encodes the given image at the given quality to the output stream. */
	    private static void encode( OutputStream outputStream, Image outputImage, String format ) 
	       throws java.io.IOException {
	       int outputWidth  = outputImage.getWidth( null );
	       if ( outputWidth < 1 ) 
	          throw new IllegalArgumentException( "output image width " + outputWidth + " is out of range" );
	       int outputHeight = outputImage.getHeight( null );
	       if ( outputHeight < 1 ) 
	          throw new IllegalArgumentException( "output image height " + outputHeight + " is out of range" );

	       // Get a buffered image from the image.
	       BufferedImage bi = new BufferedImage( outputWidth, outputHeight,
	          BufferedImage.TYPE_INT_RGB );                                                   
	       Graphics2D biContext = bi.createGraphics();
	       biContext.drawImage( outputImage, 0, 0, null );
	       ImageIO.write(bi, format, outputStream);
	       outputStream.flush();      
	    } 
	    
		/**
		 * ����gifͼƬ
		 * @param originalFile ԭͼƬ
		 * @param resizedFile ���ź��ͼƬ
		 * @param newWidth ���
		 * @param quality ���ű��� (�ȱ���)
		 * @throws IOException
		 */
	    private static void resize(File originalFile, File resizedFile, int newWidth, float quality) throws IOException {
	        if (quality < 0 || quality > 1) {
	            throw new IllegalArgumentException("Quality has to be between 0 and 1");
	        } 
	        ImageIcon ii = new ImageIcon(originalFile.getCanonicalPath());
	        Image i = ii.getImage();
	        Image resizedImage = null; 
	        int iWidth = i.getWidth(null);
	        int iHeight = i.getHeight(null); 
	        if (iWidth > iHeight) {
	            resizedImage = i.getScaledInstance(newWidth, (newWidth * iHeight) / iWidth, Image.SCALE_SMOOTH);
	        } else {
	            resizedImage = i.getScaledInstance((newWidth * iWidth) / iHeight, newWidth, Image.SCALE_SMOOTH);
	        } 
	        // This code ensures that all the pixels in the image are loaded.
	        Image temp = new ImageIcon(resizedImage).getImage(); 
	        // Create the buffered image.
	        BufferedImage bufferedImage = new BufferedImage(temp.getWidth(null), temp.getHeight(null),
	                                                        BufferedImage.TYPE_INT_RGB); 
	        // Copy image to buffered image.
	        Graphics g = bufferedImage.createGraphics(); 
	        // Clear background and paint the image.
	        g.setColor(Color.white);
	        g.fillRect(0, 0, temp.getWidth(null), temp.getHeight(null));
	        g.drawImage(temp, 0, 0, null);
	        g.dispose(); 
	        // Soften.
	        float softenFactor = 0.05f;
	        float[] softenArray = {0, softenFactor, 0, softenFactor, 1-(softenFactor*4), softenFactor, 0, softenFactor, 0};
	        Kernel kernel = new Kernel(3, 3, softenArray);
	        ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
	        bufferedImage = cOp.filter(bufferedImage, null); 
	        // Write the jpeg to a file.
	        FileOutputStream out = new FileOutputStream(resizedFile);        
	        // Encodes image as a JPEG data stream
//	        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out); 
//	        JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bufferedImage); 
//	        param.setQuality(quality, true); 
//	        encoder.setJPEGEncodeParam(param);
//	        encoder.encode(bufferedImage);
	    }
	//*************************************************************************************************************************
	//*************************************************************************************************************************

	public static void main(String[] args) throws Exception {
		String srcImagePaht = "d:\\111.jpg";
		/* 这儿填写你存放要缩小图片的文件夹全地址 */
		String destImagePath = "d:\\1112.jpg";
		/* 这儿填写你转化后的图片存放的文件夹 */
		ImageUtil.doThumbnail(srcImagePaht, destImagePath, 40);
	}
	
	public static String readFileContent(String path) {
		BufferedReader reader = null;
		StringBuffer content = new StringBuffer();
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
			for (String line = null; (line = reader.readLine()) != null;) {
				content.append(line).append("\n");
			}
			return content.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	
	 
}
