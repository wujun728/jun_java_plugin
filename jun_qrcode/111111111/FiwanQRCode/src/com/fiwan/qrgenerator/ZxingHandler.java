package com.fiwan.qrgenerator;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import com.fiwan.utils.ExportQrCode;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import de.erichseifert.vectorgraphics2d.EPSGraphics2D;
import de.erichseifert.vectorgraphics2d.PDFGraphics2D;
import de.erichseifert.vectorgraphics2d.SVGGraphics2D;

/**
 * @version v1.0
 * @author frogchou
 * Zxing的一个管理类，用于扩展Zxing的一些功能。
 * */
public class ZxingHandler {
	private static final int BLACK = 0xFF000000;
	private static final int WHITE = 0xFFFFFFFF;

	/**
	 * 返回一个彩色定位的 BufferedImage
	 * @param matrix 输入一个已有的BitMatrix 对象，这个对象中已经存放了一个二维码
	 * @return BufferedImage 输出一个进过处理的BufferedImage
	 * */
	public static BufferedImage toBufferedImageWithColor(BitMatrix matrix) {
		int H = matrix.getHeight();
		int W = matrix.getWidth();
		int L = getFinderParttenWidth(matrix) + 3;  //获取定位符边长
		int[] pixels = new int[W * H];
		Color redColor = new Color(182, 0, 5);
		int redColorInt = redColor.getRGB();
		Color greenColor = new Color(0, 124, 54);
		int greenColorInt = greenColor.getRGB();
		Color blueColor = new Color(0, 64, 152);
		int blueColorInt = blueColor.getRGB();
		for (int y = 0; y < H; y++) {
			for (int x = 0; x < W; x++) {
				// 查找定位符，并换成彩色的。
				if (x > 0 && x < L && y > 0 && y < L) {
					pixels[y * W + x] = matrix.get(x, y) ? redColorInt : WHITE;
				} else if (x > (W - L) && x < H && y > 0 && y < L) {
					pixels[y * W + x] = matrix.get(x, y) ? blueColorInt : WHITE;
				} else if (x > 0 && x < L && y > (H - L) && y < H) {
					pixels[y * W + x] = matrix.get(x, y) ? greenColorInt
							: WHITE;
				} else {
					pixels[y * W + x] = matrix.get(x, y) ? BLACK : WHITE;
				}
			}
		}
		BufferedImage image = new BufferedImage(W, H,
				BufferedImage.TYPE_INT_RGB);
		image.getRaster().setDataElements(0, 0, W, H, pixels);
		return image;
	}

