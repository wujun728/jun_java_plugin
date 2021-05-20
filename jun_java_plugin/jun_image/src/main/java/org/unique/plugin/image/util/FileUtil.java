package org.unique.plugin.image.util;

import java.io.File;

public class FileUtil {

	/**
	 * 判断文件是否存在
	 * 
	 * @param filePath
	 * @return
	 */
	public static boolean exists(String filePath) {
		return new File(filePath).exists();
	}

	/**
	 * 获取文件后缀
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getSuffix(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".") + 1);
	}

	/**
	 * 获取文件名
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getFileName(String filePath) {
		return filePath.substring(filePath.lastIndexOf("/") + 1);
	}

	/**
	 * 获取文件不带后缀的全路径
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getNoSuffixFilePath(String filePath) {
		return filePath.substring(0, filePath.lastIndexOf("."));
	}

	/**
	 * 获取图片缩略图路径
	 * 
	 * @param filePath
	 * @param width
	 * @param height
	 * @param quality
	 * @return
	 */
	public static String getThumbPath(String filePath, int width, int height,
			int quality) {
		String kzm = null;
		if (quality > 0) {
			kzm = FileUtil.getNoSuffixFilePath(filePath) + "_" + width + "x"
					+ height + "_" + quality + "."
					+ FileUtil.getSuffix(filePath);
		} else {
			kzm = FileUtil.getNoSuffixFilePath(filePath) + "_" + width + "x"
					+ height + "." + FileUtil.getSuffix(filePath);
		}
		return kzm;
	}

	/**
	 * 获取缩略图路径
	 * 
	 * @param filePath
	 * @param scale
	 * @param quality
	 * @return
	 */
	public static String getThumbScalePath(String filePath, float scale,
			int quality) {
		String kzm = null;
		if (quality > 0) {
			kzm = FileUtil.getNoSuffixFilePath(filePath) + "_"
					+ BigDecimalUtil.decimal2percent(scale, 0) + "_" + quality
					+ "." + FileUtil.getSuffix(filePath);
		} else {
			kzm = FileUtil.getNoSuffixFilePath(filePath) + "_"
					+ BigDecimalUtil.decimal2percent(scale, 0) + "."
					+ FileUtil.getSuffix(filePath);
		}
		return kzm;
	}

	/**
	 * 获取图片保存名称
	 * 
	 * @param filePath
	 * @param scale
	 * @param width
	 * @param height
	 * @param quality
	 * @param rotate
	 * @return
	 */
	public static String getImgPath(String filePath, Integer width,
			Integer height, boolean isforce, Double quality, Double scale,
			Double rotate) {
		String k = getNoSuffixFilePath(filePath);
		StringBuffer sb = new StringBuffer(k);
		if (null != width && height <= 0) {
			if (!isforce) {
				sb.append("_w" + width + "!");
			} else {
				sb.append("_w" + width);
			}
		}
		if (null != height && width <= 0) {
			if (!isforce) {
				sb.append("_h" + height + "!");
			} else {
				sb.append("_h" + height);
			}
		}
		if (null != width && null != height && width > 0 && height > 0) {
			if (!isforce) {
				sb.append("_" + width + "x" + height + "!");
			} else {
				sb.append("_" + width + "x" + height);
			}
		}
		if (null != scale && scale.compareTo(0.0D) > 0) {
			if (!isforce) {
				sb.append("_" + scale + "!");
			} else {
				sb.append("_" + scale);
			}
		}
		if (null != quality && quality > 0) {
			sb.append("_q" + quality);
		}
		if (null != rotate && rotate.compareTo(0.0D) > 0) {
			sb.append("_r" + rotate);
		}
		return sb.append("." + getSuffix(filePath)).toString();
	}

}
