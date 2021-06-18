
package com.fiwan.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.nutz.lang.Streams;


import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import de.erichseifert.vectorgraphics2d.EPSGraphics2D;
import de.erichseifert.vectorgraphics2d.PDFGraphics2D;
import de.erichseifert.vectorgraphics2d.SVGGraphics2D;
import de.erichseifert.vectorgraphics2d.VectorGraphics2D;

public class ExportQrCode {
	/**
	 * @param bitMatrix
	 * @param type
	 * @param width_height
	 * @param out
	 * @throws WriterException
	 * @throws IOException
	 */
	public static void doExport(BitMatrix bitMatrix,
								QCodeFileType type,
								double width_height,
								java.io.OutputStream out) throws WriterException, IOException {
		MatrixToImageConfig config = new MatrixToImageConfig();
		doExport(bitMatrix, type, config, out);
	}

	/**
	 * @param bitMatrix 注意如果是矢量数据,width不要太大容易造成文件过大
	 * @param type
	 * @param out
	 * @throws WriterException
	 * @throws IOException
	 */
	public static void doExport(BitMatrix bitMatrix,
								QCodeFileType type,
								MatrixToImageConfig config,
								java.io.OutputStream out) throws WriterException, IOException {
		double point_x = 0;
		double point_y = 0;

		//System.out.println(Json.toJson(bitMatrix.getEnclosingRectangle()));
		// 256 和32是有区别的,
		int width = bitMatrix.getWidth();
		int height = bitMatrix.getHeight();
		final int blockSize = 1;

		VectorGraphics2D v = null;
		switch (type) {
		case EPS:
			v = new EPSGraphics2D(point_x, point_y, width, height);
			break;
		case PDF:
			v = new PDFGraphics2D(point_x, point_y, (width), height);
			break;
		case SVG:
			v = new SVGGraphics2D(point_x, point_y, (width) * blockSize + width, (height)
																					* blockSize
																					+ height);
			break;
		default:
			break;
		}

		if (type.isVector()) { // 矢量图形

			// 同过点方式导出,容量太大
//			fill2Vector(v, bitMatrix, blockSize);
			// 通过线方式导出
			fill2VectorLine(v, bitMatrix, blockSize);
			Streams.writeAndClose(out, v.getBytes());
		} else {

			MatrixToImageWriter.writeToStream(bitMatrix, type.name(), out, config);

		}

	}

	/**
	 * 填充数据到 g 对象中,直接调用 g.toString()并写入到磁盘,即可得到 矢量图形文件
	 * 
	 * <code>
	 * VectorGraphics2D g=null;
	 * BitMatrix bitMatrix=null;
	 * fill2Vector(  g,   bitMatrix);
	 * 
	 * String dir = "E:/temp/093009/qrcode";
		File target = new File(dir, "32_h.eps");
		PrintStream psFile = new PrintStream(target);
		psFile.append(g.toString());
		psFile.close();
	 *  </code>
	 * 
	 * 
	 * @param g
	 * @param bitMatrix
	 * @param blockSize
	 *            块大小,一个单位的 对象宽度,也就是一个正方形点的 大小,
	 * @throws WriterException
	 */
	public static void fill2Vector(VectorGraphics2D g, BitMatrix bitMatrix, int blockSize)
			throws WriterException {
		if (g == null || bitMatrix == null)
			return;
		// 256 和32是有却别的,
		double width = bitMatrix.getWidth();
		double height = bitMatrix.getHeight();

		if (true) {
			for (int x = 0; x < width; x++) {
				int theX = x * blockSize;
				for (int y = 0; y < height; y++) {
					if (bitMatrix.get(x, y)) {
						int theY = y * blockSize;
						g.fillRect(theX, theY, blockSize, blockSize);
					}
				}
			}
		}
	}

	/**
	 * 填充数据到 g 对象中,直接调用 g.toString()并写入到磁盘,即可得到 矢量图形文件
	 * 
	 * <code>
	 * VectorGraphics2D g=null;
	 * BitMatrix bitMatrix=null;
	 * fill2Vector(  g,   bitMatrix);
	 * 
	 * String dir = "E:/temp/093009/qrcode";
		File target = new File(dir, "32_h.eps");
		PrintStream psFile = new PrintStream(target);
		psFile.append(g.toString());
		psFile.close();
	 *  </code>
	 * 
	 * 
	 * @param g
	 * @param bitMatrix
	 * @param blockSize
	 *            块大小,一个单位的 对象宽度,也就是一个正方形点的 大小,
	 * @throws WriterException
	 */
	@SuppressWarnings("unchecked")
	public static void fill2VectorLine(VectorGraphics2D funcOld, BitMatrix bitMatrix, int blockSize)
			throws WriterException {
		if (funcOld == null || bitMatrix == null)
			return;
		// 256 和32是有却别的,
		double width = bitMatrix.getWidth();
		double height = bitMatrix.getHeight();

		for (int x = 0; x < width; x++) {
			int theX = x * blockSize;
		//	System.out.println(String.format(	"------------------------------%s----------------------",
		//										x));
			// int yStart = 0;
			// int yEnd = 0;
			List tmp = new ArrayList();
			int jsq = 0;
			int prev = -1;
			for (int y = 0; y < height; y++) {

				if (bitMatrix.get(x, y)) {
					//System.out.println(String.format("	\t%s", y));
					if (prev == -1) {
						jsq++;
						prev = y;
						continue;
					}
					if (1 == y - prev) {// 判断是否是连续的 下一个-上一个=1
						jsq++;
						prev = y;
					} else {

						tmp.add(String.format("a:%s->%s", (y - jsq), (y)));
						// 把上一步的图形输出出去
						// funcOld.drawLine(theX, (y - jsq) * blockSize,
						// theX, (y) * blockSize);
						drawLine(blockSize, funcOld, theX, (y - jsq), theX, y);
						jsq = 0;
						prev = y;
					}

				} else {
					if (prev >= 0) {

						int y1 = (prev - jsq + 1);
						int y2 = prev;
						tmp.add(String.format("b:%s->%s", (y1), (y2)));
						// 把之前的输出
						if (y1 == y2) {
							funcOld.fillRect(theX, y1, blockSize, blockSize);
						} else {
							// funcOld.drawLine(theX, y1 * blockSize, theX,
							// y2 * blockSize);
							drawLine(blockSize, funcOld, theX, y1, theX, y2);
						}
						jsq = 0;
						prev = -1;
					}
				}
			}
			if (jsq > 0) {
				// 把剩余的输出
				// funcOld.drawLine(theX, (prev) * blockSize, theX, (prev +
				// jsq-1) * blockSize);
				drawLine(blockSize, funcOld, theX, prev, theX, (prev + jsq - 1));
				tmp.add(String.format("c:%s->%s", (prev), (prev + jsq - 1)));
			}
			//System.out.println(String.format("%s=%s", x, Json.toJson(tmp)));
		}
	}

	private static void drawLine(	int blockSize,
									VectorGraphics2D funcOld,
									int x1,
									int y1,
									int x2,
									int y2) {
		// funcOld.drawLine(x1, y1 * blockSize, x2, y2 *
		// blockSize);//不行和fillRect无法匹配

		java.awt.Rectangle s = new java.awt.Rectangle(x1, y1, blockSize, (y2 - y1 + 1) * blockSize);
		funcOld.fill(s);
		// 也行
		// for (int i = y1; i <= y2; i++) {
		// funcOld.fillRect(x1, i, blockSize, blockSize);
		// }
	}

}