	/**
	 * 返回一个黑白的 BufferedImage
	 * @param matrix 输入一个已有的BitMatrix 对象，这个对象中已经存放了一个二维码
	 * @return BufferedImage 输出一个进过处理的BufferedImage
	 * */
	public static BufferedImage toBufferedImage(BitMatrix matrix) {
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
			}
		}
		return image;
	}

	/**
	 * 将黑白二维码图片输出到一个指定的文件中
	 * @param matrix 输入一个已有的BitMatrix 对象，这个对象中已经存放了一个二维码
	 * @param format 文件类型 仅限于图片文件类型
	 * @param file 指定输出的目标文件
	 * */
	public static void writeToFile(BitMatrix matrix, String format, File file)
			throws IOException {
		BufferedImage image = toBufferedImage(matrix);
		if (!ImageIO.write(image, format, file)) {
			throw new IOException("Could not write an image of format "
					+ format + " to " + file);
		}
	}

	/**
	 * 将彩色二维码图片输出到一个指定的文件中
	 * @param matrix 输入一个已有的BitMatrix 对象，这个对象中已经存放了一个二维码
	 * @param format 文件类型 仅限于图片文件类型
	 * @param file 指定输出的目标文件
	 * */
	public static void writeToFileWithColor(BitMatrix matrix, String format,
			File file) throws IOException {
		BufferedImage image = toBufferedImageWithColor(matrix);
		if (!ImageIO.write(image, format, file)) {
			throw new IOException("Could not write an image of format "
					+ format + " to " + file);
		}
	}

	/**
	 *将黑白二维码图片 放到一个输出流中
	 * @param matrix 输入一个已有的BitMatrix 对象，这个对象中已经存放了一个二维码
	 * @param format 文件类型 仅限于图片文件类型
	 * @param stream 指定输出流
	 * */
	public static void writeToStream(BitMatrix matrix, String format,
			OutputStream stream) throws IOException {
		BufferedImage image = toBufferedImage(matrix);
		if (!ImageIO.write(image, format, stream)) {
			throw new IOException("Could not write an image of format "
					+ format);
		}
	}

	/**
	 * 查找彩色定位符
	 * */
	private static int getFinderParttenWidth(BitMatrix matrix) {
		int W = matrix.getWidth();
		int H = matrix.getHeight();
		int length = 0;
		boolean flag = false;
		for (int y = 0; y < H; y++) {
			for (int x = 0; x < W; x++) {
				if (matrix.get(x, y) == true) {
					flag = true;
					length++;
				} else {
					if (flag != false) {
						return x;
					}
				}
			}
		}
		return length;
	}
	
	/**
	 * 识别图片中的二维码，返回码内容。
	 * @param BufferedImage 一个bufferedImage里面包含一个二维码。
	 * */
	public static String getQrcodeFromPic(BufferedImage bufferedImage){
		String retStr=null;
		LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
		Binarizer binarizer = new HybridBinarizer(source);
		BinaryBitmap bitmap = new BinaryBitmap(binarizer);
		HashMap<DecodeHintType, Object> hintTypeObjectHashMap = new HashMap<DecodeHintType, Object>();
		hintTypeObjectHashMap.put(DecodeHintType.CHARACTER_SET, "UTF-8");
		Result result = null;
		try {
			result = new MultiFormatReader().decode(bitmap, hintTypeObjectHashMap);
		} catch (NotFoundException e) {
			e.printStackTrace();
			return retStr;
		}finally {
			if (result != null) {
				retStr = result.getText();
				return retStr;
			}
			return retStr;
		}
	}
	
	public static void createEPSQRCode(Settings setting,File file,String content){
		double point_x = 0;
		double point_y = 0;
		final int blockSize = 1;
		EPSGraphics2D funcOld = new EPSGraphics2D(point_x, point_y, setting.getQrcodeSize() * blockSize,
				setting.getQrcodeSize() * blockSize);
		try {
			ExportQrCode.fill2VectorLine(funcOld,
					GetBitMatrix(content, setting.getQrcodeSize(),setting.getQrcodeErrorRate()), blockSize);
			PrintStream psFile = new PrintStream(file);
			psFile.append(funcOld.toString());
			psFile.close();
		} catch (WriterException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void createPDFQRCode(Settings setting,File file,String content){
		double point_x = 0;
		double point_y = 0;
		final int blockSize = 1;
		PDFGraphics2D funcOld = new PDFGraphics2D(point_x, point_y, setting.getQrcodeSize() * blockSize,
				setting.getQrcodeSize() * blockSize);
		try {
			ExportQrCode.fill2VectorLine(funcOld,
					GetBitMatrix(content, setting.getQrcodeSize(),setting.getQrcodeErrorRate()), blockSize);
			PrintStream psFile = new PrintStream(file);
			psFile.append(funcOld.toString());
			psFile.close();
		} catch (WriterException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void createSVGQRCode(Settings setting,File file,String content){
		double point_x = 0;
		double point_y = 0;
		final int blockSize = 1;
		SVGGraphics2D funcOld = new SVGGraphics2D(point_x, point_y, setting.getQrcodeSize() * blockSize,
				setting.getQrcodeSize() * blockSize);
		try {
			ExportQrCode.fill2VectorLine(funcOld,
					GetBitMatrix(content, setting.getQrcodeSize(),setting.getQrcodeErrorRate()), blockSize);
			PrintStream psFile = new PrintStream(file);
			psFile.append(funcOld.toString());
			psFile.close();
		} catch (WriterException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static BitMatrix GetBitMatrix(String content, int size,ErrorCorrectionLevel errorCorrectionLevel ) throws WriterException {
		size = (size <= 0) ? 100 : size;
		BitMatrix bitMatrix = null;
		MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
		Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		hints.put(EncodeHintType.MARGIN, 1); // 控制码图白边
		hints.put(EncodeHintType.ERROR_CORRECTION, errorCorrectionLevel); // 容错率
		bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, size, size, hints);
		return bitMatrix;
	}
}