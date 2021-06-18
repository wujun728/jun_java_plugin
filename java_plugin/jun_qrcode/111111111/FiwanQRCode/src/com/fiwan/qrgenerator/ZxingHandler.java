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
 * @author Wujun
 * Zxing��һ�������࣬������չZxing��һЩ���ܡ�
 * */
public class ZxingHandler {
	private static final int BLACK = 0xFF000000;
	private static final int WHITE = 0xFFFFFFFF;

	/**
	 * ����һ����ɫ��λ�� BufferedImage
	 * @param matrix ����һ�����е�BitMatrix ��������������Ѿ������һ����ά��
	 * @return BufferedImage ���һ�����������BufferedImage
	 * */
	public static BufferedImage toBufferedImageWithColor(BitMatrix matrix) {
		int H = matrix.getHeight();
		int W = matrix.getWidth();
		int L = getFinderParttenWidth(matrix) + 3;  //��ȡ��λ���߳�
		int[] pixels = new int[W * H];
		Color redColor = new Color(182, 0, 5);
		int redColorInt = redColor.getRGB();
		Color greenColor = new Color(0, 124, 54);
		int greenColorInt = greenColor.getRGB();
		Color blueColor = new Color(0, 64, 152);
		int blueColorInt = blueColor.getRGB();
		for (int y = 0; y < H; y++) {
			for (int x = 0; x < W; x++) {
				// ���Ҷ�λ���������ɲ�ɫ�ġ�
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
	 * ����һ���ڰ׵� BufferedImage
	 * @param matrix ����һ�����е�BitMatrix ��������������Ѿ������һ����ά��
	 * @return BufferedImage ���һ�����������BufferedImage
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
	 * ���ڰ׶�ά��ͼƬ�����һ��ָ�����ļ���
	 * @param matrix ����һ�����е�BitMatrix ��������������Ѿ������һ����ά��
	 * @param format �ļ����� ������ͼƬ�ļ�����
	 * @param file ָ�������Ŀ���ļ�
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
	 * ����ɫ��ά��ͼƬ�����һ��ָ�����ļ���
	 * @param matrix ����һ�����е�BitMatrix ��������������Ѿ������һ����ά��
	 * @param format �ļ����� ������ͼƬ�ļ�����
	 * @param file ָ�������Ŀ���ļ�
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
	 *���ڰ׶�ά��ͼƬ �ŵ�һ���������
	 * @param matrix ����һ�����е�BitMatrix ��������������Ѿ������һ����ά��
	 * @param format �ļ����� ������ͼƬ�ļ�����
	 * @param stream ָ�������
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
	 * ���Ҳ�ɫ��λ��
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
	 * ʶ��ͼƬ�еĶ�ά�룬���������ݡ�
	 * @param BufferedImage һ��bufferedImage�������һ����ά�롣
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
		hints.put(EncodeHintType.MARGIN, 1); // ������ͼ�ױ�
		hints.put(EncodeHintType.ERROR_CORRECTION, errorCorrectionLevel); // �ݴ���
		bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, size, size, hints);
		return bitMatrix;
	}
}