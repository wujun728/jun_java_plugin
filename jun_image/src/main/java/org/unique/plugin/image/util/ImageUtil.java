package org.unique.plugin.image.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.apache.log4j.Logger;

/**
 * 图片工具类
 * 
 * @author rex
 */
public class ImageUtil {

    private static Logger logger = Logger.getLogger(ImageUtil.class);

    /**
	 * 是否是图片类型
	 * 
	 * @param fileName
	 * @return
	 */
	public static boolean isImg(String fileName) {
		Pattern p = Pattern.compile("^(jpeg|jpg|png|gif|bmp|webp)([\\w-./?%&=]*)?$");
		Matcher m = p.matcher(fileName.substring(fileName.lastIndexOf(".") + 1 ));
		if (m.find()) {
			return true;
		}
		return false;
	}
	
    /**
     * 获取图片宽高(图片越大，消耗的时间越长，针对百K以下的图片速度较快)
     * 
     * @param file
     * @return
     */
    public static Map<String, Integer> getImageWH(File file) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        try {
            BufferedImage Bi = ImageIO.read(file);
            map.put("width", Bi.getWidth());
            map.put("height", Bi.getHeight());
        } catch (IOException e) {
            logger.warn(e.getMessage());
        }
        return map;
    }

    /**
     * 获取图片宽高(不论图片大小，基本恒定时间，在100ms左右)
     * 
     * @param file
     * @param suffix 文件后缀
     * @return
     */
    public static Map<String, Integer> getImageWH(File file, String suffix) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        try {
            Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName(suffix);
            ImageReader reader = (ImageReader) readers.next();
            ImageInputStream iis = ImageIO.createImageInputStream(file);
            reader.setInput(iis, true);
            map.put("width", reader.getWidth(0));
            map.put("height", reader.getHeight(0));
        } catch (IOException e) {
            logger.warn(e.getMessage());
        }
        return map;
    }
}
