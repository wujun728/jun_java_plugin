/**
 * Copyright 2013 ABSir's Studio
 * 
 * All right reserved
 *
 * Create on 2013-8-13 上午10:56:54
 */
package com.absir.zxing.support;

import java.awt.image.BufferedImage;

/**
 * @author absir
 * 
 */
public class BufferedImageSource {

	/** width */
	private int width;

	/** height */
	private int height;

	/** rgbas */
	private byte[] rgbas;

	/**
	 * @param image
	 */
	public BufferedImageSource(BufferedImage bufferedImage) {
		width = bufferedImage.getWidth();
		height = bufferedImage.getHeight();
		rgbas = new byte[width * height * 4];
		int k = 0;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int pixel = bufferedImage.getRGB(i, j);
				rgbas[k++] = (byte) ((pixel) & 0xff);
				rgbas[k++] = (byte) ((pixel >> 8) & 0xff);
				rgbas[k++] = (byte) ((pixel >> 16) & 0xff);
				rgbas[k++] = (byte) ((pixel >> 24) & 0xff);
			}
		}
	}
}
