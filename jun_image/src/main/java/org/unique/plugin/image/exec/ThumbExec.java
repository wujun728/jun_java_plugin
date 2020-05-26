package org.unique.plugin.image.exec;

import java.io.File;
import java.io.IOException;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.Thumbnails.Builder;

import org.apache.log4j.Logger;

/**
 * 缩略图处理器
 * 
 * @author rex
 */
public class ThumbExec {

	private static Logger logger = Logger.getLogger(ThumbExec.class);

	/**
	 * 缩略图片
	 * @param filePath	原图地址
	 * @param thumbPath	缩略图地址
	 * @param width		缩放width
	 * @param height	缩放height
	 * @param isforce	是否按比例
	 * @param scale		按百分比缩放
	 * @param quality	图片质量
	 * @param rotate	旋转角度
	 * @return
	 */
	public static String thumb(String filePath, String thumbPath, int width,
			int height, boolean isforce, double scale, double quality,
			double rotate) {
		File img = new File(thumbPath);
		// 如果存在则返回
		if (img.exists()) {
			return img.getPath();
		}
		Builder<File> f = Thumbnails.of(filePath);
		// 只按width缩放
		if (width > 0 && height <= 0) {
			f.width(width).keepAspectRatio(isforce);
		}
		// 只按height缩放
		if (width <= 0 && height > 0) {
			f.height(height).keepAspectRatio(isforce);
		}
		// 按照widthxheight缩放
		if (width > 0 && height > 0) {
			f.size(width, height).keepAspectRatio(isforce);
		}
		//按照比例缩放
		if (scale > 0.0D) {
			f.scale(scale);
		}
		//缩略图质量
		if (quality > 0.0D) {
			f.outputQuality(quality).outputFormat("jpg");
		}
		//旋转角度
		if (rotate > 0.0D) {
			f.rotate(rotate);
		}
		try {
			f.toFile(img);
			return img.getPath();
		} catch (IOException e) {
			logger.warn(e.getMessage());
		}
		return filePath;
	}

	public static void main(String[] args) throws IOException {
		String filePath = "E:\\javaer.jpg";
		File img = new File("E:\\javaer_4.jpg");
		Builder<File> f = Thumbnails.of(filePath);
		f.width(200).toFile(img);
	}
}
