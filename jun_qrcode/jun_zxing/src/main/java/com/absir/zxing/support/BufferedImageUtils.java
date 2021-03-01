/**
 * Copyright 2013 ABSir's Studio
 * 
 * All right reserved
 *
 * Create on 2013-8-13 上午11:40:24
 */
package com.absir.zxing.support;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

/**
 * @author absir
 * 
 */
public class BufferedImageUtils {

	/**
	 * @param image
	 * @return
	 */
	public static BufferedImage getBufferedImage(Image image) {
		return getBufferedImage(image, image.getWidth(null), image.getHeight(null));
	}

	/**
	 * @param image
	 * @param width
	 * @param height
	 * @return
	 */
	public static BufferedImage getBufferedImage(Image image, int width, int height) {
		return getBufferedImage(image, width, height, BufferedImage.TYPE_INT_ARGB);
	}

	/**
	 * @param image
	 * @param width
	 * @param height
	 * @param imageType
	 * @return
	 */
	public static BufferedImage getBufferedImage(Image image, int width, int height, int imageType) {
		BufferedImage bufferedImage = new BufferedImage(width, height, imageType);
		Graphics2D g = bufferedImage.createGraphics();
		g.drawImage(image, 0, 0, width, height, 0, 0, image.getWidth(null), image.getHeight(null), null);
		waitForImage(bufferedImage);
		return bufferedImage;
	}
	
	/**
	 * @param bufferedImage
	 */
	private static void waitForImage(BufferedImage bufferedImage) {
		bufferedImage.getHeight(new ImageObserver() {
			public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
				if (infoflags == ALLBITS) {
					return true;
				}

				return false;
			}
		});
	}

}
